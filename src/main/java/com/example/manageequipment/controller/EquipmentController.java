package com.example.manageequipment.controller;

import com.example.manageequipment.dto.EquipmentDto;
import com.example.manageequipment.dto.UserDto;
import com.example.manageequipment.model.Equipment;
import com.example.manageequipment.service.EquipmentService;
import com.example.manageequipment.type.IntegerArrayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    @PostMapping("/create")
    public ResponseEntity<EquipmentDto> createEquipment(@RequestBody Equipment equipment) {
        System.out.println(equipment);
        return new ResponseEntity<>(equipmentService.createEquipment(equipment), HttpStatus.CREATED);
    }

    @GetMapping("/equipments")
    public ResponseEntity<List<EquipmentDto>> getEquipments() {
        return new ResponseEntity<>(equipmentService.getAllEquipment(), HttpStatus.OK);
    }

    @PostMapping("/update/{equipmentId}")
    public ResponseEntity<EquipmentDto> updateEquipment(@PathVariable Long equipmentId, @RequestBody EquipmentDto equipmentDto) {
        return new ResponseEntity<>(equipmentService.updateEquipment(equipmentId, equipmentDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public  ResponseEntity<String> deleteEquipment(@RequestBody IntegerArrayRequest equipmentIds) {
        equipmentService.deleteEquipment(equipmentIds.getIds());
        return new ResponseEntity<>("Delete equipment success!!", HttpStatus.OK);
    }

    @PostMapping("/transfer/{userId}")
    public ResponseEntity<UserDto> transferEquipment(@RequestBody IntegerArrayRequest equipmentIds, @PathVariable Long userId) {
        return new ResponseEntity<>(equipmentService.transferEquipment(equipmentIds.getIds(), userId), HttpStatus.OK);
    }
}

