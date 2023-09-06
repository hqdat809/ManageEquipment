package com.example.manageequipment.service;

import com.example.manageequipment.dto.EquipmentDto;
import com.example.manageequipment.dto.UserDto;
import com.example.manageequipment.model.Equipment;
import com.example.manageequipment.type.IntegerArrayRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EquipmentService {
    EquipmentDto createEquipment(Equipment equipment);

    List<EquipmentDto> getAllEquipment();

    EquipmentDto updateEquipment(Long equipmentId, EquipmentDto equipmentDto);

    void deleteEquipment(List<Long> ids);

    UserDto transferEquipment(List<Long> equipmentIds, Long userId);
}
