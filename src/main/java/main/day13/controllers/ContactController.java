package main.day13.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import main.day13.models.ContactModel;
import main.day13.service.Contacts;

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
        if (contacts.createContactFile(contactModel)) {
            // separation of the model
            ContactModel resultModel = new ContactModel(contactModel.getID(),
                    contactModel.getName(),
                    contactModel.getEmail(),
                    contactModel.getPhoneNumber());
            model.addAttribute("contact", resultModel);
            model.addAttribute("successMsg", "Contact saved!");
            return "result";
        } else {
            model.addAttribute("errorMessage", "Contact saved!");
            return "error";
        }
    }
}