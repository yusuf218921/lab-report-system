package com.bigbox.labreports.system.service.implementations;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.ErrorDataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.core.results.SuccessDataResult;
import com.bigbox.labreports.system.entity.entities.LabTechnician;
import com.bigbox.labreports.system.repository.LabTechnicianRepository;
import com.bigbox.labreports.system.service.contracts.LabTechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LabTechnicianImpl implements LabTechnicianService {
    private final LabTechnicianRepository labTechnicianRepository;

    @Autowired
    public LabTechnicianImpl(LabTechnicianRepository labTechnicianRepository) {
        this.labTechnicianRepository = labTechnicianRepository;
    }

    @Override
    public DataResult<LabTechnician> getById(long id) {
        Optional<LabTechnician> result = labTechnicianRepository.findById(id);
        if(result.isPresent())
            return new  SuccessDataResult<>(result.get());
        else
            return new ErrorDataResult<>("Lab technician not found");

    }

    @Override
    public DataResult<LabTechnician> addLabTechnician() {
        return null;
    }

    @Override
    public DataResult<LabTechnician> updateLabTechnician() {
        return null;
    }

    @Override
    public Result deleteLabTechnician() {
        return null;
    }
}
