package com.bigbox.labreports.system.repository;

import com.bigbox.labreports.system.entity.dtos.ReportForListRequest;
import com.bigbox.labreports.system.entity.entities.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report , Long> {
    @Query("SELECT r FROM Report r JOIN r.labTechnician lt " +
            "WHERE (:#{#request.patientFirstName} IS NULL OR r.patientFirstName = :#{#request.patientFirstName}) " +
            "AND (:#{#request.patientLastName} IS NULL OR r.patientLastName = :#{#request.patientLastName}) " +
            "AND (:#{#request.patientIdNumber} IS NULL OR r.patientIdNumber = :#{#request.patientIdNumber}) " +
            "AND (:#{#request.labTechnicianFirstName} IS NULL OR lt.firstName = :#{#request.labTechnicianFirstName}) " +
            "AND (:#{#request.labTechnicianLastName} IS NULL OR lt.lastName = :#{#request.labTechnicianLastName})")
    Page<Report> findReportsByPatientAndTechnician(
            @Param("request") ReportForListRequest request,
            Pageable pageable);

    @Query("SELECT r FROM Report r JOIN r.labTechnician lt " +
            "WHERE (:#{#request.patientFirstName} IS NULL OR r.patientFirstName = :#{#request.patientFirstName}) " +
            "AND (:#{#request.patientLastName} IS NULL OR r.patientLastName = :#{#request.patientLastName}) " +
            "AND (:#{#request.patientIdNumber} IS NULL OR r.patientIdNumber = :#{#request.patientIdNumber}) " +
            "AND (:#{#request.labTechnicianFirstName} IS NULL OR lt.firstName = :#{#request.labTechnicianFirstName}) " +
            "AND (:#{#request.labTechnicianLastName} IS NULL OR lt.lastName = :#{#request.labTechnicianLastName}) " +
            "ORDER BY r.reportDate :#{#request.sortDirection}")
    Page<Report> findReportsByPatientAndTechnicianWithSort(
            @Param("request") ReportForListRequest request,
            Pageable pageable);
}
