package com.example.ui.events;

import com.example.backend.entity.Contact;
import com.example.ui.ContactForm;
import com.vaadin.flow.component.ComponentEvent;

public abstract class ContactFormEvent extends ComponentEvent<ContactForm> {
  private Contact contact;

  protected ContactFormEvent(ContactForm source, Contact contact){
    super(source, false);
    this.contact = contact;
  }

  public Contact getContact() {
    return contact;
  }
}

