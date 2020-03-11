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
package com.airback.module.project.event

import com.airback.vaadin.event.ApplicationEvent
import com.airback.module.project.domain.SimpleInvoice

/**
 * @author airback Ltd
 * @since 6.0.0
 */
object InvoiceEvent {
    class GotoList(source: Any, val data: Any?) : ApplicationEvent(source)

    class NewInvoiceAdded(source: Any, val data: SimpleInvoice) : ApplicationEvent(source)

    class InvoiceUpdateAdded(source: Any, val data: SimpleInvoice) : ApplicationEvent(source)

    class InvoiceDelete(source: Any, val data: SimpleInvoice) : ApplicationEvent(source)

    class DisplayInvoiceView(source: Any, val data: SimpleInvoice) : ApplicationEvent(source)
}