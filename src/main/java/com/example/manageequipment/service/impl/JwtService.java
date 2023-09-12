package com.example.manageequipment.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.manageequipment.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String secret_key = "123";
//  Expiration of token
    private static final Integer expiration = 10 * 60 * 1000;
    public String generateToken(User user, Collection<SimpleGrantedAuthority> authorities) {
        Algorithm algorithm = Algorithm.HMAC256(secret_key.getBytes());

        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withClaim("id", user.getId())
                .sign(algorithm);
    }

    public String generateRefreshToken(User user, Collection<SimpleGrantedAuthority> authorities) {
        Algorithm algorithm = Algorithm.HMAC256(secret_key.getBytes());

        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }
}
