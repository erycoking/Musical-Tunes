package com.erycoking.MusicStore.repositories;

import com.erycoking.MusicStore.models.Client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository  extends JpaRepository<Client, Integer> {

    Optional<Client> findByEmail(String email);
    boolean existsByName(String name);
}
