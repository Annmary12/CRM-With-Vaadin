package com.example.ui.view.dashboard;

import com.example.backend.service.CompanyService;
import com.example.backend.service.ContactService;
import com.example.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Map;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | CRM")
public class DashboardView extends VerticalLayout {
  private ContactService contactService;
  private CompanyService companyService;

  public DashboardView(ContactService contactService, CompanyService companyService){
    this.companyService = companyService;
    this.contactService = contactService;
    addClassName("dashboard-view");
    setDefaultHorizontalComponentAlignment(Alignment.CENTER);

    add(getContactStats(), getCompanyChart());
  }

  private Component getContactStats(){
    Span span = new Span(contactService.count() + " contacts");
    span.addClassName("contact-stats");
    return span;
  }

  private Chart getCompanyChart() {
    Chart chart = new Chart(ChartType.PIE);
    DataSeries dataSeries = new DataSeries();
    Map<String, Integer> companies = companyService.getStats();
    companies.forEach((company, employees) -> dataSeries.add(new DataSeriesItem(company, employees)));
    chart.getConfiguration().setSeries(dataSeries);
    return chart;
  }

}
