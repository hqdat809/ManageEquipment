package com.example.manageequipment.service;

import com.example.manageequipment.model.Role;
import com.example.manageequipment.model.User;
import com.example.manageequipment.repository.RoleCustomRepo;
import com.example.manageequipment.repository.UserRepository;
import com.example.manageequipment.service.impl.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleCustomRepo roleCustomRepo;

    @Mock
    private JwtService jwtService;
    private User user;
    private Role role;
    private String token = "token";
    private String RefreshToken = "refreshToken";
    @BeforeEach
    public void init() {
        user = User.builder().firstName("ha").lastName("dat")
                .address("648 tay son").email("hqdat222@gmail.com").build();
        role = Role.builder().name("STUDENT").build();
    }


}
