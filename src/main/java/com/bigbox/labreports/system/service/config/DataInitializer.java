package com.bigbox.labreports.system.config;

import com.bigbox.labreports.system.core.entity.User;
import com.bigbox.labreports.system.core.entity.OperationClaim;
import com.bigbox.labreports.system.entity.entities.LabTechnician;
import com.bigbox.labreports.system.entity.entities.Report;
import com.bigbox.labreports.system.repository.UserRepository;
import com.bigbox.labreports.system.repository.OperationClaimRepository;
import com.bigbox.labreports.system.repository.LabTechnicianRepository;
import com.bigbox.labreports.system.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner dataLoader(UserRepository userRepository, OperationClaimRepository operationClaimRepository, LabTechnicianRepository labTechnicianRepository, ReportRepository reportRepository) {
        return args -> {
            // Create roles
            OperationClaim userRole = new OperationClaim();
            userRole.setOperationClaimName("ROLE_USER");
            operationClaimRepository.save(userRole);

            OperationClaim adminRole = new OperationClaim();
            adminRole.setOperationClaimName("ROLE_ADMIN");
            operationClaimRepository.save(adminRole);

            // Create users
            User user = new User();
            user.setUsername("user");
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("password"));
            Set<OperationClaim> userRoles = new HashSet<>();
            userRoles.add(userRole);
            user.setOperationClaims(userRoles);
            userRepository.save(user);

            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("password"));
            Set<OperationClaim> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            admin.setOperationClaims(adminRoles);
            userRepository.save(admin);

            // Create a lab technician
            LabTechnician labTechnician = new LabTechnician();
            labTechnician.setFirstName("John");
            labTechnician.setLastName("Doe");
            labTechnician.setHospitalIdNumber("12345");
            labTechnicianRepository.save(labTechnician);

            // Create a report
            Report report = new Report();
            report.setFileNumber("123");
            report.setPatientFirstName("Jane");
            report.setPatientLastName("Doe");
            report.setPatientIdNumber("67890");
            report.setReportDate(new java.util.Date());
            report.setLabTechnician(labTechnician);
            report.setReportImage(new byte[0]); // Empty image for example
            reportRepository.save(report);
        };
    }
}
