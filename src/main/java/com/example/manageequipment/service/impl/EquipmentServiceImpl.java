package com.example.manageequipment.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;


    public EquipmentDto mapToDto(Equipment equipment) {
        EquipmentDto equipmentDto = new EquipmentDto();
        equipmentDto.setId(equipment.getId());
        equipmentDto.setName(equipment.getName());
        equipmentDto.setImageUrl(equipment.getImageUrl());

        if (equipment.getOwner() != null) {
            equipmentDto.setOwnerId(equipment.getOwner().getId());
        } else {
            equipmentDto.setOwnerId(null);
        }

        if (!equipment.getTransferredUser().isEmpty()) {
            List<Long> transferredUserIds = new ArrayList<>();
            equipment.getTransferredUser().forEach(u -> {
                transferredUserIds.add(u.getId());
            });

            equipmentDto.setTransferredUserIds(transferredUserIds);
        }

        return equipmentDto;
    }

    @Override
    public EquipmentDto createEquipment(Equipment equipment, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {

            Map r = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

            String imgUrl = (String) r.get("secure_url");

            System.out.println("====================================================");
            System.out.println("image url: "+ imgUrl);
            System.out.println("====================================================");
            equipment.setImageUrl(imgUrl);

            Equipment equipmentCreated = equipmentRepository.save(equipment);

            return mapToDto(equipmentCreated);
        }
        else {
            Equipment equipmentCreated = equipmentRepository.save(equipment);

            EquipmentDto equipmentDto = mapToDto(equipmentCreated);
            return equipmentDto;
        }

    }

    @Override
    public List<EquipmentDto> getAllEquipment() {
        List<Equipment> equipmentList = equipmentRepository.findAll();
        List<EquipmentDto> equipmentDtos = new ArrayList<>();
        equipmentList.forEach(e -> equipmentDtos.add(mapToDto(e)));

        return equipmentDtos;
    }

    @Override
    public EquipmentDto updateEquipment(Long equipmentId, EquipmentDto equipmentDto, MultipartFile image) throws IOException {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid equipment id" + equipmentId));

        if (equipmentDto.getName() != null) {
            equipment.setName(equipmentDto.getName());
        }

        if (image != null && !image.isEmpty()) {

            Map r = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

            String imgUrl = (String) r.get("secure_url");

            System.out.println("====================================================");
            System.out.println("image url: "+ imgUrl);
            System.out.println("====================================================");
            equipment.setImageUrl(imgUrl);
        }

        Equipment equipmentUpdated = equipmentRepository.save(equipment);

        return mapToDto(equipmentUpdated);
    }

    @Override
    public void deleteEquipment(List<Long> ids) {
        ids.forEach(id -> {
            Equipment equipment = equipmentRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid equipment id " + id));
            if (equipment.getOwner() != null) {
                User owner = userRepository.findById(equipment.getOwner().getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user id "));
                Set<Equipment> equipments = owner.getEquipments();
                equipments.remove(owner);
            }

            if (equipment.getTransferredUser() != null) {
                for (User u : equipment.getTransferredUser()) {
                    u.getTransferredEquipment().remove(equipment);
                }
            }

//            Set<User> users = equipment.getTransferredUser();
//            users.forEach(u -> {
//                Set<Equipment> transferredEquipment = u.getTransferredEquipment();
//                transferredEquipment.remove(equipment);
//                u.setTransferredEquipment(transferredEquipment);
//                userRepository.save(u);
//            });

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

//          Set owner for equipment
            equipment.setOwner(userData);

//          Set transferred User for equipment
            Set<User> transferredUser = equipment.getTransferredUser();
            transferredUser.add(userData);
            equipment.setTransferredUser(transferredUser);

            equipmentRepository.save(equipment);
        });
        return userService.mapToDto(userData);
    }
}
