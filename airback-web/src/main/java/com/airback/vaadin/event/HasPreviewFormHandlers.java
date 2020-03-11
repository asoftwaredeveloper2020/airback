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
package com.airback.vaadin.event;

/**
 * Interface denote to have at least one instance of class
 * <code>PreviewFormHandler</code> in its concrete class
 *
 * @param <T>
 * @author airback Ltd.
 * @since 1.0
 */
public interface HasPreviewFormHandlers<T> {

    /**
     * @param handler
     */
    void addFormHandler(PreviewFormHandler<T> handler);
}