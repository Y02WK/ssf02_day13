package main.day13.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import main.day13.helpers.Contacts;
import main.day13.models.ContactModel;

/**
 * ContactController
 */
@Controller
public class ContactController {
    @Autowired
    private Contacts contacts;

    @PostMapping("/contact")
    @ResponseStatus(code = HttpStatus.CREATED)
    public String handleForm(@ModelAttribute ContactModel contactModel, Model model) {
        contacts.createContactFile(contactModel);
        // separation of the model
        // ContactModel resultModel = new ContactModel(contactModel.getName(),
        // contactModel.getEmail(),
        // contactModel.getPhoneNumber());
        model.addAttribute("contact", contactModel);
        model.addAttribute("successMsg", "Contact saved!");
        return "result";
    }
}