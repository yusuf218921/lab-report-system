package com.bigbox.labreports.system;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.core.results.SuccessDataResult;
import com.bigbox.labreports.system.core.results.SuccessResult;
import com.bigbox.labreports.system.entity.dtos.report.*;
import com.bigbox.labreports.system.entity.entities.LabTechnician;
import com.bigbox.labreports.system.entity.entities.Report;
import com.bigbox.labreports.system.repository.ReportRepository;
import com.bigbox.labreports.system.service.contracts.LabTechnicianService;
import com.bigbox.labreports.system.service.implementations.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private LabTechnicianService labTechnicianService;

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
        report = new Report(1L, "File123", "John", "Doe", "12345", "title", "details",  new Date(), null, labTechnician);
        reportForAddRequest = new ReportForAddRequest("File123", "John", "Doe", "12345", "title", "details", "2023-01-01 12:00:00", null, 1L);
        reportForUpdateRequest = new ReportForUpdateRequest(1L, "File123", "John", "Doe", "12345","title","details", "2023-01-01 12:00:00", null, 1L);
        reportForDeleteRequest = new ReportForDeleteRequest(1L);
        reportForListRequest = new ReportForListRequest(0, 10);
    }

    @Test
    void testAddReport() throws IOException, ParseException {
        lenient().when(labTechnicianService.getById(anyLong())).thenReturn(new SuccessDataResult<>(labTechnician, ""));
        lenient().when(reportRepository.save(any(Report.class))).thenReturn(report);
        lenient().when(modelMapper.map(any(ReportForAddRequest.class), eq(Report.class))).thenReturn(report);

        Result result = reportService.addReport(reportForAddRequest);

        assertTrue(result.isSuccess());
    }

    @Test
    void testAddReportWithNonExistentLabTechnician() throws IOException, ParseException {
        when(labTechnicianService.getById(anyLong())).thenReturn(new DataResult<>(null, false, "Lab technician not found"));

        Result result = reportService.addReport(reportForAddRequest);

        assertFalse(result.isSuccess());
        assertEquals("Lab technician not found", result.getMessage());
    }

    @Test
    void testUpdateReport() throws IOException, ParseException {
        lenient().when(reportRepository.findById(anyLong())).thenReturn(Optional.of(report));
        lenient().when(labTechnicianService.getById(anyLong())).thenReturn(new SuccessDataResult<>(labTechnician, ""));
        lenient().when(modelMapper.map(any(ReportForUpdateRequest.class), any(Class.class))).thenReturn(report);
        lenient().when(reportRepository.save(any(Report.class))).thenReturn(report);

        // Metodu çağırma
        SuccessResult result = (SuccessResult) reportService.updateReport(reportForUpdateRequest);

        // Assertion
        assertTrue(result.isSuccess());
    }

    @Test
    void testUpdateReportWithNonExistentReport() throws IOException, ParseException {
        when(reportRepository.findById(anyLong())).thenReturn(Optional.empty());

        Result result = reportService.updateReport(reportForUpdateRequest);

        assertFalse(result.isSuccess());
        assertEquals("Report not found", result.getMessage());
    }

    @Test
    void testUpdateReportWithNonExistentLabTechnician() throws IOException, ParseException {
        when(reportRepository.findById(anyLong())).thenReturn(Optional.of(report));
        when(labTechnicianService.getById(anyLong())).thenReturn(new DataResult<>(null, false, "Lab technician not found"));

        Result result = reportService.updateReport(reportForUpdateRequest);

        assertFalse(result.isSuccess());
        assertEquals("Lab technician not found", result.getMessage());
    }

    @Test
    void testDeleteReport() {
        when(reportRepository.findById(anyLong())).thenReturn(Optional.of(report));

        Result result = reportService.deleteReport(reportForDeleteRequest);

        assertTrue(result.isSuccess());
        assertEquals("Report deleted successfully", result.getMessage());
    }

    @Test
    void testDeleteReportWithNonExistentReport() {
        when(reportRepository.findById(anyLong())).thenReturn(Optional.empty());

        Result result = reportService.deleteReport(reportForDeleteRequest);

        assertFalse(result.isSuccess());
        assertEquals("Report not found", result.getMessage());
    }

    @Test
    void testGetReportById() {
        //report.setReportImage(new byte[] {1, 2, 3});

        when(reportRepository.findById(anyLong())).thenReturn(Optional.of(report));
        when(modelMapper.map(any(Report.class), any(Class.class))).thenReturn(new ReportForGetResponse());

        DataResult<ReportForGetResponse> result = reportService.getReportById(1L);

        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
    }

    @Test
    void testGetReportByIdWithNonExistentReport() {
        when(reportRepository.findById(anyLong())).thenReturn(Optional.empty());

        DataResult<ReportForGetResponse> result = reportService.getReportById(1L);

        assertFalse(result.isSuccess());
        assertEquals("Report not found", result.getMessage());
    }

    @Test
    void testGetAllReportsWithPage() {
        Pageable pageable = PageRequest.of(0, 10);
        when(reportRepository.findReportsByPatientAndTechnicianWithSort(any(ReportForListRequest.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(report), pageable, 1));

        when(modelMapper.map(any(Report.class), any(Class.class)))
                .thenAnswer(invocation -> {
                    ReportForListResponse response = new ReportForListResponse();
                    response.setFileNumber(report.getFileNumber());
                    response.setPatientFirstName(report.getPatientFirstName());
                    response.setPatientLastName(report.getPatientLastName());
                    response.setPatientIdNumber(report.getPatientIdNumber());
                    response.setReportDate(report.getReportDate().toString());
                    response.setLabTechnicianResponse(new ReportForListResponse.LabTechnicianResponse(
                            report.getLabTechnician().getLabTechnicianId(),
                            report.getLabTechnician().getFirstName(),
                            report.getLabTechnician().getLastName(),
                            report.getLabTechnician().getHospitalIdNumber()
                    ));
                    return response;
                });

        DataResult<Page<ReportForListResponse>> result = reportService.getAllReportsWithPage(reportForListRequest);

        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().getTotalElements());
        assertEquals(report.getFileNumber(), result.getData().getContent().get(0).getFileNumber());
    }
}
