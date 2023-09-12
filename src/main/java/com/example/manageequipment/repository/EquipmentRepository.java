package com.example.manageequipment.repository;

import com.example.manageequipment.model.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findAllByName(String name);

    List<Equipment> findAllByNameContains(String name);

    Page<Equipment> findAllByNameContains2(String name);

    @Query(value = "SELECT * FROM EQUIPMENT WHERE NAME = ?1",
            countQuery = "SELECT count(*) FROM EQUIPMENT WHERE NAME = ?1",
            nativeQuery = true)
    Page<Equipment> findByName(String lastname, Pageable pageable);
}
