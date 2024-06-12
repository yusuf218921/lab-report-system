package com.bigbox.labreports.system.core.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operation_claims")
public class OperationClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_claim_id")
    private Long operationClaimId;

    @Column(name = "operation_claim_name")
    private String operationClaimName;

    @ManyToMany(mappedBy = "operationClaims")
    private Set<User> users;
}
