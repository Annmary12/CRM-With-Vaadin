package com.example.ui;

import com.example.backend.entity.Company;
import com.example.backend.entity.Contact;
import com.example.backend.service.ContactService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {
  private ContactService contactService;
  private Grid<Contact> grid = new Grid<>(Contact.class);
  private TextField filterText = new TextField();
  private ContactForm contactForm;

  public MainView(ContactService contactService) {
    this.contactService = contactService;
    addClassName("list-view");
    setSizeFull();
    configureFilter();
    configureGrid();

    contactForm = new ContactForm();

    Div content = new Div(grid, contactForm);
    content.addClassName("content");
    add(filterText, content);
    updateList();
    }

    private void configureGrid(){
      grid.addClassName("contact-grid");
//      grid.setWidth("20");
      grid.setSizeFull();
      grid.setColumns("firstName", "lastName", "email", "status");
      grid.addColumn(contact -> {
        Company company = contact.getCompany();
        return company == null ? "-" : company.getName();
      }).setHeader("Company");
      grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void configureFilter(){
      filterText.setPlaceholder("Filter by name...");
      filterText.setClearButtonVisible(true);
      filterText.setValueChangeMode(ValueChangeMode.LAZY);
      filterText.addValueChangeListener(e -> updateList());
    }

    private void updateList(){
      grid.setItems(contactService.findAll(filterText.getValue()));
    }
}
