/**
 * Copyright © airback
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.airback.module.project.view.ticket;

import com.hp.gagawa.java.elements.A;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Span;
import com.airback.common.i18n.GenericI18Enum;
import com.airback.core.IgnoreException;
import com.airback.core.utils.StringUtils;
import com.airback.module.project.CurrentProjectVariables;
import com.airback.module.project.ProjectLinkGenerator;
import com.airback.module.project.ProjectRolePermissionCollections;
import com.airback.module.project.domain.BugWithBLOBs;
import com.airback.module.project.domain.ProjectTicket;
import com.airback.module.project.domain.Risk;
import com.airback.module.project.domain.Task;
import com.airback.module.project.event.TicketEvent;
import com.airback.module.project.i18n.ProjectCommonI18nEnum;
import com.airback.module.project.service.BugService;
import com.airback.module.project.service.TaskService;
import com.airback.module.project.service.ProjectTicketService;
import com.airback.module.project.service.RiskService;
import com.airback.module.project.ui.components.TicketRowRender;
import com.airback.spring.AppContextUtil;
import com.airback.vaadin.AppUI;
import com.airback.vaadin.EventBusFactory;
import com.airback.vaadin.TooltipHelper;
import com.airback.vaadin.UserUIContext;
import com.airback.vaadin.ui.ELabel;
import com.airback.vaadin.ui.UIUtils;
import com.airback.vaadin.web.ui.AbstractToggleSummaryField;
import com.airback.vaadin.web.ui.ConfirmDialogExt;
import com.airback.vaadin.web.ui.WebThemes;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;

/**
 * @author airback Ltd
 * @since 5.2.3
 */
public class ToggleTicketSummaryField extends AbstractToggleSummaryField {
    private ProjectTicket ticket;
    private boolean isRead = true;

    public ToggleTicketSummaryField(ProjectTicket ticket) {
        this.ticket = ticket;
        this.setWidth("100%");
        titleLinkLbl = ELabel.html(buildTicketLink()).withStyleName(ValoTheme.LABEL_NO_MARGIN,
                WebThemes.LABEL_WORD_WRAP).withUndefinedWidth();
        if (ticket.isClosed()) {
            titleLinkLbl.addStyleName(WebThemes.LINK_COMPLETED);
        } else if (ticket.isOverdue()) {
            titleLinkLbl.addStyleName(WebThemes.LINK_OVERDUE);
        }
        this.addComponent(titleLinkLbl);
        if (CurrentProjectVariables.canWriteTicket(ticket)) {
            this.addStyleName("editable-field");
            buttonControls = new MHorizontalLayout().withMargin(false).withStyleName("toggle");
            buttonControls.setDefaultComponentAlignment(Alignment.TOP_LEFT);
            MButton instantEditBtn = new MButton("", clickEvent -> {
                if (isRead) {
                    removeComponent(titleLinkLbl);
                    removeComponent(buttonControls);
                    TextField editField = new TextField();
                    editField.setValue(ticket.getName());
                    editField.setWidth("100%");
                    editField.focus();
                    addComponent(editField);
                    removeStyleName("editable-field");
                    editField.addShortcutListener(new ShortcutListener("enter", ShortcutAction.KeyCode.ENTER, (int[]) null) {
                        @Override
                        public void handleAction(Object sender, Object target) {
                            updateFieldValue(editField);
                        }
                    });
                    editField.addBlurListener(blurEvent -> updateFieldValue(editField));
                    isRead = !isRead;
                }
            }).withIcon(VaadinIcons.EDIT).withStyleName(ValoTheme.BUTTON_ICON_ALIGN_TOP)
                    .withDescription(UserUIContext.getMessage(GenericI18Enum.ACTION_CLICK_TO_EDIT));
            buttonControls.with(instantEditBtn);

            if ((ticket.isRisk() && CurrentProjectVariables.canAccess(ProjectRolePermissionCollections.RISKS))
                    || (ticket.isBug() && CurrentProjectVariables.canAccess(ProjectRolePermissionCollections.BUGS))
                    || (ticket.isTask() && CurrentProjectVariables.canAccess(ProjectRolePermissionCollections.TASKS))) {
                MButton removeBtn = new MButton("", clickEvent -> {
                    ConfirmDialogExt.show(UI.getCurrent(),
                            UserUIContext.getMessage(GenericI18Enum.DIALOG_DELETE_TITLE, AppUI.getSiteName()),
                            UserUIContext.getMessage(GenericI18Enum.DIALOG_DELETE_SINGLE_ITEM_MESSAGE),
                            UserUIContext.getMessage(GenericI18Enum.ACTION_YES),
                            UserUIContext.getMessage(GenericI18Enum.ACTION_NO),
                            confirmDialog -> {
                                if (confirmDialog.isConfirmed()) {
                                    AppContextUtil.getSpringBean(ProjectTicketService.class).removeTicket(ticket, UserUIContext.getUsername());
                                    TicketRowRender rowRenderer = UIUtils.getRoot(ToggleTicketSummaryField.this,
                                            TicketRowRender.class);
                                    if (rowRenderer != null) {
                                        rowRenderer.selfRemoved();
                                    }
                                    EventBusFactory.getInstance().post(new TicketEvent.HasTicketPropertyChanged(this, "all"));
                                }
                            });
                }).withIcon(VaadinIcons.TRASH).withStyleName(ValoTheme.BUTTON_ICON_ALIGN_TOP);
                buttonControls.with(removeBtn);
            }

            this.addComponent(buttonControls);
        }
    }

