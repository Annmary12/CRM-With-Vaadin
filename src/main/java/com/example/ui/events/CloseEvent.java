package com.example.ui.events;

import com.example.ui.ContactForm;

public class CloseEvent extends ContactFormEvent {

  public CloseEvent(ContactForm source) {
    super(source, null);
  }
}
