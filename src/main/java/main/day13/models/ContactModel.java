package main.day13.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.day13.helpers.Contacts;

@Component
public class ContactModel {
    @Autowired
    private Contacts contacts;
    private final String id = contacts.generateID(8);
    private String name;
    private String email;
    private String phoneNumber;

    public ContactModel() {
    }

    public ContactModel(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
