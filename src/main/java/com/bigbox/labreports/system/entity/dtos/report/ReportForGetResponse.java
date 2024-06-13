package com.bigbox.labreports.system.entity.dtos.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportForGetResponse {

    private Long reportId;
    private String fileNumber;
    private String patientFirstName;
    private String patientLastName;
    private String patientIdNumber;
    private String diagnosisTitle;
    private String diagnosisDetails;
    private String reportDate;
    private String reportImage;
    private LabTechnicianResponse labTechnician;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LabTechnicianResponse {
        private Long labTechnicianId;
        private String firstName;
        private String lastName;
        private String hospitalIdNumber;
    }
}
