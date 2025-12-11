package edu.gisma.gh1043541.healthcaresystem.controller;

import edu.gisma.gh1043541.healthcaresystem.entity.Role;
import edu.gisma.gh1043541.healthcaresystem.service.RoleServiceI;
import edu.gisma.gh1043541.healthcaresystem.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleServiceI roleService;
    private final UserService userService;

    public RoleController(RoleServiceI roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping
    public String listRoles(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "roles/index";
    }

    @GetMapping("/new")
    public String createRoleForm(Model model) {
        model.addAttribute("role", new Role());
        return "roles/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute  Role role) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        Long userId = userService.findByUsername(username).getId();
        role.setUpdatedBy(userId);
        role.setCreatedBy(userId);

        roleService.save(role);
        return "redirect:/roles";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("role", roleService.findById(id));
        return "roles/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        roleService.delete(id);
        return "redirect:/roles";
    }
}

