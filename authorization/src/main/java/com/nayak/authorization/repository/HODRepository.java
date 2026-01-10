package com.nayak.authorization.repository;

import com.nayak.authorization.entity.HOD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HODRepository extends JpaRepository<HOD, Long> {

    List<HOD> findByDepartment(String department);

    List<HOD> findByPrincipal_PrincipalId(Long principalId);
}
