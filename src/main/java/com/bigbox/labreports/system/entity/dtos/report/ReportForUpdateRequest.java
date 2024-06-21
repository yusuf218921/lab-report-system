package com.bigbox.labreports.system.entity.dtos.report;
import com.bigbox.labreports.system.core.validation.StartsWith;
import com.bigbox.labreports.system.core.validation.UniqueValue;
import com.bigbox.labreports.system.entity.entities.Report;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportForUpdateRequest {

    @NotBlank(message = "Report Id is mandatory")
    private Long reportId;

    @StartsWith(prefix = "F")
    @NotBlank(message = "File number is mandatory")
    @UniqueValue(entityClass = Report.class, fieldName = "fileNumber")
    private String fileNumber;

    @NotBlank(message = "Patient first name is mandatory")
    private String patientFirstName;

    @NotBlank(message = "Patient last name is mandatory")
    private String patientLastName;

    @StartsWith(prefix = "P")
    @UniqueValue(entityClass = Report.class, fieldName = "patientIdNumber")
    @NotBlank(message = "Patient ID number is mandatory")
    private String patientIdNumber;

    @NotBlank(message = "Diagnosis title is mandatory")
    private String diagnosisTitle;

    @NotBlank(message = "Diagnosis details is mandatory")
    private String diagnosisDetails;

    @NotNull(message = "Report date is mandatory")
    private String reportDate;

    private MultipartFile reportImage = null;

    @NotNull(message = "Lab technician ID is mandatory")
    private Long labTechnicianId;
}