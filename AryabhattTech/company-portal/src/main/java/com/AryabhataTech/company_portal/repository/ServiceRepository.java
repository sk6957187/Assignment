package com.AryabhataTech.company_portal.repository;


import com.AryabhataTech.company_portal.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Integer> {

    @Query("SELECT s FROM ServiceEntity s WHERE s.isDeleted = false OR s.isDeleted IS NULL" )
    List<ServiceEntity> findAllService();
}
