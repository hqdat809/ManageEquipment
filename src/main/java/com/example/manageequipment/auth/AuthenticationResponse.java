package com.example.manageequipment.auth;

import com.example.manageequipment.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationResponse {
    private String token;
    private String refreshToken;
    private User userData;
}
