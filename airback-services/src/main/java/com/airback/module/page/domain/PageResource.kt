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
 * along with this program.  If not, see <http:></http:>//www.gnu.org/licenses/>.
 */
package com.airback.module.page.domain

import com.airback.core.arguments.NotBindable

import java.util.Calendar

/**
 * @author airback Ltd.
 * @since 4.4.0
 */
open class PageResource {
    @NotBindable
    var createdTime: Calendar? = null

    @NotBindable
    var createdUser: String? = null

    @NotBindable
    var path = ""
}
