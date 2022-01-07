package main.day13.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import main.day13.models.ContactModel;

@Controller
@RequestMapping("/")
public class FormResource {
    @Autowired
    private ContactModel contact;

    @GetMapping()
    public String displayForm(Model model) {
        model.addAttribute("contact", contact);
        return "form";
    }
}
