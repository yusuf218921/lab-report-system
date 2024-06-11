package com.bigbox.labreports.system.service.config;

import com.bigbox.labreports.system.entity.dtos.labTechnician.LabTechnicianForAddRequest;
import com.bigbox.labreports.system.entity.dtos.report.ReportForAddRequest;
import com.bigbox.labreports.system.entity.entities.LabTechnician;
import com.bigbox.labreports.system.entity.entities.Report;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.PropertyMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Custom converter for date mapping
        AbstractConverter<String, Date> toDate = new AbstractConverter<String, Date>() {
            @Override
            protected Date convert(String source) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    return dateFormat.parse(source);
                } catch (ParseException e) {
                    return null; // handle exception as needed
                }
            }
        };

        //Map for matching LabTechnician and LabTechnicianForAddRequest
        modelMapper.addMappings(new PropertyMap<LabTechnicianForAddRequest, LabTechnician>() {
            @Override
            protected void configure() {
                map().setFirstName(source.getFirstName());
                map().setLastName(source.getLastName());
                map().setHospitalIdNumber(source.getHospitalIdNumber());
                skip(destination.getLabTechnicianId());
            }
        });

        //Map for matching Report and ReportForAddRequest
        modelMapper.addMappings(new PropertyMap<ReportForAddRequest, Report>() {
            @Override
            protected void configure() {
                map().setFileNumber(source.getFileNumber());
                map().setPatientFirstName(source.getPatientFirstName());
                map().setPatientLastName(source.getPatientLastName());
                map().setPatientIdNumber(source.getPatientIdNumber());
                map().setDiagnosisTitle(source.getDiagnosisTitle());
                map().setDiagnosisDetails(source.getDiagnosisDetails());
                using(toDate).map(source.getReportDate()).setReportDate(null);
                map().setLabTechnician(new LabTechnician(source.getLabTechnicianId()));
                skip(destination.getReportId());
            }
        });

        return modelMapper;
    }
}
