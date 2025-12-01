package edu.gisma.gh1043541.healthcaresystem.service;


import edu.gisma.gh1043541.healthcaresystem.entity.User;
import edu.gisma.gh1043541.healthcaresystem.repository.IRoleRepository;
import edu.gisma.gh1043541.healthcaresystem.repository.IUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IBaseService<User, Long> {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository,
                       IRoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ------------------------------------------------------
    // GET LOGGED-IN USER ID
    // ------------------------------------------------------
    public Long getLoggedInUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User loggedUser = userRepository.findByUsername(username).orElse(null);
        return (loggedUser != null) ? loggedUser.getId() : 1L; // fallback to admin
    }

    // ------------------------------------------------------
    // CRUD override Functions
    // ------------------------------------------------------

    @Override
    public void delete(Long id, Long UpdatedBy) {
        userRepository.deleteById(id);
    }

    @Override
    public User save(User user) {
        // Set default password (encoded)
        if (user.getPassword().isEmpty() || user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode("123456"));
        }else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Default role
        if (user.getRole() == null) {
            user.setRole(roleRepository.findByName("USER"));
        }
        return userRepository.save(user);
    }

    public User update(Long Id, User user) {
        User oldUser = userRepository.findById(Id).orElseThrow();

        // Keep old password always
        user.setPassword(oldUser.getPassword());

        // Keep createdBy from old record
        user.setCreatedBy(oldUser.getCreatedBy());

        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}

