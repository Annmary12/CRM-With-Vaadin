package com.example.ui;

import com.example.backend.entity.Company;
import com.example.backend.entity.Contact;
import com.example.backend.service.ContactService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class MainView extends VerticalLayout {
  private ContactService contactService;
  private Grid<Contact> grid = new Grid<>(Contact.class);

  public MainView(ContactService contactService) {
    this.contactService = contactService;
    addClassName("list-view");
    setSizeFull();
    configureGrid();

    add(grid);
    updateList();
    }

    private void configureGrid(){
      grid.addClassName("contact-grid");
      grid.setSizeFull();
      grid.setColumns("firstName", "lastName", "email", "status");
      grid.addColumn(contact -> {
        Company company = contact.getCompany();
        return company == null ? "-" : company.getName();
      }).setHeader("Company");
      grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateList(){
      grid.setItems(contactService.findAll());
    }
}
