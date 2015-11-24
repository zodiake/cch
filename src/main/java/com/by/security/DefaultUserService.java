package com.by.security;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.by.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.by.model.User;
import com.by.service.UserService;

@Service
public class DefaultUserService implements UserDetailsService {
    @Autowired
    private UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = service.findByName(username);
        if (user == null)
            throw new UsernameNotFoundException("user not exist");
        return new DefaultUserDetails(user);
    }

    private class DefaultUserDetails extends User implements UserDetails {

        public DefaultUserDetails(User user) {
            setId(user.getId());
            setName(user.getName());
            setUserAuthorities(user.getUserAuthorities());
            setPassword(user.getPassword());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return getUserAuthorities().stream().flatMap(i -> AuthorityUtil.createAuthority(i.getName()).stream())
                    .collect(Collectors.toList());
        }

        @Override
        public String getUsername() {
            return getName();
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

}
