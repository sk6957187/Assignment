package com.ASTech.ASTech.service;

import com.ASTech.ASTech.dto.ServiceDto;
import com.ASTech.ASTech.model.ResponseStructure;
import com.ASTech.ASTech.model.ServiceEntity;
import com.ASTech.ASTech.repositery.ServiceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Services {

    private final ServiceRepository repository;

    public Services(ServiceRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<ResponseStructure<List<ServiceDto>>> getAllServices() {

        List<ServiceEntity> entities = repository.findAll();

        List<ServiceDto> dtos = entities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        ResponseStructure<List<ServiceDto>> response =
                new ResponseStructure<>();

        response.setStatusCode(HttpStatus.OK.value());
        response.setData(dtos);

        response.setMsg(dtos.isEmpty()
                ? "Service not available"
                : "Services are available");

        return ResponseEntity.ok(response);
    }

    private ServiceDto mapToDto(ServiceEntity entity) {
        ServiceDto dto = new ServiceDto();
        dto.setId(entity.getId());
        dto.setSerName(entity.getSerName());
        dto.setDescription(entity.getDescription());
        dto.setLink(entity.getLink());
        return dto;
    }
}
