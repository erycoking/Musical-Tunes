package com.erycoking.MusicStore.controllers;

import com.erycoking.MusicStore.exception.UserNotFoundException;
import com.erycoking.MusicStore.models.Client.AuthRequest;
import com.erycoking.MusicStore.models.Client.Client;
import com.erycoking.MusicStore.models.TokenResponse;
import com.erycoking.MusicStore.security.jwt.JwtTokenProvider;
import com.erycoking.MusicStore.services.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(description = "Set of Endpoints for authentication")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ClientService clientService;

    @PostMapping(value = "/signin")
    @ApiOperation("Returns a token with user information")
    public ResponseEntity<TokenResponse> login(
            @ApiParam(value = "object containing username and password of the user", required = true)
            @RequestBody AuthRequest data){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword()));
            String token = jwtTokenProvider.createToken(clientService.getByUserName(data.getUsername()).orElseThrow(() -> new UserNotFoundException("Username " + data.getUsername() + "not found")));

            Optional<Client> client =  clientService.getByUserName(data.getUsername());
            if (client.isPresent())
                return ResponseEntity.ok(new TokenResponse(token, client.get()));
            else
                throw new BadCredentialsException("An error occurred");

        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/signup")
    @ApiOperation("Adds a user and return a token")
    public ResponseEntity<TokenResponse> register(
            @ApiParam(value = "object of the user to be added", required = true)
            @Valid @RequestBody Client client){
        try {

            if (clientService.exist(client.getName()))
                throw new BadCredentialsException("An error occurred");

            client.setRole("ROLE_USER");
            Client newClient =  clientService.save(client);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(client.getEmail(), client.getPassword()));
            String token = jwtTokenProvider.createToken(clientService.getByUserName(client.getEmail()).orElseThrow(() -> new UserNotFoundException("Username " + client.getEmail() + "not found")));

            return ResponseEntity.ok(new TokenResponse(token, newClient));

        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}
