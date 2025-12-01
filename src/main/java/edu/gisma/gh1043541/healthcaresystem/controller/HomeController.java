package edu.gisma.gh1043541.healthcaresystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to My Healthcare System Project!");
        return "index"; // Looks for index.html in src/main/resources/templates
    }
}
