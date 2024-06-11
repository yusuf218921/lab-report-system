package com.bigbox.labreports.system.controller;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForAddRequest;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForDeleteRequest;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForUpdateRequest;
import com.bigbox.labreports.system.entity.entities.LabTechnician;
import com.bigbox.labreports.system.service.contracts.LabTechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/labTechnician")
public class LabTechnicianController {

    private final LabTechnicianService labTechnicianService;

    @Autowired
    public LabTechnicianController(LabTechnicianService labTechnicianService) {
        this.labTechnicianService = labTechnicianService;
    }

    @GetMapping("/{id}")
    public DataResult<LabTechnician> getById(@PathVariable long id) {
        return labTechnicianService.getById(id);
    }

    @PostMapping
    public DataResult<LabTechnician> addLabTechnician(@RequestBody LabTechnicianForAddRequest request) {
        return labTechnicianService.addLabTechnician(request);
    }

    @PutMapping
    public DataResult<LabTechnician> updateLabTechnician(@RequestBody LabTechnicianForUpdateRequest request) {
        return labTechnicianService.updateLabTechnician(request);
    }

    @DeleteMapping
    public Result deleteLabTechnician(@RequestBody LabTechnicianForDeleteRequest request) {
        return labTechnicianService.deleteLabTechnician(request);
    }
}
