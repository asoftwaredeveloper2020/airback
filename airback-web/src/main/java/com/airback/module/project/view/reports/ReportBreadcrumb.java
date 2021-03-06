package com.airback.module.project.view.reports;

import com.airback.module.project.ProjectLinkGenerator;
import com.airback.module.project.event.ReportEvent;
import com.airback.module.project.i18n.BreadcrumbI18nEnum;
import com.airback.module.project.i18n.ProjectCommonI18nEnum;
import com.airback.module.project.i18n.ProjectReportI18nEnum;
import com.airback.vaadin.AppUI;
import com.airback.vaadin.EventBusFactory;
import com.airback.vaadin.UserUIContext;
import com.airback.vaadin.mvp.CacheableComponent;
import com.airback.vaadin.mvp.ViewComponent;
import com.airback.vaadin.ui.ELabel;
import com.airback.vaadin.web.ui.WebThemes;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import java.time.LocalDate;

/**
 * @author airback Ltd
 * @since 5.3.0
 */
@ViewComponent
public class ReportBreadcrumb extends MHorizontalLayout implements CacheableComponent {

    public ReportBreadcrumb() {
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
    }

    public void gotoReportDashboard() {
        removeAllComponents();
        this.addComponent(ELabel.html(VaadinIcons.HOME.getHtml()));
        this.addComponent(ELabel.html(VaadinIcons.ANGLE_RIGHT.getHtml()));
        this.addComponent(new MButton(UserUIContext.getMessage(ProjectCommonI18nEnum.VIEW_REPORTS)).withStyleName(WebThemes.BUTTON_LINK));
        AppUI.addFragment("project/reports", UserUIContext.getMessage(ProjectCommonI18nEnum.VIEW_REPORTS));
    }

    public void gotoStandupList(LocalDate onDate) {
        removeAllComponents();
        this.addComponent(ELabel.html(VaadinIcons.HOME.getHtml()));
        this.addComponent(ELabel.html(VaadinIcons.ANGLE_RIGHT.getHtml()));
        this.addComponent(new MButton(UserUIContext.getMessage(ProjectCommonI18nEnum.VIEW_REPORTS), new GotoReportsListener()).withStyleName(WebThemes.BUTTON_LINK));
        this.addComponent(ELabel.html(VaadinIcons.ANGLE_RIGHT.getHtml()));
        this.addComponent(new MButton(UserUIContext.getMessage(ProjectCommonI18nEnum.VIEW_STANDUP)).withStyleName(WebThemes.BUTTON_LINK));
        if (onDate == null) {
            AppUI.addFragment("project/reports/standup/list/",
                    UserUIContext.getMessage(BreadcrumbI18nEnum.FRA_STANDUP));
        } else {
            AppUI.addFragment("project/reports/standup/list/" + UserUIContext.formatDate(onDate).replace('/', '-'),
                    UserUIContext.getMessage(BreadcrumbI18nEnum.FRA_STANDUP));
        }
    }

    public void gotoTimesheetReport() {
        removeAllComponents();
        this.addComponent(ELabel.html(VaadinIcons.HOME.getHtml()));
        this.addComponent(ELabel.html(VaadinIcons.ANGLE_RIGHT.getHtml()));
        this.addComponent(new MButton(UserUIContext.getMessage(ProjectCommonI18nEnum.VIEW_REPORTS), new GotoReportsListener()).withStyleName(WebThemes.BUTTON_LINK));
        this.addComponent(ELabel.html(VaadinIcons.ANGLE_RIGHT.getHtml()));
        this.addComponent(new MButton(UserUIContext.getMessage(ProjectReportI18nEnum.REPORT_TIMESHEET)).withStyleName(WebThemes.BUTTON_LINK));
    }

    public void gotoWeeklyTimingReport() {
        removeAllComponents();
        this.addComponent(ELabel.html(VaadinIcons.HOME.getHtml()));
        this.addComponent(ELabel.html(VaadinIcons.ANGLE_RIGHT.getHtml()));
        this.addComponent(new MButton(UserUIContext.getMessage(ProjectCommonI18nEnum.VIEW_REPORTS), new GotoReportsListener()).withStyleName(WebThemes.BUTTON_LINK));
        this.addComponent(ELabel.html(VaadinIcons.ANGLE_RIGHT.getHtml()));
        this.addComponent(new MButton(UserUIContext.getMessage(ProjectReportI18nEnum.REPORT_HOURS_SPENT)).withStyleName(WebThemes.BUTTON_LINK));
    }

    public void gotoUserWorkloadReport() {
        removeAllComponents();
        this.addComponent(ELabel.html(VaadinIcons.HOME.getHtml()));
        this.addComponent(ELabel.html(VaadinIcons.ANGLE_RIGHT.getHtml()));
        this.addComponent(new MButton(UserUIContext.getMessage(ProjectCommonI18nEnum.VIEW_REPORTS), new GotoReportsListener()).withStyleName(WebThemes.BUTTON_LINK));
        this.addComponent(ELabel.html(VaadinIcons.ANGLE_RIGHT.getHtml()));
        this.addComponent(new MButton(UserUIContext.getMessage(ProjectReportI18nEnum.REPORT_TICKET_ASSIGNMENT)).withStyleName(WebThemes.BUTTON_LINK));
        AppUI.addFragment(ProjectLinkGenerator.generateUsersWorkloadReportLink(), UserUIContext.getMessage(ProjectReportI18nEnum.REPORT_TICKET_ASSIGNMENT));
    }

    private static class GotoReportsListener implements Button.ClickListener {
        private static final long serialVersionUID = 1L;

        @Override
        public void buttonClick(Button.ClickEvent event) {
            EventBusFactory.getInstance().post(new ReportEvent.GotoConsole(this));
        }
    }
}
