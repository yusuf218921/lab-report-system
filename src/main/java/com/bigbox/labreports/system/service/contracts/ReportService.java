package com.bigbox.labreports.system.service.contracts;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.entity.dtos.report.ReportForAddRequest;
import com.bigbox.labreports.system.entity.dtos.report.ReportForDeleteRequest;
import com.bigbox.labreports.system.entity.dtos.report.ReportForListRequest;
import com.bigbox.labreports.system.entity.dtos.report.ReportForUpdateRequest;
import com.bigbox.labreports.system.entity.entities.Report;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.text.ParseException;

public interface ReportService {
    DataResult<Page<Report>> getAllPage(ReportForListRequest request);
    DataResult<Report>  addReport(ReportForAddRequest request) throws IOException, ParseException;
    Result deleteReport(ReportForDeleteRequest request);
    DataResult<Report> updateReport(ReportForUpdateRequest request) throws IOException, ParseException;
}
