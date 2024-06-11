package com.bigbox.labreports.system.entity.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lab_technicians")
public class LabTechnician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_technician_id")
    private Long labTechnicianId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "hospital_id_number")
    private String hospitalIdNumber;

    @OneToMany(mappedBy = "labTechnician", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;

    public LabTechnician(Long id){
        labTechnicianId = id;
    }
}
