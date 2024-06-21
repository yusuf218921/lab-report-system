package com.bigbox.labreports.system.entity.dtos.labTechnician;

import com.bigbox.labreports.system.entity.dtos.report.ReportForListResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LabTechnicianForGetResponse {
    private Long labTechnicianId;
    private String firstName;
    private String lastName;
    private String hospitalIdNumber;
    private List<ReportForListResponse> reports;
}
