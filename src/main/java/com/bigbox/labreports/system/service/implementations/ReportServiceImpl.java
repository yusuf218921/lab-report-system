package com.bigbox.labreports.system.service.implementations;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.core.results.SuccessDataResult;
import com.bigbox.labreports.system.core.results.SuccessResult;
import com.bigbox.labreports.system.entity.dtos.report.ReportForAddRequest;
import com.bigbox.labreports.system.entity.dtos.report.ReportForDeleteRequest;
import com.bigbox.labreports.system.entity.dtos.report.ReportForListRequest;
import com.bigbox.labreports.system.entity.dtos.report.ReportForUpdateRequest;
import com.bigbox.labreports.system.entity.entities.Report;
import com.bigbox.labreports.system.repository.ReportRepository;
import com.bigbox.labreports.system.service.contracts.LabTechnicianService;
import com.bigbox.labreports.system.service.contracts.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public DataResult<Page<Report>> getAllPage(ReportForListRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize());
            return new SuccessDataResult<>(reportRepository.findReportsByPatientAndTechnicianWithSort(request, pageable));
    }

    @Override
    public DataResult<Report> addReport(ReportForAddRequest request) throws IOException, ParseException {
        Report report = modelMapper.map(request, Report.class);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = formatter.parse(request.getReportDate());
        report.setReportDate(new java.sql.Timestamp(date.getTime()));

        MultipartFile reportImage = request.getReportImage();
        if (reportImage != null && !reportImage.isEmpty()) {
            report.setReportImage(reportImage.getBytes());
        }

        report.setLabTechnician(labTechnicianService.getById(request.getLabTechnicianId()).getData());

        return new SuccessDataResult<>(reportRepository.save(report));

    }

    @Override
    public Result deleteReport(ReportForDeleteRequest request) {
        reportRepository.deleteById(request.getReportId());
        return new SuccessResult("Report deleted successfully");
    }

    @Override
    public DataResult<Report> updateReport(ReportForUpdateRequest request) throws IOException, ParseException {
        Report report = modelMapper.map(request, Report.class);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = formatter.parse(request.getReportDate());
        report.setReportDate(new java.sql.Timestamp(date.getTime()));

        MultipartFile reportImage = request.getReportImage();
        if (reportImage != null && !reportImage.isEmpty()) {
            report.setReportImage(reportImage.getBytes());
        }

        report.setLabTechnician(labTechnicianService.getById(request.getLabTechnicianId()).getData());

        return new SuccessDataResult<>(reportRepository.save(report));
    }

    // TODO: rapor ekleme silme ve güncelleme metodları için rol bazlı sistem eklenecek
}
