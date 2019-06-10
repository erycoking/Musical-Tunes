package com.erycoking.MusicStore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Entity
public class Client{
    public Client(@NotNull(message = "Name is required")
                  @Pattern(regexp = "[a-zA-Z]+([.']+\\s+[a-zA-Z]+)*", message = "Only letters, apostrophe and period are allowed") String name,
                  @Email @NotNull(message = "Email is required") String email,
                  @NotNull(message = "Password is required")
                  @Size(min = 8, message = "password should be equal or longer than 8 characters") String password1,
                  @NotNull(message = "role is required")
                  @Size(min = 8, message = "role should not be less than 3 characters")
                  String role) {
        this.name = name;
        this.email = email;
        this.password = password1;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int clientId;

    @NotNull(message = "Name is required")
    @Pattern(regexp = "[a-zA-Z]+([.']+\\s+[a-zA-Z]+)*", message = "Only letters, apostrophe and period are allowed")
    @Column(name = "name", nullable = false)
    private String name;

    @Email
    @NotNull(message = "Email is required")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull(message = "Password is required")
    @Column(name = "password", nullable = false)
    @Size(min = 8, message = "password should be equal or longer than 8 characters")
    private String password;

    @JsonIgnore
    @NotNull(message = "role is required")
    @Size(min = 8, message = "role should not be less than 3 characters")
    @Column(name = "roles", nullable = false)
    private String role;

}
