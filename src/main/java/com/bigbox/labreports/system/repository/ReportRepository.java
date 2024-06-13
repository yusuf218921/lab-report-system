package com.bigbox.labreports.system.repository;

import com.bigbox.labreports.system.entity.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report , Long>, ReportRepositoryCustom {
    List<Report> getByLabTechnician_labTechnicianId(Long labTechnicianId);
}
