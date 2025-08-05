package com.futsalBooking.advanceJavaProject.dataCentralization;

import com.futsalBooking.advanceJavaProject.model.Role;
import com.futsalBooking.advanceJavaProject.repository.RoleServiceRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataCentralization {

    @Autowired
    private RoleServiceRepository roleServiceRepository;

    @PostConstruct
    public void initData() {
        if (roleServiceRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setRole("ROLE_ADMIN");

            Role ownerRole = new Role();
            ownerRole.setRole("ROLE_OWNER");

            Role userRole = new Role();
            userRole.setRole("ROLE_USER");

            roleServiceRepository.saveAll(Arrays.asList(adminRole, ownerRole, userRole));
            System.out.println("Default roles inserted successfully!");
        } else {
            System.out.println("Roles already exist in database");
        }
    }
}
