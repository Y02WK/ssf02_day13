package main.day13.service;

import main.day13.exceptions.ContactNotFoundException;
import main.day13.models.ContactModel;

/**
 * ContactsRepo
 */
public interface ContactsRepo {
    public void saveContact(ContactModel contactModel);

    public ContactModel getContact(String id) throws ContactNotFoundException;
}