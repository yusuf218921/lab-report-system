package com.bigbox.labreports.system.entity.dtos.report;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
public class ReportForAddRequest {

    private String fileNumber;
    private String patientFirstName;
    private String patientLastName;
    private String patientIdNumber;
    private String diagnosisTitle;
    private String diagnosisDetails;
    private String reportDate;
    private MultipartFile reportImage;
    private Long labTechnicianId;
}
