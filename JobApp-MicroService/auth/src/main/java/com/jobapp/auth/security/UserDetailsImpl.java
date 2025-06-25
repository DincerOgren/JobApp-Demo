package com.jobapp.auth.security;

import com.jobapp.auth.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public static UserDetailsImpl build(User user) {
        // (authorities are derived in getAuthorities(), so just new up the object)
        return new UserDetailsImpl(user);
    }


    @Override
    public String getUsername() {
        return user.getEmail(); // <-- use email as "username"
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public String getName(){
        return user.getName();
    }

    // Implement other methods (authorities, isAccountNonExpired, etc.)
    // For a basic app, just return true for those.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toSet());
    }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public Long getId() { return user.getId(); } // expose id for claims if you want

    public User getUser() { return user; }
}