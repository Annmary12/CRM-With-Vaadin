package com.example.ui.events;

import com.example.backend.entity.Contact;
import com.example.ui.ContactForm;
import com.example.ui.events.ContactFormEvent;

public class SaveEvent extends ContactFormEvent {

  public SaveEvent(ContactForm source, Contact contact) {
    super(source, contact);
  }
}

