package com.example.ui.view.list;

import com.example.backend.entity.Company;
import com.example.backend.entity.Contact;
import com.example.backend.service.CompanyService;
import com.example.backend.service.ContactService;
import com.example.ui.MainLayout;
import com.example.ui.events.CloseEvent;
import com.example.ui.events.DeleteEvent;
import com.example.ui.events.SaveEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.sql.SQLException;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Contact | Vaadin CRM")
public class ListView extends VerticalLayout {
  private ContactService contactService;
  private Grid<Contact> grid = new Grid<>(Contact.class);
  private TextField filterText = new TextField();
  private ContactForm contactForm;

  public ListView(ContactService contactService, CompanyService companyService) {
    this.contactService = contactService;
    addClassName("list-view");
    setSizeFull();
    configureGrid();


    try {
      contactForm = new ContactForm(companyService.findAll());
      contactForm.addListener(SaveEvent.class, this::saveContact);
      contactForm.addListener(DeleteEvent.class, this::deleteContact);
      contactForm.addListener(CloseEvent.class,  e -> closeEditor());

      HorizontalLayout content = new HorizontalLayout(grid, contactForm);
      content.addClassName("content");
      add(getToolbar(), content);
      updateList();
//      closeEditor();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }


    }

  private void deleteContact(DeleteEvent event) {
    contactService.delete(event.getContact());
    updateList();
    closeEditor();
  }

  private void saveContact(SaveEvent event) {
    contactService.save(event.getContact());
    updateList();
    closeEditor();
  }

  private void closeEditor() {
    contactForm.setContact(null);
    contactForm.setVisible(false);
    grid.setVisible(true);
    removeClassName("editing");
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

      grid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));
    }

  private void editContact(Contact contact) {
    if(contact == null){
      closeEditor();
    } else {
      contactForm.setContact(contact);
      contactForm.setVisible(true);
      addClassName("editing");
    }
  }

  private HorizontalLayout getToolbar(){
      filterText.setPlaceholder("Filter by name...");
      filterText.setClearButtonVisible(true);
      filterText.setValueChangeMode(ValueChangeMode.LAZY);
      filterText.addValueChangeListener(e -> updateList());

      Button addContactButton = new Button("Add contact");
      addContactButton.addClickListener(click -> addContact());

      HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
      toolbar.addClassName("toolbar");
      return toolbar;
    }

  private void addContact() {
   grid.asSingleSelect().clear();
   editContact(new Contact());
  }

  private void updateList(){
      grid.setItems(contactService.findAll(filterText.getValue()));
    }
}
