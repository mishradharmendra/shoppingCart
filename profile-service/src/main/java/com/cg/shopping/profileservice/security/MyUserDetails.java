package com.cg.shopping.profileservice.security;

import com.cg.shopping.profileservice.entity.UserProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class MyUserDetails implements UserDetails {

    private UserProfile user;

    public MyUserDetails(UserProfile user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.user.getRole().equals("ROLE_ADMIN")) {
            return List.of(new SimpleGrantedAuthority("ADMIN"));
        } else {
            return List.of(new SimpleGrantedAuthority("CUSTOMER"));
        }
    }

    public  String getName(){
        return user.getFullName();
    }

    public String getId(){
        return user.get_id();
    }

    public String getRole() {
        return user.getRole();
    }

    public Boolean getIsAdmin(){
        boolean roles_admin = getAuthorities()
                .stream()
                .filter(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"))
                .findAny()
                .isPresent();
        return roles_admin;
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
