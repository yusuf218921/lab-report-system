package com.bigbox.labreports.system;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForAddRequest;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForDeleteRequest;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForUpdateRequest;
import com.bigbox.labreports.system.entity.entities.LabTechnician;
import com.bigbox.labreports.system.repository.LabTechnicianRepository;
import com.bigbox.labreports.system.service.implementations.LabTechnicianServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LabTechnicianServiceTest {

    @Mock
    private LabTechnicianRepository labTechnicianRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private LabTechnicianServiceImpl labTechnicianService;

    private LabTechnician labTechnician;
    private LabTechnicianForAddRequest labTechnicianForAddRequest;
    private LabTechnicianForUpdateRequest labTechnicianForUpdateRequest;
    private LabTechnicianForDeleteRequest labTechnicianForDeleteRequest;

    @BeforeEach
    void setUp() {
        labTechnician = new LabTechnician(1L, "John", "Doe", "12345", null);
        labTechnicianForAddRequest = new LabTechnicianForAddRequest("John", "Doe", "12345");
        labTechnicianForUpdateRequest = new LabTechnicianForUpdateRequest(1L, "John", "Doe", "12345");
        labTechnicianForDeleteRequest = new LabTechnicianForDeleteRequest(1L);
    }

    @Test
    void testAddLabTechnician() {
        when(modelMapper.map(any(LabTechnicianForAddRequest.class), eq(LabTechnician.class))).thenReturn(labTechnician);
        when(labTechnicianRepository.save(any(LabTechnician.class))).thenReturn(labTechnician);

        DataResult<LabTechnician> result = labTechnicianService.addLabTechnician(labTechnicianForAddRequest);

        assertTrue(result.isSuccess());
        assertEquals(labTechnician, result.getData());
    }

    @Test
    void testGetById() {
        when(labTechnicianRepository.findById(1L)).thenReturn(Optional.of(labTechnician));

        DataResult<LabTechnician> result = labTechnicianService.getById(1L);

        assertTrue(result.isSuccess());
        assertEquals(labTechnician, result.getData());
    }

    @Test
    void testUpdateLabTechnician() {
        when(labTechnicianRepository.findById(1L)).thenReturn(Optional.of(labTechnician));
        when(labTechnicianRepository.save(any(LabTechnician.class))).thenReturn(labTechnician);

        DataResult<LabTechnician> result = labTechnicianService.updateLabTechnician(labTechnicianForUpdateRequest);

        assertTrue(result.isSuccess());
        assertEquals(labTechnician, result.getData());
    }

    @Test
    void testDeleteLabTechnician() {
        when(labTechnicianRepository.existsById(1L)).thenReturn(true);

        Result result = labTechnicianService.deleteLabTechnician(labTechnicianForDeleteRequest);

        assertTrue(result.isSuccess());
    }

}
