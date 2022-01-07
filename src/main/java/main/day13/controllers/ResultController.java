package main.day13.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import main.day13.exceptions.ContactNotFoundException;
import main.day13.helpers.Contacts;
import main.day13.models.ContactModel;

/**
 * ResultController
 */
@Controller
public class ResultController {
    @Autowired
    private Contacts contacts;

    @GetMapping("contact/{id}")
    public String retrieveContact(@PathVariable(required = true) String id, Model model) {
        ContactModel contactModel = contacts.getContactFile(id);
        try {
            if (contactModel != null) {
                model.addAttribute("name", contactModel.getName());
                model.addAttribute("email", contactModel.getEmail());
                model.addAttribute("phoneNumber", contactModel.getPhoneNumber());
                return "result";
            } else {
                throw new ContactNotFoundException("Contact not found");
            }
        } catch (ContactNotFoundException e) {
            model.addAttribute("userid", id);
            return "error";
        }
    }
}