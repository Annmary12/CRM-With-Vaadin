package com.example.ui.events;

import com.example.ui.view.list.ContactForm;

public class CloseEvent extends ContactFormEvent {

  public CloseEvent(ContactForm source) {
    super(source, null);
  }
}
