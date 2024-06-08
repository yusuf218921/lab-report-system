package com.bigbox.labreports.system.service.contracts;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.entity.dtos.ReportForAddRequest;
import com.bigbox.labreports.system.entity.dtos.ReportForDeleteRequest;
import com.bigbox.labreports.system.entity.dtos.ReportForListRequest;
import com.bigbox.labreports.system.entity.dtos.ReportForUpdateRequest;
import com.bigbox.labreports.system.entity.entities.Report;
import org.springframework.data.domain.Page;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.text.ParseException;

public interface ReportService {
    DataResult<Page<Report>> getAllPage(ReportForListRequest request);
    DataResult<Report>  addReport(ReportForAddRequest request) throws IOException, ParseException;
    Result deleteReport(ReportForDeleteRequest request);
    DataResult<Report> updateReport(ReportForUpdateRequest request) throws IOException, ParseException;
}
