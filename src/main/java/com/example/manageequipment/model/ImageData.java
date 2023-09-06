package com.example.manageequipment.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Column(name = "imagedata",length = 50000000)
    private byte[] imageData;

    @OneToOne(mappedBy = "imageData")
    private Equipment equipment;
}
