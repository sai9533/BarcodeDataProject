package com.example.demo.config;

import com.example.demo.Entity.User;
import com.example.demo.Repo.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private HttpSession httpSession;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        User user=userRepository.findByEmail(username);
        if(user==null) {
            throw new UsernameNotFoundException("user not found");
        }else {
        	httpSession.setAttribute("authenticatedUser", user);
            return new CustomUser(user);
        }

    }

}