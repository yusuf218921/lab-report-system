package com.bigbox.labreports.system.entity.dtos.report;

import com.bigbox.labreports.system.entity.entities.LabTechnician;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportForListResponse {
    private String fileNumber;
    private String patientFirstName;
    private String patientLastName;
    private String patientIdNumber;
    private String reportDate;
    private LabTechnicianResponse labTechnicianResponse;

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
