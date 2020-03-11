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
package com.airback.vaadin.ui.formatter

import com.airback.core.utils.StringUtils
import com.airback.i18n.LocalizationHelper
import com.airback.module.user.domain.SimpleUser
import com.airback.vaadin.UserUIContext

/**
 * @author airback Ltd
 * @since 5.3.4
 */
class I18nHistoryFieldFormat(private val enumCls: Class<out Enum<*>>) : HistoryFieldFormat {

    override fun toString(value: String) = toString(UserUIContext.getUser(), value, true, "")

    override fun toString(currentViewUser: SimpleUser, value: String, displayAsHtml: Boolean, msgIfBlank: String) =
            if (StringUtils.isNotBlank(value)) LocalizationHelper.getMessage(currentViewUser.locale, enumCls, value) else msgIfBlank
}
