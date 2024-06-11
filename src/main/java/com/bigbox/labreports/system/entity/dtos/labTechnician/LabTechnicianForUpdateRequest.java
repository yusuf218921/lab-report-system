package com.bigbox.labreports.system.entity.dtos.labTechnician;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabTechnicianForUpdateRequest {
    private Long labTechnicianId;
    private String firstName;
    private String lastName;
    private String hospitalIdNumber;
}
