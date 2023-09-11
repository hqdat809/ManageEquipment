package com.example.manageequipment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50, message = "Name should be less than 50 characters")
    private String firstName;

    @Size(max = 50, message = "Name should be less than 50 characters")
    private String lastName;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotNull(message = "Email shouldn't be null!!")
    @Column(unique = true)
    private String email;


    @Size(max = 50, message = "Name should be less than 50 characters")
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private Set<Equipment> equipments = new HashSet<>();

    @ManyToMany(mappedBy = "transferredUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Equipment> transferredEquipment = new HashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", equipments=" + equipments +
                '}';
    }
}
