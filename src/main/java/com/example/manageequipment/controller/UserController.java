package com.example.manageequipment.controller;

import com.example.manageequipment.dto.UserDto;
import com.example.manageequipment.model.User;
import com.example.manageequipment.service.UserService;
import com.example.manageequipment.type.IntegerArrayRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user) , HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDto user) {
        return new ResponseEntity<>(userService.updateUser(userId, user), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody IntegerArrayRequest userIds) {
        userService.deleteUser(userIds.getIds());
        return new ResponseEntity<>("Delete user success!!", HttpStatus.OK);
    }

}
