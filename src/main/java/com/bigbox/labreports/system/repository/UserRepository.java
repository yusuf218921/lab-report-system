package com.bigbox.labreports.system.repository;

import com.bigbox.labreports.system.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT oc.operationClaimName FROM UserOperationClaim uoc JOIN uoc.operationClaim oc WHERE uoc.user.userId = :userId")
    List<String> findRolesByUserId(@Param("userId") Long userId);
}
