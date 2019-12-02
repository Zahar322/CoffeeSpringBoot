package com.controller.service.serviceImpl;

import com.controller.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user=userService.findByUsername(username);
        if(user!=null){
            return user;
        }
        throw new UsernameNotFoundException("User "+username+" not found");
    }
}