    private void updateFieldValue(TextField editField) {
        removeComponent(editField);
        addComponent(titleLinkLbl);
        addComponent(buttonControls);
        addStyleName("editable-field");
        String newValue = editField.getValue();
        if (StringUtils.isNotBlank(newValue) && !newValue.equals(ticket.getName())) {
            ticket.setName(newValue);
            titleLinkLbl.setValue(buildTicketLink());
            if (ticket.isBug()) {
                BugWithBLOBs bug = ProjectTicket.buildBug(ticket);
                BugService bugService = AppContextUtil.getSpringBean(BugService.class);
                bugService.updateSelectiveWithSession(bug, UserUIContext.getUsername());
            } else if (ticket.isTask()) {
                Task task = ProjectTicket.buildTask(ticket);
                TaskService taskService = AppContextUtil.getSpringBean(TaskService.class);
                taskService.updateSelectiveWithSession(task, UserUIContext.getUsername());
            } else if (ticket.isRisk()) {
                Risk risk = ProjectTicket.buildRisk(ticket);
                RiskService riskService = AppContextUtil.getSpringBean(RiskService.class);
                riskService.updateSelectiveWithSession(risk, UserUIContext.getUsername());
            }
        }

        isRead = !isRead;
    }

    private String buildTicketLink() {
        Div issueDiv = new Div();

        A ticketLink = new A().setId(String.format("tag%s", TooltipHelper.TOOLTIP_ID));
        if (ticket.isBug() || ticket.isTask() || ticket.isRisk()) {
            ticketLink.setHref(ProjectLinkGenerator.generateProjectItemLink(ticket.getProjectShortName(),
                    ticket.getProjectId(), ticket.getType(), ticket.getExtraTypeId() + ""));
        } else {
            throw new IgnoreException(String.format("Not support type: %s", ticket.getType()));
        }

        ticketLink.setAttribute("onmouseover", TooltipHelper.projectHoverJsFunction(ticket.getType(), ticket.getTypeId() + ""));
        ticketLink.setAttribute("onmouseleave", TooltipHelper.itemMouseLeaveJsFunction());
        ticketLink.appendText(ticket.getName());

        issueDiv.appendChild(ticketLink);

        if (ticket.isOverdue()) {
            issueDiv.appendChild(new Span().setCSSClass(WebThemes.META_INFO).appendText(" - " + UserUIContext
                    .getMessage(ProjectCommonI18nEnum.OPT_DUE_IN, UserUIContext.formatDuration(ticket.getDueDate()))));
        }
        return issueDiv.write();
    }

    public void setClosedTicket() {
        titleLinkLbl.addStyleName(WebThemes.LINK_COMPLETED);
    }

    public void unsetClosedTicket() {
        titleLinkLbl.removeStyleName(WebThemes.LINK_COMPLETED);
    }
}
