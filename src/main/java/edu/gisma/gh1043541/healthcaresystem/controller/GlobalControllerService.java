package edu.gisma.gh1043541.healthcaresystem.controller;

import edu.gisma.gh1043541.healthcaresystem.entity.User;
import edu.gisma.gh1043541.healthcaresystem.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerService {

    @ModelAttribute("loggedInUser")
    public User loggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            return userDetails.getUser();
        }
        return null;
    }
}