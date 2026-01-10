package com.nayak.authorization.repository;

import com.nayak.authorization.entity.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrincipalRepository extends JpaRepository<Principal, Long> {

    // Optional custom queries
    boolean existsByEmail(String email);
}
