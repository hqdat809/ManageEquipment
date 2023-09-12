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
//    List<Equipment> findAllByName(String name);

//    @Query("SELECT e from Equipment e where e.name like %:name%")
    List<Equipment> findByNameContaining(String name);


    @Query(value = "SELECT * FROM equipment WHERE NAME LIKE %:name%",
            countQuery = "SELECT count(*) FROM equipment WHERE LIKE %:name%",
            nativeQuery = true)
    Page<Equipment> findByName(String name, Pageable pageable);
}
