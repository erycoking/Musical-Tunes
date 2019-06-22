package com.erycoking.MusicStore.exception;

import com.erycoking.MusicStore.models.ErrorDetails;
import com.erycoking.MusicStore.security.jwt.InvalidJwtAuthenticationException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {InvalidJwtAuthenticationException.class})
    public ResponseEntity invalidJwtAuthentication(InvalidJwtAuthenticationException ex, WebRequest request) {
        log.debug("handling InvalidJwtAuthenticationException...");
        return status(UNAUTHORIZED).build();
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity handleBaseException(DataIntegrityViolationException e){
        log.debug("handling DataIntegrityViolationException...");
        return ResponseEntity.badRequest().body(e);
    }

    @ExceptionHandler(value = FileStorageException.class)
    public ResponseEntity handleFileStorageException(FileStorageException e){
        log.debug("handling FileStorageException...");
        return ResponseEntity.badRequest().body(e);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException(UserNotFoundException e){
        log.debug("handling UserNotFoundException...");
        return ResponseEntity.badRequest().body(e);
    }

    @ExceptionHandler(value = JwtException.class)
    public ResponseEntity handleJwtException(JwtException e){
        log.debug("handling JwtException...");
        return ResponseEntity.badRequest().body(e);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity handleBadRequestException(BadRequestException e){
        log.debug("handling BadRequestException...");
        return ResponseEntity.badRequest().body(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
