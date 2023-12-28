package com.SpringSecurity.SpringSecurityApplication1.config;

import com.SpringSecurity.SpringSecurityApplication1.model.OurUsers;
import com.SpringSecurity.SpringSecurityApplication1.repository.OurUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class OurUsersInfoDetailService implements UserDetailsService {
    @Autowired
    private OurUserRepo ourUserRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<OurUsers> user = ourUserRepo.findByEmail(username);
        return user.map(OurUsersInfoDetail :: new).orElseThrow(()->new UsernameNotFoundException("Email doesn't exist"));
    }
}
