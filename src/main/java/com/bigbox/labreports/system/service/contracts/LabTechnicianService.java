package com.bigbox.labreports.system.service.contracts;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForAddRequest;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForDeleteRequest;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForUpdateRequest;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForGetResponse;
import com.bigbox.labreports.system.entity.entities.LabTechnician;
import org.springframework.security.access.prepost.PreAuthorize;

public interface LabTechnicianService {
    @PreAuthorize("hasRole('ADMIN')")
    DataResult<LabTechnician> getById(long id);

    @PreAuthorize("hasRole('ADMIN')")
    Result addLabTechnician(LabTechnicianForAddRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    Result updateLabTechnician(LabTechnicianForUpdateRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    Result deleteLabTechnician(LabTechnicianForDeleteRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    DataResult<LabTechnicianForGetResponse> getByIdReturnResponseDto(Long id);

}
