package com.bigbox.labreports.system.repository;

import com.bigbox.labreports.system.entity.entities.Report;
import com.bigbox.labreports.system.entity.dtos.report.ReportForListRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ReportRepositoryCustomImpl implements ReportRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Report> findReportsByPatientAndTechnicianWithSort(ReportForListRequest request, Pageable pageable) {
        StringBuilder jpql = new StringBuilder("SELECT r FROM Report r JOIN r.labTechnician lt WHERE 1=1");

        if (request.getPatientFirstName() != null) {
            jpql.append(" AND r.patientFirstName = :patientFirstName");
        }
        if (request.getPatientLastName() != null) {
            jpql.append(" AND r.patientLastName = :patientLastName");
        }
        if (request.getPatientIdNumber() != null) {
            jpql.append(" AND r.patientIdNumber = :patientIdNumber");
        }
        if (request.getLabTechnicianFirstName() != null) {
            jpql.append(" AND lt.firstName = :labTechnicianFirstName");
        }
        if (request.getLabTechnicianLastName() != null) {
            jpql.append(" AND lt.lastName = :labTechnicianLastName");
        }
        if (request.getSortDirection() != null && (request.getSortDirection().equalsIgnoreCase("ASC") || request.getSortDirection().equalsIgnoreCase("DESC"))) {
            jpql.append(" ORDER BY r.reportDate ").append(request.getSortDirection());
        }

        TypedQuery<Report> query = entityManager.createQuery(jpql.toString(), Report.class);

        if (request.getPatientFirstName() != null) {
            query.setParameter("patientFirstName", request.getPatientFirstName());
        }
        if (request.getPatientLastName() != null) {
            query.setParameter("patientLastName", request.getPatientLastName());
        }
        if (request.getPatientIdNumber() != null) {
            query.setParameter("patientIdNumber", request.getPatientIdNumber());
        }
        if (request.getLabTechnicianFirstName() != null) {
            query.setParameter("labTechnicianFirstName", request.getLabTechnicianFirstName());
        }
        if (request.getLabTechnicianLastName() != null) {
            query.setParameter("labTechnicianLastName", request.getLabTechnicianLastName());
        }

        int totalRows = query.getResultList().size();
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Report> reports = query.getResultList();

        return new PageImpl<>(reports, pageable, totalRows);
    }
}
