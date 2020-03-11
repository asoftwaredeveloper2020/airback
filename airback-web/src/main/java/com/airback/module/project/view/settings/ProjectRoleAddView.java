/**
 * Copyright © airback
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.airback.module.project.view.settings;

import com.airback.module.project.domain.ProjectRole;
import com.airback.security.PermissionMap;
import com.airback.vaadin.event.HasEditFormHandlers;
import com.airback.vaadin.mvp.IFormAddView;

/**
 * @author airback Ltd.
 * @since 1.0
 */
public interface ProjectRoleAddView extends IFormAddView<ProjectRole> {

    HasEditFormHandlers<ProjectRole> getEditFormHandlers();

    PermissionMap getPermissionMap();
}
