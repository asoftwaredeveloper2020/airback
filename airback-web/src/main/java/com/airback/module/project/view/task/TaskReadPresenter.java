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

import com.airback.common.i18n.GenericI18Enum;
import com.airback.module.project.CurrentProjectVariables;
import com.airback.module.project.ProjectRolePermissionCollections;
import com.airback.module.project.ProjectTypeConstants;
import com.airback.module.project.domain.SimpleTask;
import com.airback.module.project.domain.Task;
import com.airback.module.project.event.TaskEvent;
import com.airback.module.project.event.TicketEvent;
import com.airback.module.project.service.TaskService;
import com.airback.module.project.service.TicketKeyService;
import com.airback.module.project.view.ProjectBreadcrumb;
import com.airback.module.project.view.ProjectView;
import com.airback.spring.AppContextUtil;
import com.airback.vaadin.AppUI;
import com.airback.vaadin.EventBusFactory;
import com.airback.vaadin.UserUIContext;
import com.airback.vaadin.event.DefaultPreviewFormHandler;
import com.airback.vaadin.mvp.LoadPolicy;
import com.airback.vaadin.mvp.ScreenData;
import com.airback.vaadin.mvp.ViewManager;
import com.airback.vaadin.mvp.ViewScope;
import com.airback.vaadin.reporting.FormReportLayout;
import com.airback.vaadin.reporting.PrintButton;
import com.airback.vaadin.ui.NotificationUtil;
import com.airback.vaadin.web.ui.AbstractPresenter;
import com.airback.vaadin.web.ui.ConfirmDialogExt;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.UI;

/**
 * @author airback Ltd.
 * @since 1.0
 */
@LoadPolicy(scope = ViewScope.PROTOTYPE)
public class TaskReadPresenter extends AbstractPresenter<TaskReadView> {
    private static final long serialVersionUID = 1L;

    public TaskReadPresenter() {
        super(TaskReadView.class);
    }

    @Override
    protected void postInitView() {
        this.view.getPreviewFormHandlers().addFormHandler(new DefaultPreviewFormHandler<SimpleTask>() {

            @Override
            public void onAssign(SimpleTask data) {
                UI.getCurrent().addWindow(new AssignTaskWindow(data));
            }

            @Override
            public void onEdit(SimpleTask data) {
                EventBusFactory.getInstance().post(new TaskEvent.GotoEdit(this, data));
            }

            @Override
            public void onAdd(SimpleTask data) {
                EventBusFactory.getInstance().post(new TaskEvent.GotoAdd(this, null));
            }

            @Override
            public void onPrint(Object source, SimpleTask data) {
                PrintButton btn = (PrintButton) source;
                btn.doPrint(data, new FormReportLayout(ProjectTypeConstants.TASK, Task.Field.name.name(),
                        TaskDefaultFormLayoutFactory.getReadForm(), Task.Field.name.name(), Task.Field.id.name(),
                        Task.Field.parenttaskid.name(), "selected"));
            }

            @Override
            public void onDelete(SimpleTask data) {
                ConfirmDialogExt.show(UI.getCurrent(),
                        UserUIContext.getMessage(GenericI18Enum.DIALOG_DELETE_TITLE, AppUI.getSiteName()),
                        UserUIContext.getMessage(GenericI18Enum.DIALOG_DELETE_SINGLE_ITEM_MESSAGE),
                        UserUIContext.getMessage(GenericI18Enum.ACTION_YES),
                        UserUIContext.getMessage(GenericI18Enum.ACTION_NO),
                        confirmDialog -> {
                            if (confirmDialog.isConfirmed()) {
                                TaskService taskService = AppContextUtil.getSpringBean(TaskService.class);
                                taskService.removeWithSession(data, UserUIContext.getUsername(), AppUI.getAccountId());
                                EventBusFactory.getInstance().post(new TicketEvent.GotoDashboard(this, null));
                            }
                        });
            }

            @Override
            public void onClone(SimpleTask data) {
                Task cloneData = (Task) data.copy();
                cloneData.setId(null);
                EventBusFactory.getInstance().post(new TaskEvent.GotoEdit(this, cloneData));
            }

            @Override
            public void onCancel() {
                EventBusFactory.getInstance().post(new TicketEvent.GotoDashboard(this, null));
            }

            @Override
            public void gotoNext(SimpleTask task) {
                TicketKeyService ticketKeyService = AppContextUtil.getSpringBean(TicketKeyService.class);
                Integer nextId = ticketKeyService.getNextKey(task.getProjectid(), task.getTicketKey());
                if (nextId != null) {
                    EventBusFactory.getInstance().post(new TicketEvent.GotoRead(this, task.getProjectid(), nextId));
                } else {
                    NotificationUtil.showGotoLastRecordNotification();
                }
            }

            @Override
            public void gotoPrevious(SimpleTask task) {
                TicketKeyService ticketKeyService = AppContextUtil.getSpringBean(TicketKeyService.class);
                Integer nextId = ticketKeyService.getPreviousKey(task.getProjectid(), task.getTicketKey());
                if (nextId != null) {
                    EventBusFactory.getInstance().post(new TicketEvent.GotoRead(this, task.getProjectid(), nextId));
                } else {
                    NotificationUtil.showGotoFirstRecordNotification();
                }
            }
        });
    }

    @Override
    protected void onGo(HasComponents container, ScreenData<?> data) {
        if (CurrentProjectVariables.canRead(ProjectRolePermissionCollections.TASKS)) {
            ProjectView projectView = (ProjectView) container;
            projectView.gotoSubView(ProjectView.TICKET_ENTRY, view);

            SimpleTask task = null;
            if (data.getParams() instanceof Integer) {
                TaskService taskService = AppContextUtil.getSpringBean(TaskService.class);
                task = taskService.findById((Integer) data.getParams(), AppUI.getAccountId());
            } else if (data.getParams() instanceof SimpleTask) {
                task = (SimpleTask) data.getParams();
            }

            if (task != null) {
                view.previewItem(task);
                ProjectBreadcrumb breadCrumb = ViewManager.getCacheComponent(ProjectBreadcrumb.class);
                breadCrumb.gotoTaskRead(task);
            } else {
                NotificationUtil.showRecordNotExistNotification();
            }
        } else {
            NotificationUtil.showMessagePermissionAlert();
        }
    }
}