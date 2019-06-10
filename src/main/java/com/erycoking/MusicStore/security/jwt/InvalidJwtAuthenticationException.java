package com.erycoking.MusicStore.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {
    public InvalidJwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public InvalidJwtAuthenticationException(String msg) {
        super(msg);
    }
}
