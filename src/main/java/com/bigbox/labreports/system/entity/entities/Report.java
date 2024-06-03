package com.bigbox.labreports.system.entity.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "file_number")
    private String fileNumber;

    @Column(name = "patient_first_name")
    private String patientFirstName;

    @Column(name = "patient_last_name")
    private String patientLastName;

    @Column(name = "patient_id_number")
    private String patientIdNumber;

    @Column(name = "diagnosis_title")
    private String diagnosisTitle;

    @Column(name = "diagnosis_details")
    private String diagnosisDetails;

    @Column(name = "report_date")
    private Date reportDate;

    @Lob
    @Column(name = "report_image")
    private byte[] reportImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lan_technician_id", nullable = false)
    private LabTechnician labTechnician;



}
