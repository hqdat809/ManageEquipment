package com.example.manageequipment.model;

import com.example.manageequipment.auth.CustomAuthorityDeserializer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50, message = "Name should be less than 50 characters")
    private String firstName;

    @Size(max = 50, message = "Name should be less than 50 characters")
    private String lastName;

    @JsonBackReference
//    @ValidPassword(message = "Password is invalid!!")
    private String password;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotNull(message = "Email shouldn't be null!!")
    @Column(unique = true)
    private String email;


    @Size(max = 50, message = "Name should be less than 50 characters")
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private Set<Equipment> equipments = new HashSet<>();

    @ManyToMany(mappedBy = "transferredUser")
    @JsonIgnore
    private Set<Equipment> transferredEquipment = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    @JsonBackReference
    private Set<Role> roles = new HashSet<>();




    @Override
    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (roles != null) {
            roles.forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getName())));
        }
        return List.of(new SimpleGrantedAuthority(authorities.toString()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

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
