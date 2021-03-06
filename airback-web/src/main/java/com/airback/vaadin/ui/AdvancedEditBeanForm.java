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
package com.airback.vaadin.ui;

import com.airback.vaadin.event.HasEditFormHandlers;
import com.airback.vaadin.event.IEditFormHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic attachForm with java bean as datasource. It includes validation
 * against bean input
 *
 * @param <B> java bean as datasource map with attachForm fields
 * @author airback Ltd.
 * @since 2.0
 */
public class AdvancedEditBeanForm<B> extends GenericBeanForm<B> implements HasEditFormHandlers<B> {
    private static final long serialVersionUID = 1L;

    private List<IEditFormHandler<B>> editFormHandlers = new ArrayList<>();

    /**
     * Validate attachForm against data
     *
     * @return true if data is valid, otherwise return false and show result to
     * attachForm
     */
    public boolean validateForm() {
        return fieldFactory.commit();
    }

    @Override
    public void addFormHandler(IEditFormHandler<B> editFormHandler) {
        editFormHandlers.add(editFormHandler);
    }

    public void fireSaveForm() {
        editFormHandlers.forEach(editFormHandler -> editFormHandler.onSave(this.getBean()));
    }

    public void fireSaveAndNewForm() {
        editFormHandlers.forEach(editFormHandler -> editFormHandler.onSaveAndNew(this.getBean()));
    }

    public void fireCancelForm() {
        editFormHandlers.forEach(IEditFormHandler::onCancel);
    }
}
