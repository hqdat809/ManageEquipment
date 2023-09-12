package com.example.manageequipment.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.manageequipment.auth.AuthenticationRequest;
import com.example.manageequipment.auth.AuthenticationResponse;
import com.example.manageequipment.auth.RequestRefreshToken;
import com.example.manageequipment.model.Role;
import com.example.manageequipment.model.User;
import com.example.manageequipment.repository.RoleCustomRepo;
import com.example.manageequipment.repository.UserRepository;
import com.example.manageequipment.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final RoleCustomRepo roleCustomRepo;

    public final static String secret_key = "123";
    boolean isAdmin;


    @Override
    public AuthenticationResponse adminAuthenticate(AuthenticationRequest authenticationRequest) {
        System.out.println("authen: " + authenticationRequest.getEmail());
        System.out.println("authen: " + authenticationRequest.getPassword());
        isAdmin = false;
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword())
        );
        System.out.println("authen 1");

        User user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email is incorrect!!"));
        List<Role> role = null;
        System.out.println("authen 2");
        if(user != null) {
            role = roleCustomRepo.findRoleByEmail(user.getEmail()).stream().collect(Collectors.toList());
        }

        System.out.println("authen 3");
        assert role != null;
        role.forEach(r -> {
            System.out.println("role: " + r.getName());
            if (r.getName().equals("ADMIN")) {
                isAdmin = true;
            }
        });

        if(isAdmin) {
            System.out.println("authen: " + authenticationRequest.getEmail());
            System.out.println("authen: " + authenticationRequest.getEmail());


            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Set<Role> set = new HashSet<>();

            role.stream().forEach(r -> set.add(Role.builder().name(r.getName()).build()));

//        user.setRoles(set);

            set.stream().forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getName())));

            var jwtToken = jwtService.generateToken(user, authorities);
            var jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);

            return new AuthenticationResponse(jwtToken, jwtRefreshToken, user);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not an ADMIN!!");
    }

    @Override
    public AuthenticationResponse userAuthenticate(AuthenticationRequest authenticationRequest) {
        isAdmin = false;
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword())
        );
        User user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email is incorrect!!"));
        List<Role> role = null;
        if(user != null) {
            role = roleCustomRepo.findRoleByEmail(user.getEmail()).stream().collect(Collectors.toList());
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Set<Role> set = new HashSet<>();

        role.stream().forEach(r -> set.add(Role.builder().name(r.getName()).build()));

//        user.setRoles(set);

        set.stream().forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getName())));

        var jwtToken = jwtService.generateToken(user, authorities);
        var jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);

        return new AuthenticationResponse(jwtToken, jwtRefreshToken, user);
    }

    @Override
    public AuthenticationResponse refreshToken(RequestRefreshToken requestRefreshToken) {

        DecodedJWT decodedJWT = JWT.decode(requestRefreshToken.getRefreshToken());
        System.out.println("subject: "+ decodedJWT.getSubject());
        String email = decodedJWT.getSubject();

        User user = userRepository.findByEmail(email).get();

        List<Role> role = null;

        if(user != null) {
            role = roleCustomRepo.findRoleByEmail(user.getEmail()).stream().collect(Collectors.toList());
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Set<Role> set = new HashSet<>();

        role.stream().forEach(r -> set.add(Role.builder().name(r.getName()).build()));

        user.setRoles(set);

        set.stream().forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getName())));
        var newJwtToken = jwtService.generateToken(user, authorities);

        return new AuthenticationResponse(newJwtToken, requestRefreshToken.getRefreshToken(), user);
    }
}
