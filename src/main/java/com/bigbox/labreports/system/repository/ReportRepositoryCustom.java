package com.bigbox.labreports.system.repository;

import com.bigbox.labreports.system.entity.entities.Report;
import com.bigbox.labreports.system.entity.dtos.report.ReportForListRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportRepositoryCustom {
    Page<Report> findReportsByPatientAndTechnicianWithSort(ReportForListRequest request, Pageable pageable);
}
