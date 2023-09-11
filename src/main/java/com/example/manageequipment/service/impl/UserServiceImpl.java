package com.example.manageequipment.service.impl;

import com.example.manageequipment.dto.UserDto;
import com.example.manageequipment.model.Equipment;
import com.example.manageequipment.model.User;
import com.example.manageequipment.repository.EquipmentRepository;
import com.example.manageequipment.repository.UserRepository;
import com.example.manageequipment.service.UserService;
import com.example.manageequipment.type.IntegerArrayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setAddress(user.getAddress());

        List<Long> equipmentIds = new ArrayList<>();
        user.getEquipments().forEach(e -> equipmentIds.add(e.getId()));

        List<Long> transferredEquipIds = new ArrayList<>();
        user.getTransferredEquipment().forEach(e -> transferredEquipIds.add(e.getId()));

        userDto.setEquipmentIds(equipmentIds);
        userDto.setTransferredEquipIds(transferredEquipIds);
        return userDto;
    }

    @Override
    public UserDto createUser(User user) {
        User createdUser = userRepository.save(user);
        System.out.println("created user: "+ createdUser);
        return mapToDto(createdUser);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();

        List<UserDto> listUser = new ArrayList<>();

        users.forEach(u -> {
            listUser.add(mapToDto((u)));
        });

        return listUser;
    }

    @Override
    public UserDto updateUser(Long userId, UserDto user) {
        User userData = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user id "+ userId));

        if (user.getFirstName() != null) {
            userData.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            userData.setLastName(user.getLastName());
        }
        if (user.getEmail() != null) {
            userData.setEmail(user.getEmail());
        }
        if (user.getAddress() != null) {
            userData.setAddress(user.getAddress());
        }

        User userUpdated = userRepository.save(userData);

        return mapToDto(userUpdated);
    }

    @Override
    public void deleteUser(List<Long> userIds) {
        userIds.stream().forEach(id -> {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user id "+ id));
            Set<Equipment> equipments = user.getEquipments();
            equipments.forEach(equip -> {
                equip.setOwner(null);
                equipmentRepository.save(equip);
            });

//            Set<Equipment> transferredEquip = user.getTransferredEquipment();
//            transferredEquip.forEach(equip -> {
//                Set<User> users = equip.getTransferredUser();
//                users.remove(user);
//                equip.setTransferredUser(users);
//                equipmentRepository.save(equip);
//            });

            userRepository.delete(user);
        });

    }
}
