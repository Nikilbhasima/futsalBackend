package com.futsalBooking.advanceJavaProject.repository;

import com.futsalBooking.advanceJavaProject.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleServiceRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(String roleAdmin);
}
