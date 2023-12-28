package com.SpringSecurity.SpringSecurityApplication1.controller;

import com.SpringSecurity.SpringSecurityApplication1.model.OurUsers;
import com.SpringSecurity.SpringSecurityApplication1.model.Product;
import com.SpringSecurity.SpringSecurityApplication1.repository.OurUserRepo;
import com.SpringSecurity.SpringSecurityApplication1.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class Controller {

    @Autowired
    private OurUserRepo ourUserRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String goHome(){
        return "This is publically accessible api";
    }

    @PostMapping("/user/save")
    public ResponseEntity<Object> saveUser(@RequestBody OurUsers ourusers){
        ourusers.setPassword(passwordEncoder.encode(ourusers.getPassword()));
        OurUsers result = ourUserRepo.save(ourusers);
        if(result.getId() > 0){
            return ResponseEntity.ok("User saved successfully");
        }
        return  ResponseEntity.status(404).body("Error,User not saved");
    }

    @GetMapping("/product/all")
    public ResponseEntity<Object>  getAllProducts(){
        return ResponseEntity.ok(productRepo.findAll());
    }
    @GetMapping("/user/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object>  getAllUsers(){
        return ResponseEntity.ok(ourUserRepo.findAll());
    }

    @GetMapping("/user/single")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Object> getDetails(){
        return ResponseEntity.ok(ourUserRepo.findByEmail(getLoggedInUserDetails().getUsername()));
    }

    public UserDetails getLoggedInUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.getPrincipal() instanceof UserDetails){
            return (UserDetails)authentication.getPrincipal();
        }
        return null;
    }

}
