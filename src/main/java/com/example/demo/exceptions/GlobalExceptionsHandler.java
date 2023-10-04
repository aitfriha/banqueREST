package com.example.demo.exceptions;

import static com.example.demo.exceptions.EntityType.Customer;
import static com.example.demo.exceptions.ExceptionType.FORBIDDEN;
import static com.example.demo.exceptions.ExceptionType.WRONG_CREDENTIALS;
import static com.example.demo.exceptions.MainException.getMessageTemplate;

import javax.security.auth.login.CredentialException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.response.Response;

@ControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler(value = { AccessDeniedException.class })
    public ResponseEntity<Response> handleAnyException(AccessDeniedException ex, WebRequest request) {
       // return new ResponseEntity<>("My custom error message", new HttpHeaders(), HttpStatus.FORBIDDEN);
    	return new ResponseEntity<Response>(Response.accessForbidden().setPayload(getMessageTemplate(Customer, FORBIDDEN)),HttpStatus.FORBIDDEN);
    }
    
    @ExceptionHandler(value = { BadCredentialsException.class })
    public ResponseEntity<Response> handleCredentialException(BadCredentialsException ex, WebRequest request) {
       // return new ResponseEntity<>("My custom error message", new HttpHeaders(), HttpStatus.FORBIDDEN);
    	return new ResponseEntity<Response>(Response.wrongCredentials().setPayload(getMessageTemplate(Customer, WRONG_CREDENTIALS)),HttpStatus.UNAUTHORIZED);
    }
}