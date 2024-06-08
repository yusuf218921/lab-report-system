package com.bigbox.labreports.system.repository;

import com.bigbox.labreports.system.entity.entities.LabTechnician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabTechnicianRepository extends JpaRepository<LabTechnician, Long> {

}
