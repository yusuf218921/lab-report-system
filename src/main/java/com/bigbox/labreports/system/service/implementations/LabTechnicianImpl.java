package com.bigbox.labreports.system.service.implementations;

import com.bigbox.labreports.system.repository.LabTechnicianRepository;
import com.bigbox.labreports.system.service.contracts.LabTechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabTechnicianImpl implements LabTechnicianService {
    private final LabTechnicianRepository labTechnicianRepository;

    @Autowired
    public LabTechnicianImpl(LabTechnicianRepository labTechnicianRepository) {
        this.labTechnicianRepository = labTechnicianRepository;
    }
}
