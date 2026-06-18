package com.AryabhataTech.company_portal.controller;

import com.AryabhataTech.company_portal.dao.ServiceDTO;
import com.AryabhataTech.company_portal.model.ResponseStructure;
import com.AryabhataTech.company_portal.model.ServiceEntity;
import com.AryabhataTech.company_portal.service.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceContr {

//    @Autowired
//    private Services services;

    private static final Logger logger = LoggerFactory.getLogger(ServiceContr.class);


    private final Services services;

    public ServiceContr(Services services){
        this.services = services;
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<ServiceDTO>>> getService(){
        logger.info("Fetching all services");
        return services.getAllServices();
    }

    @PostMapping
    public ResponseEntity<ResponseStructure<ServiceDTO>> createService(@RequestBody ServiceEntity entity){
        logger.info("Service created for: ", entity);
        return services.createService(entity);
    }

}
