package com.erycoking.MusicStore.models.Client;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Client{

    public Client(@NotNull(message = "Name is required") @Pattern(regexp = "[a-zA-Z]+([.']+\\s+[a-zA-Z]+)*", message = "Only letters, apostrophe and period are allowed") String name,
                  @Email @NotNull(message = "Email is required") String email,
                  @NotNull(message = "Password is required") @Size(min = 8, message = "password should be equal or longer than 8 characters") String password1,
                  @NotNull(message = "role is required") String role) {
        this.name = name;
        this.email = email;
        this.password = password1;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty
    private int clientId;

    @NotNull(message = "Name is required")
    @Pattern(regexp = "[a-zA-Z]+([.']+\\s+[a-zA-Z]+)*", message = "Only letters, apostrophe and period are allowed")
    @Column(name = "name", nullable = false)
    @ApiModelProperty(example ="eryco king", required = true, position = 0)
    private String name;

    @Email
    @NotNull(message = "Email is required")
    @Column(name = "email", nullable = false, unique = true)
    @ApiModelProperty(example = "erycoking360@gmail.com", required = true, position = 2)
    private String email;

    @NotNull(message = "Password is required")
    @Column(name = "password", nullable = false)
    @Size(min = 8, message = "password should be equal or longer than 8 characters")
    @ApiModelProperty(example = "password", required = true, position = 2)
    private String password;

    @Column(name = "roles", nullable = false)
    @NotNull(message = "role is required")
    @ApiModelProperty
    private String role;

}
