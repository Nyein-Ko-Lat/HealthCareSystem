package edu.gisma.gh1043541.healthcaresystem.security;

import edu.gisma.gh1043541.healthcaresystem.entity.Role;
import edu.gisma.gh1043541.healthcaresystem.entity.User;
import edu.gisma.gh1043541.healthcaresystem.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String fullName;
    private final String password;
    private final GrantedAuthority authority;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.fullName = user.getFirstName() + " " + user.getLastName();
        this.password = user.getPassword();
        this.authority = new SimpleGrantedAuthority(user.getRole().getName());
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getRoleName() { return authority.getAuthority(); }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(authority);   // convert to List
    }

    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public User getUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(fullName);
        Role role = new Role();
        role.setName(authority.getAuthority());
        user.setRole(role);
        return user;
    }
}
