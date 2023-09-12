package com.example.manageequipment.controller;

import com.example.manageequipment.auth.AuthenticationRequest;
import com.example.manageequipment.auth.AuthenticationResponse;
import com.example.manageequipment.auth.RequestRefreshToken;
import com.example.manageequipment.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping(value = "/admin/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest authenticationRequest) {

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(authenticationService.adminAuthenticate(authenticationRequest));
    }

    @PostMapping(value = "/user/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> userLogin(@Valid @RequestBody AuthenticationRequest authenticationRequest) {

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(authenticationService.userAuthenticate(authenticationRequest));
    }

    @PostMapping(value ="/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RequestRefreshToken requestRefreshToken) {
        return new ResponseEntity<>(authenticationService.refreshToken(requestRefreshToken), HttpStatus.OK);
    }
}
