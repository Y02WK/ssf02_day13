package main.day13.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import main.day13.exceptions.ContactNotFoundException;
import main.day13.models.ContactModel;
import main.day13.utils.ContactsRedis;

/**
 * ResultController
 */
@Controller
public class ResultController {
    @Autowired
    private ContactsRedis contactsRedis;

    @GetMapping("contact/{id}")
    public String retrieveContact(
            @PathVariable(required = true) String id,
            Model model,
            HttpServletResponse response) {

        try {
            ContactModel contactModel = contactsRedis.getFromRedis(id);
            model.addAttribute("contact", contactModel);
            return "result";
        } catch (ContactNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            model.addAttribute("userid", id);
            return "userNotFound";
        }
    }
}