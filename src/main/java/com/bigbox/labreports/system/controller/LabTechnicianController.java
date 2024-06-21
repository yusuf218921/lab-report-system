package com.bigbox.labreports.system.controller;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForAddRequest;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForDeleteRequest;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForUpdateRequest;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForGetResponse;
import com.bigbox.labreports.system.service.contracts.LabTechnicianService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/labTechnicians")
public class LabTechnicianController {

    private final LabTechnicianService labTechnicianService;

    @Autowired
    public LabTechnicianController(LabTechnicianService labTechnicianService) {
        this.labTechnicianService = labTechnicianService;
    }

    @GetMapping("/{id}")
    public DataResult<LabTechnicianForGetResponse> getById(@PathVariable long id) {
        return labTechnicianService.getByIdReturnResponseDto(id);
    }

    @PostMapping
    public ResponseEntity<Result> addLabTechnician(@Valid @RequestBody LabTechnicianForAddRequest request) {
        Result result = labTechnicianService.addLabTechnician(request);
        return new ResponseEntity<>(result, result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<Result> updateLabTechnician(@Valid @RequestBody LabTechnicianForUpdateRequest request) {
        Result result = labTechnicianService.updateLabTechnician(request);
        return new ResponseEntity<>(result, result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public Result deleteLabTechnician(@RequestBody LabTechnicianForDeleteRequest request) {
        return labTechnicianService.deleteLabTechnician(request);
    }
}
