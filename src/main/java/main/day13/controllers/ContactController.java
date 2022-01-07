package main.day13.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String handleForm(@ModelAttribute ContactModel contactModel, Model model) {
        // no validation
        List<String> contactFields = Arrays.asList(
                contactModel.getName(),
                contactModel.getEmail(),
                contactModel.getPhoneNumber());

        contacts.createContactFile(contactFields);
        return "redirect:/";
    }
}