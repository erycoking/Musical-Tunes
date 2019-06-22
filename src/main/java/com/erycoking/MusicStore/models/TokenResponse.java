package com.erycoking.MusicStore.models;

import com.erycoking.MusicStore.models.Client.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private Client user;
}
