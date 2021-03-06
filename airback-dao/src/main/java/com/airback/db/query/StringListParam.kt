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
package com.airback.db.query

import com.airback.common.i18n.QueryI18nEnum.IN
import com.airback.common.i18n.QueryI18nEnum.NOT_IN
import com.airback.db.arguments.SetValueSearchField

/**
 * @author airback Ltd.
 * @since 4.0
 */
class StringListParam(id: String, table: String, column: String, var values: Set<String>?) : ColumnParam(id, table, column) {

    fun buildStringParamInList(oper: String, values: Set<*>): SetValueSearchField =
            SetValueSearchField(oper, "$table.$column in ", values)

    fun buildStringParamNotInList(oper: String, values: Set<*>): SetValueSearchField =
            SetValueSearchField(oper, "$table.$column not in", values)

    companion object {

        @JvmField
        val OPTIONS = arrayOf(IN, NOT_IN)
    }
}