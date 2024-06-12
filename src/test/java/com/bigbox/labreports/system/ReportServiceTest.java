package com.bigbox.labreports.system;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.ErrorDataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.core.results.SuccessDataResult;
import com.bigbox.labreports.system.entity.dtos.report.ReportForAddRequest;
import com.bigbox.labreports.system.entity.dtos.report.ReportForDeleteRequest;
import com.bigbox.labreports.system.entity.dtos.report.ReportForListRequest;
import com.bigbox.labreports.system.entity.dtos.report.ReportForUpdateRequest;
import com.bigbox.labreports.system.entity.entities.LabTechnician;
import com.bigbox.labreports.system.entity.entities.Report;
import com.bigbox.labreports.system.repository.ReportRepository;
import com.bigbox.labreports.system.service.implementations.LabTechnicianServiceImpl;
import com.bigbox.labreports.system.service.implementations.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private LabTechnicianServiceImpl labTechnicianService;

    @InjectMocks
    private ReportServiceImpl reportService;

    private Report report;
    private ReportForAddRequest reportForAddRequest;
    private ReportForUpdateRequest reportForUpdateRequest;
    private ReportForDeleteRequest reportForDeleteRequest;
    private ReportForListRequest reportForListRequest;
    private LabTechnician labTechnician;

    @BeforeEach
    void setUp() {
        labTechnician = new LabTechnician(1L, "John", "Doe", "12345", null);
        report = new Report(1L, "F12345", "Jane", "Smith", "P123456", "Diagnosis Title", "Diagnosis Details", new Date(), null, labTechnician);

        reportForAddRequest = new ReportForAddRequest();
        reportForAddRequest.setFileNumber("F12345");
        reportForAddRequest.setPatientFirstName("Jane");
        reportForAddRequest.setPatientLastName("Smith");
        reportForAddRequest.setPatientIdNumber("P123456");
        reportForAddRequest.setDiagnosisTitle("Diagnosis Title");
        reportForAddRequest.setDiagnosisDetails("Diagnosis Details");
        reportForAddRequest.setReportDate("2024-06-11 10:00:00");
        reportForAddRequest.setLabTechnicianId(1L);

        reportForUpdateRequest = new ReportForUpdateRequest();
        reportForUpdateRequest.setReportId(1L);
        reportForUpdateRequest.setFileNumber("F12345");
        reportForUpdateRequest.setPatientFirstName("Jane");
        reportForUpdateRequest.setPatientLastName("Smith");
        reportForUpdateRequest.setPatientIdNumber("P123456");
        reportForUpdateRequest.setDiagnosisTitle("Updated Diagnosis Title");
        reportForUpdateRequest.setDiagnosisDetails("Updated Diagnosis Details");
        reportForUpdateRequest.setReportDate("2024-06-12 11:00:00");
        reportForUpdateRequest.setLabTechnicianId(1L);

        reportForDeleteRequest = new ReportForDeleteRequest();
        reportForDeleteRequest.setReportId(1L);

        reportForListRequest = new ReportForListRequest();
        reportForListRequest.setPageNo(0);
        reportForListRequest.setPageSize(10);
    }

    @Test
    void testAddReport() throws IOException, ParseException {
        when(modelMapper.map(any(ReportForAddRequest.class), any(Class.class))).thenReturn(report);
        when(labTechnicianService.getById(1L)).thenReturn(new SuccessDataResult<>(labTechnician));
        when(reportRepository.save(any(Report.class))).thenReturn(report);

        DataResult<Report> result = reportService.addReport(reportForAddRequest);

        assertTrue(result.isSuccess());
        assertEquals(report, result.getData());
    }

    @Test
    void testAddReportWithNonExistentLabTechnician() throws IOException, ParseException {
        when(labTechnicianService.getById(1L)).thenReturn(new ErrorDataResult<>("Lab technician not found"));
        DataResult<Report> result = reportService.addReport(reportForAddRequest);

        assertFalse(result.isSuccess());
        assertNull(result.getData());
        assertEquals("Lab technician not found", result.getMessage());
    }

    @Test
    void testUpdateReport() throws IOException, ParseException {
        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));
        when(labTechnicianService.getById(1L)).thenReturn(new SuccessDataResult<>(labTechnician));
        when(modelMapper.map(any(ReportForUpdateRequest.class), eq(Report.class))).thenReturn(report);
        when(reportRepository.save(any(Report.class))).thenReturn(report);

        DataResult<Report> result = reportService.updateReport(reportForUpdateRequest);

        assertTrue(result.isSuccess());
        assertEquals(report, result.getData());
    }

    @Test
    void testUpdateNonExistentReport() throws IOException, ParseException {
        when(reportRepository.findById(1L)).thenReturn(Optional.empty());

        DataResult<Report> result = reportService.updateReport(reportForUpdateRequest);

        assertFalse(result.isSuccess());
        assertEquals("Report not found", result.getMessage());
    }

    @Test
    void testUpdateReportWithNonExistentLabTechnician() throws IOException, ParseException {
        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));
        when(labTechnicianService.getById(1L)).thenReturn(new ErrorDataResult<>("Lab technician not found"));

        DataResult<Report> result = reportService.updateReport(reportForUpdateRequest);

        assertFalse(result.isSuccess());
        assertEquals("Lab technician not found", result.getMessage());
    }

    @Test
    void testDeleteReport() {
        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));

        Result result = reportService.deleteReport(reportForDeleteRequest);

        assertTrue(result.isSuccess());
        assertEquals("Report deleted successfully", result.getMessage());
    }


    @Test
    void testDeleteNonExistentReport() {
        when(reportRepository.findById(1L)).thenReturn(Optional.empty());

        Result result = reportService.deleteReport(reportForDeleteRequest);

        assertFalse(result.isSuccess());
        assertEquals("Report not found", result.getMessage());
    }

    @Test
    void testGetAllReports() {
        Pageable pageable = PageRequest.of(0, 10);
        when(reportRepository.findReportsByPatientAndTechnicianWithSort(any(ReportForListRequest.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(report), pageable, 1));

        DataResult<Page<Report>> result = reportService.getAllPage(reportForListRequest);

        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().getTotalElements());
        assertEquals(report, result.getData().getContent().get(0));
    }

}
