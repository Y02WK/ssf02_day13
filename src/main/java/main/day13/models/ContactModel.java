package main.day13.models;

import static main.day13.utils.IDGenerator.generateID;

import org.springframework.stereotype.Component;

@Component
public class ContactModel {
    private final String id;
    private String name;
    private String email;
    private String phoneNumber;

    public ContactModel() {
        this.id = generateID(8);
    }

    public ContactModel(String id, String name, String email, String phoneNumber) {
        this.id = id;
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
