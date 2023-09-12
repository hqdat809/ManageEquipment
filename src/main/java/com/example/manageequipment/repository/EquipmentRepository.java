package com.example.manageequipment.repository;

import com.example.manageequipment.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findAllByName(String name);
}
