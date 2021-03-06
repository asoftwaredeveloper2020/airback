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
package com.airback.module.project.ui.form;

import com.airback.common.i18n.GenericI18Enum;
import com.airback.module.ecm.domain.Content;
import com.airback.module.ecm.service.ResourceService;
import com.airback.module.file.AttachmentUtils;
import com.airback.spring.AppContextUtil;
import com.airback.vaadin.AppUI;
import com.airback.vaadin.UserUIContext;
import com.airback.vaadin.ui.ELabel;
import com.airback.vaadin.ui.IgnoreBindingField;
import com.airback.vaadin.web.ui.AttachmentDisplayComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @author airback Ltd.
 * @since 4.5.3
 */
public class ProjectFormAttachmentDisplayField extends IgnoreBindingField {
    private static final long serialVersionUID = 1L;

    private int projectId;
    private String type;
    private int typeId;

    public ProjectFormAttachmentDisplayField(final int projectId, final String type, final int typeId) {
        this.projectId = projectId;
        this.type = type;
        this.typeId = typeId;
    }

    @Override
    protected Component initContent() {
        ResourceService resourceService = AppContextUtil.getSpringBean(ResourceService.class);
        List<Content> attachments = resourceService.getContents(AttachmentUtils.
                getProjectEntityAttachmentPath(AppUI.getAccountId(), projectId, type, "" + typeId));

        if (CollectionUtils.isEmpty(attachments)) {
            return new ELabel(UserUIContext.getMessage(GenericI18Enum.EXT_NO_ITEM));
        } else {
            return new AttachmentDisplayComponent(attachments);
        }
    }

    @Override
    protected void doSetValue(Object o) {

    }

    @Override
    public Object getValue() {
        return null;
    }
}
