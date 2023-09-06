package com.example.manageequipment.repository;

import com.example.manageequipment.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageData, Long> {
}
