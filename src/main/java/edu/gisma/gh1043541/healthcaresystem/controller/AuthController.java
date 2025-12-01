package edu.gisma.gh1043541.healthcaresystem.controller;

import edu.gisma.gh1043541.healthcaresystem.entity.User;
import edu.gisma.gh1043541.healthcaresystem.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "Auth/login";
    }

    // POST /auth/register
    @PostMapping("/register")
    public String Register(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        user.setCreatedBy(1L);  //Default admin user
        user.setUpdatedBy(1L);  //Default admin user

        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "Registration successful! Please login.");
        return "redirect:/auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/auth/login?logout"; // redirect to login page after logout
    }
}
