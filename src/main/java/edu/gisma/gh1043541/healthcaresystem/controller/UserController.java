package edu.gisma.gh1043541.healthcaresystem.controller;

import edu.gisma.gh1043541.healthcaresystem.entity.User;
import edu.gisma.gh1043541.healthcaresystem.service.RoleServiceI;
import edu.gisma.gh1043541.healthcaresystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleServiceI roleService;

    public UserController(UserService userService, RoleServiceI roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/index";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "users/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute User user) {

        Long loggedInUserId = userService.getLoggedInUserId();

        if (user.getId() == null) {
            // New User
            user.setCreatedBy(loggedInUserId);
            user.setUpdatedBy(loggedInUserId);
            userService.save(user);
        } else {
            // Existing user
            user.setUpdatedBy(loggedInUserId);
            userService.update(user.getId(), user);
        }

        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.findAll());
        return "users/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/users";
    }
}

