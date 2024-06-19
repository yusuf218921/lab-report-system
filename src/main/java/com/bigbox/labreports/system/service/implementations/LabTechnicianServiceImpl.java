package com.bigbox.labreports.system.service.implementations;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.ErrorResult;
import com.bigbox.labreports.system.core.results.ErrorDataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.core.results.SuccessDataResult;
import com.bigbox.labreports.system.core.results.SuccessResult;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForAddRequest;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForDeleteRequest;
import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForUpdateRequest;
import com.bigbox.labreports.system.entity.dtos.report.LabTechnicianForGetResponse;
import com.bigbox.labreports.system.entity.dtos.report.ReportForListResponse;
import com.bigbox.labreports.system.entity.entities.LabTechnician;
import com.bigbox.labreports.system.repository.LabTechnicianRepository;
import com.bigbox.labreports.system.service.contracts.LabTechnicianService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabTechnicianServiceImpl implements LabTechnicianService {

    private final LabTechnicianRepository labTechnicianRepository;
    private final ModelMapper modelMapper;
    private final ReportHelper reportHelper;

    @Autowired
    public LabTechnicianServiceImpl(LabTechnicianRepository labTechnicianRepository,
                                    ModelMapper modelMapper,
                                    ReportHelper reportHelper) {
        this.labTechnicianRepository = labTechnicianRepository;
        this.modelMapper = modelMapper;
        this.reportHelper = reportHelper;
    }

    @Override
    public DataResult<LabTechnician> getById(long id) {
        Optional<LabTechnician> result = labTechnicianRepository.findById(id);
        if (result.isPresent())
            return new SuccessDataResult<>(result.get());
        else
            return new ErrorDataResult<>("Lab technician not found");
    }

    @Override
    public Result addLabTechnician(LabTechnicianForAddRequest request) {
        LabTechnician labTechnician = modelMapper.map(request, LabTechnician.class);
        labTechnicianRepository.save(labTechnician);
        return new SuccessResult("Lab technician added successfully");
    }

    @Override
    public Result updateLabTechnician(LabTechnicianForUpdateRequest request) {
        Optional<LabTechnician> optionalLabTechnician = labTechnicianRepository.findById(request.getLabTechnicianId());
        if (optionalLabTechnician.isPresent()) {
            LabTechnician labTechnician = optionalLabTechnician.get();
            modelMapper.map(request, labTechnician);
            labTechnicianRepository.save(labTechnician);
            return new SuccessResult("Lab technician updated successfully");
        } else {
            return new ErrorResult("Lab technician not found");
        }
    }

    @Override
    public Result deleteLabTechnician(LabTechnicianForDeleteRequest request) {
        if (labTechnicianRepository.existsById(request.getLabTechnicianId())) {
            labTechnicianRepository.deleteById(request.getLabTechnicianId());
            return new SuccessResult("Lab technician deleted successfully");
        } else {
            return new ErrorResult("Lab technician not found");
        }
    }

    @Override
    public DataResult<LabTechnicianForGetResponse> getByIdReturnResponseDto(Long id) {
        Optional<LabTechnician> labTechnicianOptional = labTechnicianRepository.findById(id);
        if (labTechnicianOptional.isEmpty()) {
            return new ErrorDataResult<>("Lab technician not found");
        }
        LabTechnician labTechnician = labTechnicianOptional.get();
        LabTechnicianForGetResponse response = modelMapper.map(labTechnician, LabTechnicianForGetResponse.class);

        List<ReportForListResponse> reports = reportHelper.getReportsByLabTechnicianId(labTechnician.getLabTechnicianId(), modelMapper);
        response.setReports(reports);

        return new SuccessDataResult<>(response, "Lab technician retrieved successfully");
    }
}

