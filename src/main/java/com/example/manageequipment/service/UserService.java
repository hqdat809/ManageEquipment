package com.example.manageequipment.service;

import com.example.manageequipment.dto.UserDto;
import com.example.manageequipment.model.Role;
import com.example.manageequipment.model.User;
import com.example.manageequipment.type.IntegerArrayRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto createUser(User user);

    List<UserDto> getUsers();

    UserDto updateUser(Long userId, UserDto user);

    void deleteUser(List<Long> userId);

     Role saveRole(Role role);

     User saveUser(User user);

    void addRoleToStudent(String userEmail, String roleName);
}
