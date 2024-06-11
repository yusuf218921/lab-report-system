package com.bigbox.labreports.system.entity.dtos.labTechnician;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabTechnicianForAddRequest {
    private String firstName;
    private String lastName;
    private String hospitalIdNumber;
}
