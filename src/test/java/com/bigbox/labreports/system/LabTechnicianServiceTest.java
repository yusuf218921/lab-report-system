package com.bigbox.labreports.system;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.entity.dtos.labTechnician.*;
import com.bigbox.labreports.system.entity.dtos.report.*;
import com.bigbox.labreports.system.entity.entities.LabTechnician;
import com.bigbox.labreports.system.entity.entities.Report;
import com.bigbox.labreports.system.repository.LabTechnicianRepository;
import com.bigbox.labreports.system.repository.ReportRepository;
import com.bigbox.labreports.system.service.implementations.LabTechnicianServiceImpl;
import com.bigbox.labreports.system.service.implementations.ReportHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LabTechnicianServiceTest {

    @Mock
    private LabTechnicianRepository labTechnicianRepository;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private LabTechnicianServiceImpl labTechnicianService;

    @Mock
    private ReportHelper reportHelper;

    private LabTechnician labTechnician;
    private Report report;
    private LabTechnicianForAddRequest labTechnicianForAddRequest;
    private LabTechnicianForUpdateRequest labTechnicianForUpdateRequest;
    private LabTechnicianForDeleteRequest labTechnicianForDeleteRequest;

    @BeforeEach
    void setUp() {
        labTechnician = new LabTechnician(1L, "John", "Doe", "H12345", null);
        report = new Report(1L, "F123", "John", "Doe", "P12345", "title", "details",  new Date(), null, labTechnician);
        labTechnicianForAddRequest = new LabTechnicianForAddRequest("John", "Doe", "H12345");
        labTechnicianForUpdateRequest = new LabTechnicianForUpdateRequest(1L, "John", "Doe", "H12345");
        labTechnicianForDeleteRequest = new LabTechnicianForDeleteRequest(1L);
    }

    @Test
    void testGetById() {
        when(labTechnicianRepository.findById(1L)).thenReturn(Optional.of(labTechnician));

        DataResult<LabTechnician> result = labTechnicianService.getById(1L);

        assertTrue(result.isSuccess());
        assertEquals(labTechnician, result.getData());
    }

    @Test
    void testGetByIdWithNonExistentId() {
        when(labTechnicianRepository.findById(1L)).thenReturn(Optional.empty());

        DataResult<LabTechnician> result = labTechnicianService.getById(1L);

        assertFalse(result.isSuccess());
        assertEquals("Lab technician not found", result.getMessage());
    }

    @Test
    void testAddLabTechnician() {
        when(modelMapper.map(any(LabTechnicianForAddRequest.class), any(Class.class))).thenReturn(labTechnician);
        when(labTechnicianRepository.save(any(LabTechnician.class))).thenReturn(labTechnician);

        Result result = labTechnicianService.addLabTechnician(labTechnicianForAddRequest);

        assertTrue(result.isSuccess());
    }

    @Test
    void testUpdateLabTechnician() {
        when(labTechnicianRepository.findById(1L)).thenReturn(Optional.of(labTechnician));
        when(labTechnicianRepository.save(any(LabTechnician.class))).thenReturn(labTechnician);

        Result result = labTechnicianService.updateLabTechnician(labTechnicianForUpdateRequest);

        assertTrue(result.isSuccess());
    }

    @Test
    void testUpdateLabTechnicianWithNonExistentId() {
        when(labTechnicianRepository.findById(1L)).thenReturn(Optional.empty());

        Result result = labTechnicianService.updateLabTechnician(labTechnicianForUpdateRequest);

        assertFalse(result.isSuccess());
        assertEquals("Lab technician not found", result.getMessage());
    }

    @Test
    void testDeleteLabTechnician() {
        when(labTechnicianRepository.existsById(1L)).thenReturn(true);

        Result result = labTechnicianService.deleteLabTechnician(labTechnicianForDeleteRequest);

        assertTrue(result.isSuccess());
        assertEquals("Lab technician deleted successfully", result.getMessage());
    }

    @Test
    void testDeleteLabTechnicianWithNonExistentId() {
        when(labTechnicianRepository.existsById(1L)).thenReturn(false);

        Result result = labTechnicianService.deleteLabTechnician(labTechnicianForDeleteRequest);

        assertFalse(result.isSuccess());
        assertEquals("Lab technician not found", result.getMessage());
    }

    @Test
    void testGetByIdReturnResponseDto() {
        LabTechnicianForGetResponse responseDto = new LabTechnicianForGetResponse();
        responseDto.setReports(Collections.singletonList(new ReportForListResponse()));

        when(labTechnicianRepository.findById(1L)).thenReturn(Optional.of(labTechnician));
        when(reportHelper.getReportsByLabTechnicianId(1L, modelMapper)).thenReturn(Collections.singletonList(new ReportForListResponse()));
        when(modelMapper.map(any(LabTechnician.class), any(Class.class))).thenReturn(responseDto);

        DataResult<LabTechnicianForGetResponse> result = labTechnicianService.getByIdReturnResponseDto(1L);

        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        assertEquals(1, result.getData().getReports().size());
    }

    @Test
    void testGetByIdReturnResponseDtoWithNonExistentId() {
        when(labTechnicianRepository.findById(1L)).thenReturn(Optional.empty());

        DataResult<LabTechnicianForGetResponse> result = labTechnicianService.getByIdReturnResponseDto(1L);

        assertFalse(result.isSuccess());
        assertEquals("Lab technician not found", result.getMessage());
    }
}
