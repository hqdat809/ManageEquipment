package com.example.manageequipment.auth;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestRefreshToken {
    private String refreshToken;
}
