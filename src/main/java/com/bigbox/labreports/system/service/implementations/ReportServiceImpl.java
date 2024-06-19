package com.bigbox.labreports.system.service.implementations;

import com.bigbox.labreports.system.core.results.*;
import com.bigbox.labreports.system.entity.dtos.report.*;
import com.bigbox.labreports.system.entity.entities.Report;
import com.bigbox.labreports.system.repository.ReportRepository;
import com.bigbox.labreports.system.service.contracts.LabTechnicianService;
import com.bigbox.labreports.system.service.contracts.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ModelMapper modelMapper;
    private final LabTechnicianService labTechnicianService;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository,
                             ModelMapper modelMapper,
                             LabTechnicianService labTechnicianService) {
        this.reportRepository = reportRepository;
        this.labTechnicianService = labTechnicianService;
        this.modelMapper = modelMapper;
    }

    @Override
    public DataResult<Page<ReportForListResponse>> getAllReportsWithPage(ReportForListRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize());
        Page<Report> reportsPage = reportRepository.findReportsByPatientAndTechnicianWithSort(request, pageable);

        List<ReportForListResponse> responseList = reportsPage.getContent().stream()
                .map(report -> {
                    ReportForListResponse response = modelMapper.map(report, ReportForListResponse.class);
                    response.setLabTechnicianResponse(new ReportForListResponse.LabTechnicianResponse(
                            report.getLabTechnician().getLabTechnicianId(),
                            report.getLabTechnician().getFirstName(),
                            report.getLabTechnician().getLastName(),
                            report.getLabTechnician().getHospitalIdNumber()
                    ));
                    return response;
                })
                .collect(Collectors.toList());

        Page<ReportForListResponse> responsePage = new PageImpl<>(responseList, pageable, reportsPage.getTotalElements());

        return new SuccessDataResult<>(responsePage);
    }

    @Override
    public Result addReport(ReportForAddRequest request) throws IOException, ParseException {
        if (!labTechnicianService.getById(request.getLabTechnicianId()).isSuccess())
            return new ErrorResult("Lab technician not found");

        Report report = modelMapper.map(request, Report.class);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = formatter.parse(request.getReportDate());
        report.setReportDate(new java.sql.Timestamp(date.getTime()));

        MultipartFile reportImage = request.getReportImage();
        if (reportImage != null && !reportImage.isEmpty()) {
            report.setReportImage(reportImage.getBytes());
        }

        report.setLabTechnician(labTechnicianService.getById(request.getLabTechnicianId()).getData());
        reportRepository.save(report);
        return new SuccessResult("report is added");
    }

    @Override
    public Result deleteReport(ReportForDeleteRequest request) {
        if (reportRepository.findById(request.getReportId()).isEmpty())
            return new ErrorDataResult<>("Report not found");
        reportRepository.deleteById(request.getReportId());
        return new SuccessResult("Report deleted successfully");
    }

    @Override
    public Result updateReport(ReportForUpdateRequest request) throws IOException, ParseException {
        if (reportRepository.findById(request.getReportId()).isEmpty())
            return new ErrorResult("Report not found");
        if (!labTechnicianService.getById(request.getLabTechnicianId()).isSuccess())
            return new ErrorResult("Lab technician not found");

        Report report = modelMapper.map(request, Report.class);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = formatter.parse(request.getReportDate());
        report.setReportDate(new java.sql.Timestamp(date.getTime()));

        MultipartFile reportImage = request.getReportImage();
        if (reportImage != null && !reportImage.isEmpty()) {
            report.setReportImage(reportImage.getBytes());
        }

        report.setLabTechnician(labTechnicianService.getById(request.getLabTechnicianId()).getData());
        reportRepository.save(report);
        return new SuccessResult("report is updated");
    }

    @Override
    public DataResult<ReportForGetResponse> getReportById(Long reportId) {
        Optional<Report> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isEmpty()) {
            return new ErrorDataResult<>("Report not found");
        }

        Report report = reportOptional.get();
        ReportForGetResponse response = modelMapper.map(report, ReportForGetResponse.class);

        if (report.getReportImage() != null) {
            response.setReportImage(Base64.getEncoder().encodeToString(report.getReportImage()));
        } else {
            response.setReportImage(null);
        }

        ReportForGetResponse.LabTechnicianResponse labTechnicianResponse = modelMapper.map(report.getLabTechnician(), ReportForGetResponse.LabTechnicianResponse.class);
        response.setLabTechnician(labTechnicianResponse);

        return new SuccessDataResult<>(response);
    }

}

