package com.ASTech.ASTech.controller;

import com.ASTech.ASTech.dto.ServiceDto;
import com.ASTech.ASTech.model.ResponseStructure;
import com.ASTech.ASTech.service.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/astech")
public class ServiceController {

    private static final Logger logger =
            LoggerFactory.getLogger(ServiceController.class);

    private final Services services;

    public ServiceController(Services services) {
        this.services = services;
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<ServiceDto>>> getAllServices() {
        logger.info("Fetching all services");
        return services.getAllServices();
    }
}
