package main.day13.models;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class ContactModel implements Serializable {
    private String name;
    private String email;
    private String phoneNumber;

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
