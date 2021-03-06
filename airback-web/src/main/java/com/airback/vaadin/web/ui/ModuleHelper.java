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
package com.airback.vaadin.web.ui;

import com.airback.module.project.view.ProjectModule;
import com.airback.module.user.accountsettings.view.AccountModule;
import com.airback.vaadin.mvp.IModule;
import com.airback.vaadin.ui.airbackSession;

/**
 * @author airback Ltd.
 * @since 1.0
 */
public class ModuleHelper {

    public static void setCurrentModule(IModule module) {
        airbackSession.putCurrentUIVariable(airbackSession.CURRENT_MODULE, module);
    }

    public static IModule getCurrentModule() {
        return (IModule) airbackSession.getCurrentUIVariable(airbackSession.CURRENT_MODULE);
    }

    public static boolean isCurrentProjectModule() {
        IModule module = getCurrentModule();
        return (module != null) && (module instanceof ProjectModule);
    }

    public static boolean isCurrentAccountModule() {
        IModule module = getCurrentModule();
        return (module != null) && (module instanceof AccountModule);
    }
}
