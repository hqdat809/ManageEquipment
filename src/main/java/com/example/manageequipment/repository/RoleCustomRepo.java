package com.example.manageequipment.repository;

import com.example.manageequipment.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleCustomRepo extends JpaRepository<Role, Integer> {
    @Query("SELECT u.roles FROM User u WHERE u.email = :email")
    Set<Role> findRoleByEmail(@Param("email") String email);

    Optional<Role> findByName(String name);

}
