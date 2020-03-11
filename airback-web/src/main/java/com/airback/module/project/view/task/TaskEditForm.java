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
package com.airback.module.project.view.task;

import com.airback.common.domain.MonitorItem;
import com.airback.common.i18n.GenericI18Enum;
import com.airback.common.service.MonitorItemService;
import com.airback.module.file.AttachmentUtils;
import com.airback.module.project.CurrentProjectVariables;
import com.airback.module.project.ProjectTypeConstants;
import com.airback.module.project.domain.SimpleTask;
import com.airback.module.project.event.TaskEvent;
import com.airback.module.project.event.TicketEvent;
import com.airback.module.project.service.TaskService;
import com.airback.module.project.ui.components.ProjectSubscribersComp;
import com.airback.spring.AppContextUtil;
import com.airback.vaadin.AppUI;
import com.airback.vaadin.EventBusFactory;
import com.airback.vaadin.UserUIContext;
import com.airback.vaadin.ui.AdvancedEditBeanForm;
import com.airback.vaadin.ui.WrappedFormLayoutFactory;
import com.airback.vaadin.web.ui.DefaultDynaFormLayout;
import com.airback.vaadin.web.ui.WebThemes;
import com.airback.vaadin.web.ui.field.AttachmentUploadField;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractComponent;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author airback Ltd
 * @since 5.4.3
 */
public class TaskEditForm extends AdvancedEditBeanForm<SimpleTask> {

    @Override
    public void setBean(final SimpleTask item) {
        this.setFormLayoutFactory(new FormLayoutFactory());
        this.setBeanFormFieldFactory(new TaskEditFormFieldFactory(this, CurrentProjectVariables.getProjectId()));
        super.setBean(item);
    }

    protected void postExecution() {

    }

    class FormLayoutFactory extends WrappedFormLayoutFactory {

        @Override
        public AbstractComponent getLayout() {
            MVerticalLayout layout = new MVerticalLayout().withMargin(false);
            wrappedLayoutFactory = new DefaultDynaFormLayout(ProjectTypeConstants.TASK, TaskDefaultFormLayoutFactory.getAddForm());
            AbstractComponent gridLayout = wrappedLayoutFactory.getLayout();

            gridLayout.addStyleName(WebThemes.SCROLLABLE_CONTAINER);
            gridLayout.addStyleName("window-max-height");

            MButton saveBtn = new MButton(UserUIContext.getMessage(GenericI18Enum.BUTTON_SAVE), clickEvent -> {
                if (validateForm()) {
                    TaskService taskService = AppContextUtil.getSpringBean(TaskService.class);
                    Integer taskId;
                    if (bean.getId() == null) {
                        taskId = taskService.saveWithSession(bean, UserUIContext.getUsername());
                    } else {
                        taskService.updateWithSession(bean, UserUIContext.getUsername());
                        taskId = bean.getId();
                    }

                    TaskEditFormFieldFactory taskEditFormFieldFactory = (TaskEditFormFieldFactory) fieldFactory;

                    AttachmentUploadField uploadField = taskEditFormFieldFactory.getAttachmentUploadField();
                    String attachPath = AttachmentUtils.getProjectEntityAttachmentPath(AppUI.getAccountId(), bean.getProjectid(),
                            ProjectTypeConstants.TASK, "" + taskId);
                    uploadField.saveContentsToRepo(attachPath);

                    ProjectSubscribersComp subscribersComp = taskEditFormFieldFactory.getSubscribersComp();
                    List<String> followers = subscribersComp.getFollowers();
                    if (followers.size() > 0) {
                        List<MonitorItem> monitorItems = new ArrayList<>();
                        for (String follower : followers) {
                            MonitorItem monitorItem = new MonitorItem();
                            monitorItem.setSaccountid(AppUI.getAccountId());
                            monitorItem.setType(ProjectTypeConstants.TASK);
                            monitorItem.setTypeid(taskId + "");
                            monitorItem.setUsername(follower);
                            monitorItem.setExtratypeid(bean.getProjectid());
                            monitorItem.setCreatedtime(LocalDateTime.now());
                            monitorItems.add(monitorItem);
                        }
                        MonitorItemService monitorItemService = AppContextUtil.getSpringBean(MonitorItemService.class);
                        monitorItemService.saveMonitorItems(monitorItems);
                    }

                    postExecution();
                    EventBusFactory.getInstance().post(new TaskEvent.NewTaskAdded(TaskEditForm.this, taskId));
                    EventBusFactory.getInstance().post(new TicketEvent.NewTicketAdded(TaskEditForm.this,
                            ProjectTypeConstants.TASK, taskId));
                }
            }).withStyleName(WebThemes.BUTTON_ACTION).withIcon(VaadinIcons.CLIPBOARD)
                    .withClickShortcut(KeyCode.ENTER);

            MButton cancelBtn = new MButton(UserUIContext.getMessage(GenericI18Enum.BUTTON_CANCEL), clickEvent -> postExecution())
                    .withStyleName(WebThemes.BUTTON_OPTION);

            MCssLayout buttonControls = new MCssLayout(new MHorizontalLayout(cancelBtn, saveBtn).withStyleName(WebThemes.ALIGN_RIGHT)
                    .withMargin(new MarginInfo(true, false, false, false))).withFullWidth().withStyleName(WebThemes.BORDER_TOP);

            layout.with(gridLayout, buttonControls).expand(gridLayout);
            return layout;
        }
    }
}
