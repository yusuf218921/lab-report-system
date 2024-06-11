package com.bigbox.labreports.system.service.contracts;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForAddRequest;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForDeleteRequest;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForUpdateRequest;
import com.bigbox.labreports.system.entity.entities.LabTechnician;

import javax.xml.crypto.Data;

public interface LabTechnicianService {
    DataResult<LabTechnician> getById(long id);
    DataResult<LabTechnician> addLabTechnician(LabTechnicianForAddRequest request);
    DataResult<LabTechnician> updateLabTechnician(LabTechnicianForUpdateRequest request);
    Result deleteLabTechnician(LabTechnicianForDeleteRequest request);
}
