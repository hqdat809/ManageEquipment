package com.example.manageequipment.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EquipmentDto {
    private Long id;
    private String name;
    private Long ownerId;
}
