package com.example.manageequipment.service;


import com.example.manageequipment.auth.AuthenticationRequest;
import com.example.manageequipment.auth.AuthenticationResponse;
import com.example.manageequipment.auth.RequestRefreshToken;

public interface AuthenticationService {

    AuthenticationResponse adminAuthenticate(AuthenticationRequest authenticationRequest);

    AuthenticationResponse refreshToken(RequestRefreshToken refreshToken);

    AuthenticationResponse userAuthenticate(AuthenticationRequest authenticationRequest);
}
