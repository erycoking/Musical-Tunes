package com.erycoking.MusicStore.controllers;

import com.erycoking.MusicStore.exception.UserNotFoundException;
import com.erycoking.MusicStore.models.AuthRequest;
import com.erycoking.MusicStore.security.jwt.JwtTokenProvider;
import com.erycoking.MusicStore.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ClientService clientService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(value = "/signin")
    public ResponseEntity<?> login(@RequestBody AuthRequest data){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword()));
            String token = jwtTokenProvider.createToken(clientService.getByUserName(data.getUsername()).orElseThrow(() -> new UserNotFoundException("Username " + data.getUsername() + "not found")));

            Map<Object, Object> model = new HashMap<>();
            model.put("username", data.getUsername());
            model.put("token", token);

            return ResponseEntity.ok(model);

        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}
