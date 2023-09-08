package com.example.manageequipment.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EquipmentDto {
    private Long id;
    private String name;
    private String imageUrl;
    private Long ownerId;
    private List<Long> transferredUserIds = new ArrayList<>();
}
