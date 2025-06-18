package com.nayak.cloudStorage.repositry;

import com.nayak.cloudStorage.model.BioData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudRepository extends JpaRepository<BioData, Integer> {
}
