package com.erycoking.MusicStore.services;

import com.erycoking.MusicStore.models.Client;
import com.erycoking.MusicStore.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements UserDetailsService {

    private ClientRepository clientRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ClientRepository clientRepository, BCryptPasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Client> getClient(int id){
        return clientRepository.findById(id);
    }

    public Optional<Client> getByUserName(String email){
        return clientRepository.findByEmail(email);
    }

    public Client save(Client client){
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client);
    }

    public List<Client> saveAll(List<Client> client){
        client.forEach(e -> e.setPassword(passwordEncoder.encode(e.getPassword())));
        return clientRepository.saveAll(client);
    }

    public void delete(int id){
        clientRepository.deleteById(id);
    }

    public void delete(Client client){
        clientRepository.delete(client);
    }

    public void deleteAll(){
        clientRepository.deleteAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(username).
                orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Arrays.asList(new SimpleGrantedAuthority(client.getRole()));
            }

            @Override
            public String getPassword() {
                return getPassword();
            }

            @Override
            public String getUsername() {
                return client.getEmail();
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
        };
    }
}
