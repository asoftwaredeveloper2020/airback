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
package com.airback.module.project.view.ticket;

import com.airback.core.SecureAccessException;
import com.airback.db.persistence.service.ISearchableService;
import com.airback.module.project.CurrentProjectVariables;
import com.airback.module.project.domain.ProjectTicket;
import com.airback.module.project.domain.criteria.ProjectTicketSearchCriteria;
import com.airback.module.project.service.ProjectTicketService;
import com.airback.module.project.view.ProjectBreadcrumb;
import com.airback.module.project.view.ProjectGenericListPresenter;
import com.airback.module.project.view.ProjectView;
import com.airback.spring.AppContextUtil;
import com.airback.vaadin.mvp.LoadPolicy;
import com.airback.vaadin.mvp.ScreenData;
import com.airback.vaadin.mvp.ViewManager;
import com.airback.vaadin.mvp.ViewScope;
import com.vaadin.ui.HasComponents;

/**
 * @author airback Ltd.
 * @since 1.0
 */
@LoadPolicy(scope = ViewScope.PROTOTYPE)
public class TicketDashboardPresenter extends ProjectGenericListPresenter<TicketDashboardView, ProjectTicketSearchCriteria, ProjectTicket> {
    private static final long serialVersionUID = 1L;

    private ProjectTicketService projectTicketService;

    public TicketDashboardPresenter() {
        super(TicketDashboardView.class);
        projectTicketService = AppContextUtil.getSpringBean(ProjectTicketService.class);
    }

    @Override
    public void doSearch(ProjectTicketSearchCriteria searchCriteria) {
        view.queryTickets(searchCriteria);
    }

    @Override
    protected void onGo(HasComponents container, ScreenData<?> data) {
        if (CurrentProjectVariables.canReadTicket()) {
            ProjectView projectView = (ProjectView) container;
            projectView.gotoSubView(ProjectView.TICKET_ENTRY, view);

            String query = (data != null && data.getParams() instanceof String) ? (String) data.getParams() : "";
            view.displayView(query);

            ProjectBreadcrumb breadCrumb = ViewManager.getCacheComponent(ProjectBreadcrumb.class);
            breadCrumb.gotoTicketDashboard(query);
        } else {
            throw new SecureAccessException();
        }
    }

    @Override
    public ISearchableService<ProjectTicketSearchCriteria> getSearchService() {
        return projectTicketService;
    }

    @Override
    protected void deleteSelectedItems() {

    }
}
