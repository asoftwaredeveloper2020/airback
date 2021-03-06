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
package com.airback.module.user.accountsettings.team.view;

import com.airback.common.i18n.GenericI18Enum;
import com.airback.module.user.accountsettings.fielddef.RoleTableFieldDef;
import com.airback.module.user.domain.SimpleRole;
import com.airback.module.user.domain.criteria.RoleSearchCriteria;
import com.airback.security.RolePermissionCollections;
import com.airback.vaadin.UserUIContext;
import com.airback.vaadin.event.HasMassItemActionHandler;
import com.airback.vaadin.event.HasSearchHandlers;
import com.airback.vaadin.event.HasSelectableItemHandlers;
import com.airback.vaadin.event.HasSelectionOptionHandlers;
import com.airback.vaadin.mvp.AbstractVerticalPageView;
import com.airback.vaadin.mvp.ViewComponent;
import com.airback.vaadin.ui.DefaultMassItemActionHandlerContainer;
import com.airback.vaadin.web.ui.SelectionOptionButton;
import com.airback.vaadin.web.ui.WebThemes;
import com.airback.vaadin.web.ui.table.AbstractPagedBeanTable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.util.Arrays;

/**
 * @author airback Ltd.
 * @since 1.0
 */
@ViewComponent
public class RoleListViewImpl extends AbstractVerticalPageView implements RoleListView {
    private static final long serialVersionUID = 1L;

    private RoleSearchPanel searchPanel;
    private SelectionOptionButton selectOptionButton;
    private RoleTableDisplay tableItem;
    private VerticalLayout listLayout;
    private DefaultMassItemActionHandlerContainer tableActionControls;
    private Label selectedItemsNumberLabel = new Label();

    public RoleListViewImpl() {
        this.setMargin(new MarginInfo(false, true, false, true));

        searchPanel = new RoleSearchPanel();
        listLayout = new MVerticalLayout().withSpacing(false).withMargin(false);
        this.with(searchPanel, listLayout);
        this.generateDisplayTable();
    }

    private void generateDisplayTable() {
        tableItem = new RoleTableDisplay(RoleTableFieldDef.selected,
                Arrays.asList(RoleTableFieldDef.rolename, RoleTableFieldDef.members,
                        RoleTableFieldDef.isDefault, RoleTableFieldDef.description));
        listLayout.addComponent(constructTableActionControls());
        listLayout.addComponent(tableItem);
    }

    @Override
    public HasSearchHandlers<RoleSearchCriteria> getSearchHandlers() {
        return this.searchPanel;
    }

    private ComponentContainer constructTableActionControls() {
        MHorizontalLayout layout = new MHorizontalLayout();
        MCssLayout layoutWrapper = new MCssLayout(layout).withFullWidth().withStyleName(WebThemes.TABLE_ACTION_CONTROLS);

        selectOptionButton = new SelectionOptionButton(tableItem);
        layout.addComponent(selectOptionButton);

        tableActionControls = new DefaultMassItemActionHandlerContainer();
        if (UserUIContext.canAccess(RolePermissionCollections.ACCOUNT_ROLE)) {
            tableActionControls.addDeleteActionItem();
        }
        tableActionControls.addDownloadPdfActionItem();
        tableActionControls.addDownloadExcelActionItem();
        tableActionControls.addDownloadCsvActionItem();

        layout.with(tableActionControls, selectedItemsNumberLabel).withAlign(selectedItemsNumberLabel, Alignment.MIDDLE_LEFT);
        return layoutWrapper;
    }

    @Override
    public void enableActionControls(int numOfSelectedItems) {
        tableActionControls.setVisible(true);
        selectedItemsNumberLabel.setValue(UserUIContext.getMessage(GenericI18Enum.TABLE_SELECTED_ITEM_TITLE, numOfSelectedItems));
    }

    @Override
    public void disableActionControls() {
        tableActionControls.setVisible(false);
        selectOptionButton.setSelectedCheckbox(false);
        this.selectedItemsNumberLabel.setValue("");
    }

    @Override
    public void showNoItemView() {

    }

    @Override
    public HasSelectionOptionHandlers getOptionSelectionHandlers() {
        return selectOptionButton;
    }

    @Override
    public HasMassItemActionHandler getPopupActionHandlers() {
        return tableActionControls;
    }

    @Override
    public HasSelectableItemHandlers<SimpleRole> getSelectableItemHandlers() {
        return this.tableItem;
    }

    @Override
    public AbstractPagedBeanTable<RoleSearchCriteria, SimpleRole> getPagedBeanGrid() {
        return this.tableItem;
    }
}
