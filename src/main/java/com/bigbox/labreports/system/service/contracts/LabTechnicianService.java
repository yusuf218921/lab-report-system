package com.bigbox.labreports.system.service.contracts;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.entity.entities.LabTechnician;

import javax.xml.crypto.Data;

public interface LabTechnicianService {
    DataResult<LabTechnician> getById(long id);
    DataResult<LabTechnician> addLabTechnician();
    DataResult<LabTechnician> updateLabTechnician();
    Result deleteLabTechnician();
}
