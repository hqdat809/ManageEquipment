package com.example.manageequipment.service;

import com.example.manageequipment.dto.EquipmentDto;
import com.example.manageequipment.dto.UserDto;
import com.example.manageequipment.model.Equipment;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface EquipmentService {
    EquipmentDto createEquipment(Equipment equipment, MultipartFile file, HttpServletRequest request) throws IOException;

    List<EquipmentDto> getAllEquipment();

    EquipmentDto updateEquipment(Long equipmentId, EquipmentDto equipmentDto);

    void deleteEquipment(List<Long> ids);

    UserDto transferEquipment(List<Long> equipmentIds, Long userId);
}
