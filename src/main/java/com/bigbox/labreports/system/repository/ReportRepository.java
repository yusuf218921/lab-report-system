package com.bigbox.labreports.system.repository;

import com.bigbox.labreports.system.entity.entities.Report;
import com.bigbox.labreports.system.service.contracts.ReportService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report , Long> {
    @Query("SELECT r FROM Report r JOIN r.labTechnician lt " +
            "WHERE (:patientFirstName IS NULL OR r.patientFirstName = :patientFirstName) " +
            "AND (:patientLastName IS NULL OR r.patientLastName = :patientLastName) " +
            "AND (:patientIdNumber IS NULL OR r.patientIdNumber = :patientIdNumber) " +
            "AND (:labTechnicianFirstName IS NULL OR lt.firstName = :labTechnicianFirstName) " +
            "AND (:labTechnicianLastName IS NULL OR lt.lastName = :labTechnicianLastName) " +
            "ORDER BY r.reportDate :sortDirection")
    List<Report> findReportsByPatientAndTechnician(
            @Param("patientFirstName") String patientFirstName,
            @Param("patientLastName") String patientLastName,
            @Param("patientIdNumber") String patientIdNumber,
            @Param("labTechnicianFirstName") String labTechnicianFirstName,
            @Param("labTechnicianLastName") String labTechnicianLastName,
            @Param("sortDirection") String sortDirection);
}
