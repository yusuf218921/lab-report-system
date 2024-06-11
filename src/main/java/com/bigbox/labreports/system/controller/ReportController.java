package com.bigbox.labreports.system.controller;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.entity.dtos.report.ReportForAddRequest;
import com.bigbox.labreports.system.entity.dtos.report.ReportForDeleteRequest;
import com.bigbox.labreports.system.entity.dtos.report.ReportForListRequest;
import com.bigbox.labreports.system.entity.dtos.report.ReportForUpdateRequest;
import com.bigbox.labreports.system.entity.entities.Report;
import com.bigbox.labreports.system.service.contracts.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<DataResult<Page<Report>>> getAll(@ModelAttribute ReportForListRequest request) {
        DataResult<Page<Report>> result = reportService.getAllPage(request);
        return new ResponseEntity<>(result, result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<DataResult<Report>> addReport(
            @RequestPart("report") String reportString,
            @RequestPart("reportImage") MultipartFile reportImage
    ) throws IOException, ParseException {
        // Parse the JSON string to ReportForAddRequest
        ObjectMapper objectMapper = new ObjectMapper();
        ReportForAddRequest request = objectMapper.readValue(reportString, ReportForAddRequest.class);

        request.setReportImage(reportImage);
        DataResult<Report> result = reportService.addReport(request);
        return new ResponseEntity<>(result, result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<DataResult<Report>> updateReport(@RequestBody ReportForUpdateRequest request) throws IOException, ParseException {
        DataResult<Report> result = reportService.updateReport(request);
        return new ResponseEntity<>(result, result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<Result> deleteReport(@RequestBody ReportForDeleteRequest request) {
        Result result = reportService.deleteReport(request);
        return new ResponseEntity<>(result, result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
