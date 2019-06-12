package com.erycoking.MusicStore.controllers;

import com.erycoking.MusicStore.exception.UserNotFoundException;
import com.erycoking.MusicStore.models.Client.AuthRequest;
import com.erycoking.MusicStore.models.Client.Client;
import com.erycoking.MusicStore.security.jwt.JwtTokenProvider;
import com.erycoking.MusicStore.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/signin")
    public ResponseEntity<?> login(@RequestBody AuthRequest data){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword()));
            String token = jwtTokenProvider.createToken(clientService.getByUserName(data.getUsername()).orElseThrow(() -> new UserNotFoundException("Username " + data.getUsername() + "not found")));


            Map<Object, Object> model = new HashMap<>();
            model.put("username", data.getUsername());
            model.put("token", token);
            Optional<Client> client =  clientService.getByUserName(data.getUsername());
            if (client.isPresent())
                model.put("displayName", client.get().getName());

            return ResponseEntity.ok(model);

        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody Client client){
        try {

            if (clientService.exist(client.getName()))
                return ResponseEntity.badRequest().body("user already exists");

            client.setRole("ROLE_USER");
            clientService.save(client);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(client.getEmail(), client.getPassword()));
            String token = jwtTokenProvider.createToken(clientService.getByUserName(client.getEmail()).orElseThrow(() -> new UserNotFoundException("Username " + client.getEmail() + "not found")));

            Map<Object, Object> model = new HashMap<>();
            model.put("username", client.getEmail());
            model.put("token", token);
            model.put("displayName",client.getName());

            return ResponseEntity.ok(model);

        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}
