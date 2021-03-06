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
package com.airback.module.project.view.user;

import com.airback.common.i18n.GenericI18Enum;
import com.airback.core.utils.DateTimeUtils;
import com.airback.db.arguments.RangeDateSearchField;
import com.airback.db.arguments.SearchField;
import com.airback.db.arguments.SetSearchField;
import com.airback.db.arguments.StringSearchField;
import com.airback.module.project.domain.ProjectTicket;
import com.airback.module.project.domain.criteria.ProjectTicketSearchCriteria;
import com.airback.module.project.i18n.ProjectI18nEnum;
import com.airback.module.project.service.ProjectTicketService;
import com.airback.module.project.view.ticket.TicketRowDisplayHandler;
import com.airback.spring.AppContextUtil;
import com.airback.vaadin.UserUIContext;
import com.airback.vaadin.web.ui.DefaultBeanPagedList;
import com.airback.vaadin.web.ui.Depot;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;

import java.time.LocalDate;
import java.util.List;

/**
 * @author airback Ltd
 * @since 5.2.4
 */
public class UserUnresolvedTicketWidget extends Depot {
    private ProjectTicketSearchCriteria searchCriteria;
    private DefaultBeanPagedList<ProjectTicketService, ProjectTicketSearchCriteria, ProjectTicket> ticketList;
    private String title = "";

    public UserUnresolvedTicketWidget() {
        super("", new CssLayout());
        this.setWidth("100%");
        final CheckBox myItemsSelection = new CheckBox(UserUIContext.getMessage(GenericI18Enum.OPT_MY_ITEMS));
        myItemsSelection.addValueChangeListener(valueChangeEvent -> {
            boolean isMyItemsOption = myItemsSelection.getValue();
            if (searchCriteria != null) {
                if (isMyItemsOption) {
                    searchCriteria.setAssignUser(StringSearchField.and(UserUIContext.getUsername()));
                } else {
                    searchCriteria.setAssignUser(null);
                }
                updateSearchResult();
            }
        });
        ticketList = new DefaultBeanPagedList<ProjectTicketService, ProjectTicketSearchCriteria, ProjectTicket>
                (AppContextUtil.getSpringBean(ProjectTicketService.class), new TicketRowDisplayHandler(true), 10) {
            @Override
            protected String stringWhenEmptyList() {
                return UserUIContext.getMessage(ProjectI18nEnum.OPT_NO_TICKET);
            }
        };
        this.addHeaderElement(myItemsSelection);
        this.bodyContent.addComponent(ticketList);
    }

    public void displayUnresolvedAssignmentsThisWeek(List<Integer> projectIds) {
        title = UserUIContext.getMessage(ProjectI18nEnum.OPT_UNRESOLVED_TICKET_THIS_WEEK);
        searchCriteria = new ProjectTicketSearchCriteria();
        searchCriteria.setOpen(new SearchField());
        searchCriteria.setProjectIds(new SetSearchField<>(projectIds));
        LocalDate now = LocalDate.now();
        LocalDate[] bounceDateOfWeek = DateTimeUtils.getBounceDatesOfWeek(now);
        RangeDateSearchField range = new RangeDateSearchField(bounceDateOfWeek[0], bounceDateOfWeek[1]);
        searchCriteria.setDateInRange(range);
        updateSearchResult();
    }

    public void displayUnresolvedAssignmentsNextWeek(List<Integer> projectIds) {
        title = UserUIContext.getMessage(ProjectI18nEnum.OPT_UNRESOLVED_TICKET_NEXT_WEEK);
        searchCriteria = new ProjectTicketSearchCriteria();
        searchCriteria.setOpen(new SearchField());
        searchCriteria.setProjectIds(new SetSearchField<>(projectIds));
        LocalDate now = LocalDate.now();
        now = now.plusDays(7);
        LocalDate[] bounceDateOfWeek = DateTimeUtils.getBounceDatesOfWeek(now);
        RangeDateSearchField range = new RangeDateSearchField(bounceDateOfWeek[0], bounceDateOfWeek[1]);
        searchCriteria.setDateInRange(range);
        updateSearchResult();
    }

    private void updateSearchResult() {
        ticketList.setSearchCriteria(searchCriteria);
        this.setTitle(String.format(title, ticketList.getTotalCount()));
    }
}
