package com.example.ecommerce.controller;

import com.example.ecommerce.model.ContactMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public String viewContact(Model model) {
        model.addAttribute("contactMessage", new ContactMessage());
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContact(@ModelAttribute("contactMessage") ContactMessage message,
                                RedirectAttributes redirectAttributes) {
        // For simulation purposes, we set a flash message indicating success
        redirectAttributes.addFlashAttribute("successMessage", "Thank you, " + message.getName() + "! Your message has been received. We will get back to you shortly.");
        return "redirect:/contact";
    }
}
