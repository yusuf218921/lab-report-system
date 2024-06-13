package com.bigbox.labreports.system.service.contracts;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.entity.dtos.report.*;
import com.bigbox.labreports.system.entity.entities.Report;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface ReportService {

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    DataResult<Page<ReportForListResponse>> getAllReportsWithPage(ReportForListRequest request);

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    DataResult<Report>  addReport(ReportForAddRequest request) throws IOException, ParseException;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    Result deleteReport(ReportForDeleteRequest request);

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    DataResult<Report> updateReport(ReportForUpdateRequest request) throws IOException, ParseException;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    DataResult<ReportForGetResponse> getReportById(Long reportId);


}
