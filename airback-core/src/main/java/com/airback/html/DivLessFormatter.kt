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
package com.airback.html

import com.hp.gagawa.java.elements.Div
import com.hp.gagawa.java.elements.Text

/**
 * @author airback Ltd
 * @since 6.0.0
 */
class DivLessFormatter() : Div() {
    override fun write(): String {
        val b = StringBuilder()
        children.forEach { b.append(it.write()) }
        return b.toString()
    }

    companion object {
        @JvmField val EMPTY_SPACE = Text("&nbsp;")
    }
}