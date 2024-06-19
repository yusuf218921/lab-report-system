package com.bigbox.labreports.system.entity.dtos.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportForListRequest {
    private String patientFirstName;
    private String patientLastName;
    private String patientIdNumber;
    private String labTechnicianFirstName;
    private String labTechnicianLastName;
    private String sortDirection;

    private int pageNo;
    private int pageSize;

    public ReportForListRequest(int pageNo, int pageSize){
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
