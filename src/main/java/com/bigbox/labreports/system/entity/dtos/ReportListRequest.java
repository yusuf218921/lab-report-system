package com.bigbox.labreports.system.entity.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportListRequest {
    private String patientFirstName;
    private String patientLastName;
    private String patientIdNumber;
    private String labTechnicianFirstName;
    private String labTechnicianLastName;
    private String sortDirection;
}
