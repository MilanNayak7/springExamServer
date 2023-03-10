package com.exam.controllers;

import com.exam.config.JwtUtils;
import com.exam.models.JwtRequest;
import com.exam.models.JwtResponse;
import com.exam.models.User;
import com.exam.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtUtils jwtUtil;


    @RequestMapping(value = "/generate-token",method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {


        try {
            System.out.println("User name is "+jwtRequest.getUsername()+"  "+"User password is :"+jwtRequest.getPassword());
             authenticate(jwtRequest.getUsername(),jwtRequest.getPassword());

        }catch (UsernameNotFoundException e){
            e.printStackTrace();
            throw new Exception("User not found");
        }

        UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(jwtRequest.getUsername());
        String token=this.jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token)) ;
    }


    private void authenticate(String username,String password) throws Exception {

        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try{
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (DisabledException e){
            throw new Exception("USER DISABLED "+e.getMessage());
        }catch (BadCredentialsException e){
            throw new Exception("Invalid Credentials " +e.getMessage());
        }
    }


    // Return currently sign in user detail
    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal){
        return ((User) this.userDetailsServiceImpl.loadUserByUsername(principal.getName()));
    }

}
