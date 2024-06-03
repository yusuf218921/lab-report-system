package com.bigbox.labreports.system.service.implementations;

import com.bigbox.labreports.system.repository.ReportRepository;
import com.bigbox.labreports.system.service.contracts.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
}
