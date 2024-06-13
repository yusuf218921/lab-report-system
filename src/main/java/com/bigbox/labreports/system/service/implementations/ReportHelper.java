package com.bigbox.labreports.system.service.implementations;

import com.bigbox.labreports.system.entity.dtos.report.ReportForListResponse;
import com.bigbox.labreports.system.entity.entities.Report;
import com.bigbox.labreports.system.repository.ReportRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportHelper {

    @Autowired
    private ReportRepository reportRepository;

    public List<ReportForListResponse> getReportsByLabTechnicianId(Long labTechnicianId, ModelMapper modelMapper) {
        List<Report> reports = reportRepository.getByLabTechnician_labTechnicianId(labTechnicianId);
        return reports.stream()
                .map(report -> {
                    ReportForListResponse response = modelMapper.map(report, ReportForListResponse.class);
                    response.setLabTechnicianResponse(new ReportForListResponse.LabTechnicianResponse(
                            report.getLabTechnician().getLabTechnicianId(),
                            report.getLabTechnician().getFirstName(),
                            report.getLabTechnician().getLastName(),
                            report.getLabTechnician().getHospitalIdNumber()
                    ));
                    return response;
                })
                .collect(Collectors.toList());
    }
}
