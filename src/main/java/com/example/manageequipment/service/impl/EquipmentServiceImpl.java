package com.example.manageequipment.service.impl;

import com.example.manageequipment.dto.EquipmentDto;
import com.example.manageequipment.dto.UserDto;
import com.example.manageequipment.model.Equipment;
import com.example.manageequipment.model.User;
import com.example.manageequipment.repository.EquipmentRepository;
import com.example.manageequipment.repository.UserRepository;
import com.example.manageequipment.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    public EquipmentDto mapToDto(Equipment equipment) {
        EquipmentDto equipmentDto = new EquipmentDto();
        equipmentDto.setId(equipment.getId());
        equipmentDto.setName(equipment.getName());
        if (equipment.getOwner() != null) {
            equipmentDto.setOwnerId(equipment.getOwner().getId());
        } else {
            equipmentDto.setOwnerId(null);
        }

        return equipmentDto;
    }

    @Override
    public EquipmentDto createEquipment(Equipment equipment) {
        Equipment equipmentCreated = equipmentRepository.save(equipment);

        return mapToDto(equipmentCreated);
    }

    @Override
    public List<EquipmentDto> getAllEquipment() {
        List<Equipment> equipmentList = equipmentRepository.findAll();
        List<EquipmentDto> equipmentDtos = new ArrayList<>();
        equipmentList.forEach(e -> equipmentDtos.add(mapToDto(e)));

        return equipmentDtos;
    }

    @Override
    public EquipmentDto updateEquipment(Long equipmentId, EquipmentDto equipmentDto) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid equipment id" + equipmentId));

        if (equipmentDto.getName() != null) {
            equipment.setName(equipmentDto.getName());
        }

        Equipment equipmentUpdated = equipmentRepository.save(equipment);

        return mapToDto(equipmentUpdated);
    }

    @Override
    public void deleteEquipment(List<Long> ids) {
        ids.forEach(id -> {
            Equipment equipment = equipmentRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid equipment id " + id));

            equipmentRepository.delete(equipment);
        });
    }

    @Override
    public UserDto transferEquipment(List<Long> equipmentIds, Long userId) {
        User userData = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user id!!" + userId));

        equipmentIds.forEach(id -> {
            Equipment equipment = equipmentRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user"));

            equipment.setOwner(userData);
            equipmentRepository.save(equipment);
        });
        return userService.mapToDto(userData);
    }
}
