package com.bigbox.labreports.system.entity.dtos.labTechnician;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LabTechnicianForUpdateRequest {
    private Long labTechnicianId;
    private String firstName;
    private String lastName;
    private String hospitalIdNumber;
}
