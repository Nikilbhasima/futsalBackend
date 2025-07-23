package com.futsalBooking.advanceJavaProject.custom;

import com.futsalBooking.advanceJavaProject.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {
    private Users users;
    public MyUserDetails(Users users) {
        this.users = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = users.getRoles()
                .stream()
                .map(role->new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getPhoneNumber();
    }
}
