package com.project.LimeRMS.Config.security;

import com.project.LimeRMS.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.ArrayList;

public class UserDetail implements UserDetails {
    private final User user;
    private Collection<GrantedAuthority> authorities;

    public UserDetail(User user) {
        this.user = user;
    }

    public final User getUser(){
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = user.getAuthentication().getAuthNm();
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> {
            System.out.print("Role: " + role);
            return role;
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserEmail();
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
