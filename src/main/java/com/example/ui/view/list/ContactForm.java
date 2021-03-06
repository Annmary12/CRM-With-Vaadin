package com.example.ui.view.list;

import com.example.backend.entity.Company;
import com.example.backend.entity.Contact;
import com.example.ui.events.CloseEvent;
import com.example.ui.events.DeleteEvent;
import com.example.ui.events.SaveEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import java.sql.SQLException;
import java.util.List;

public class ContactForm extends FormLayout {
  private  Contact contact;
  TextField firstName = new TextField("First name");
  TextField lastName = new TextField("Last name");
  EmailField email = new EmailField("Email");
  ComboBox<Contact.Status> status = new ComboBox<>("Status");
  ComboBox<Company> company = new ComboBox<>("Company");

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Close");

  Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);

  public ContactForm(List<Company> companies) throws SQLException {
    addClassName("contact-form");
    binder.bindInstanceFields(this);

    status.setItems(Contact.Status.values());
    company.setItems(companies);
    company.setItemLabelGenerator(Company::getName);

    add(
        firstName,
        lastName,
        email,
        company,
        status,
        createButtonLayout()
    );
  }

  private HorizontalLayout createButtonLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(click ->  validateAndSave());
    delete.addClickListener(click -> fireEvent(new DeleteEvent(this, contact)));
    close.addClickListener((click -> fireEvent(new CloseEvent(this))));

    return new HorizontalLayout(save, delete, close);
  }

  private void validateAndSave() {
    try {
      binder.writeBean(contact);
      fireEvent(new SaveEvent(this, contact));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  public void setContact(Contact contact){
    this.contact = contact;
    binder.readBean(contact);
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}
