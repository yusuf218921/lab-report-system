package com.bigbox.labreports.system.entity.dtos.labTechnician;

import com.bigbox.labreports.system.core.validation.StartsWith;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LabTechnicianForAddRequest {

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @StartsWith(prefix = "H")
    @NotBlank(message = "Hospital ID number is mandatory")
    private String hospitalIdNumber;

}

