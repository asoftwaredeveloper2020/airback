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
package com.airback.reporting

import net.sf.dynamicreports.jasper.builder.export.JasperXlsxExporterBuilder
import net.sf.dynamicreports.jasper.constant.JasperProperty
import net.sf.dynamicreports.report.exception.DRException

import java.io.OutputStream
import java.util.Locale

import net.sf.dynamicreports.report.builder.DynamicReports.export

/**
 * @author airback Ltd
 * @since 5.4.7
 */
class XlsReportBuilder internal constructor(locale: Locale, fieldBuilder: RpFieldsBuilder, classType: Class<*>, parameters: Map<String, Any>) : AbstractReportBuilder(locale, fieldBuilder, classType, parameters) {

    override fun initReport() {
        reportBuilder
                .setColumnTitleStyle(ReportStyles.instance().columnTitleStyle)
                .addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "2").ignorePageWidth()
                .ignorePagination()
    }

    @Throws(DRException::class)
    override fun toStream(outputStream: OutputStream) {
        val xlsExporter = export.xlsxExporter(outputStream)
                .setDetectCellType(true).setIgnorePageMargins(true)
                .setWhitePageBackground(false).setRemoveEmptySpaceBetweenColumns(true)
        reportBuilder.toXlsx(xlsExporter)
    }
}
