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
package com.airback.reporting.expression

import net.sf.dynamicreports.report.definition.ReportParameters

/**
 * @author airback Ltd.
 * @since 4.1.2
 */
class MailExpression(field: String) : SimpleFieldExpression<String>(field) {

    override fun evaluate(reportParameters: ReportParameters): String {
        val stringValue = reportParameters.getFieldValue<Any>(field).toString()
        return "mailto:$stringValue"
    }
}
