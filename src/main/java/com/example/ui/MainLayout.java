package com.example.ui;

import com.example.ui.view.dashboard.DashboardView;
import com.example.ui.view.list.ListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {
  public MainLayout() {
    createHeader();
    createDrawer();
  }

  private void createDrawer() {
    RouterLink listLink = new RouterLink("List", ListView.class);
    RouterLink dashboardLink = new RouterLink("Dashboard", DashboardView.class);
    listLink.setHighlightCondition(HighlightConditions.sameLocation());
    VerticalLayout links = new VerticalLayout(listLink, dashboardLink);
    
    addToDrawer(links);
  }

  private void createHeader() {
    H1 logo = new H1("CRM");
    logo.addClassName("logo");

    HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
    header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    header.setWidth("100%");
    header.addClassName("header");

    addToNavbar(header);
  }
}
