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
package com.airback.module.project.view.message;

import com.airback.core.airbackException;
import com.airback.core.SecureAccessException;
import com.airback.module.project.CurrentProjectVariables;
import com.airback.module.project.ProjectRolePermissionCollections;
import com.airback.module.project.domain.SimpleMessage;
import com.airback.module.project.event.MessageEvent;
import com.airback.module.project.service.MessageService;
import com.airback.module.project.view.ProjectBreadcrumb;
import com.airback.module.project.view.ProjectGenericPresenter;
import com.airback.module.project.view.ProjectView;
import com.airback.spring.AppContextUtil;
import com.airback.vaadin.AppUI;
import com.airback.vaadin.EventBusFactory;
import com.airback.vaadin.event.DefaultPreviewFormHandler;
import com.airback.vaadin.mvp.LoadPolicy;
import com.airback.vaadin.mvp.ScreenData;
import com.airback.vaadin.mvp.ViewManager;
import com.airback.vaadin.mvp.ViewScope;
import com.vaadin.ui.HasComponents;

/**
 * @author airback Ltd.
 * @since 1.0
 */
@LoadPolicy(scope = ViewScope.PROTOTYPE)
public class MessageReadPresenter extends ProjectGenericPresenter<MessageReadView> {
    private static final long serialVersionUID = 1L;

    public MessageReadPresenter() {
        super(MessageReadView.class);
    }

    @Override
    protected void postInitView() {
        view.getPreviewFormHandlers().addFormHandler(new DefaultPreviewFormHandler<SimpleMessage>() {

            @Override
            public void onCancel() {
                EventBusFactory.getInstance().post(new MessageEvent.GotoList(this, null));
            }
        });
    }

    @Override
    protected void onGo(HasComponents container, ScreenData<?> data) {
        if (CurrentProjectVariables.canRead(ProjectRolePermissionCollections.MESSAGES)) {
            ProjectView projectView = (ProjectView) container;
            projectView.gotoSubView(ProjectView.MESSAGE_ENTRY, view);

            if (data.getParams() instanceof Integer) {
                MessageService messageService = AppContextUtil.getSpringBean(MessageService.class);
                SimpleMessage message = messageService.findById((Integer) data.getParams(), AppUI.getAccountId());
                if (message != null) {
                    view.previewItem(message);

                    ProjectBreadcrumb breadCrumb = ViewManager.getCacheComponent(ProjectBreadcrumb.class);
                    breadCrumb.gotoMessage(message);
                }
            } else {
                throw new airbackException("Unhanddle this case yet");
            }
        } else {
            throw new SecureAccessException();
        }
    }
}
