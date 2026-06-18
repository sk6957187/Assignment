package com.AryabhataTech.company_portal.service;

import com.AryabhataTech.company_portal.dao.ServiceDTO;
import com.AryabhataTech.company_portal.exception.DataNotFound;
import com.AryabhataTech.company_portal.model.ResponseStructure;
import com.AryabhataTech.company_portal.model.ServiceEntity;
import com.AryabhataTech.company_portal.repository.ServiceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Services {

    private final ServiceRepository serviceRepository;

    public Services(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public ResponseEntity<ResponseStructure<List<ServiceDTO>>> getAllServices() {

        List<ServiceEntity> entities = serviceRepository.findAllService();

        if(entities.isEmpty()){
            throw new DataNotFound("No Services found!!");
        }

        List<ServiceDTO> updatedEntities = entities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        ResponseStructure<List<ServiceDTO>> res = new ResponseStructure<>();
        res.setStatus(HttpStatus.OK.value());
        res.setMessage(updatedEntities.isEmpty()
                ? "Services are not available" : "Services fetched successfully");
        res.setData(updatedEntities);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private ServiceDTO mapToDto(ServiceEntity entity) {

        ServiceDTO dto = new ServiceDTO();

        dto.setId(entity.getId());
        dto.setHeadline(entity.getHeadline());
        dto.setCategory(entity.getCategory());
        dto.setDescription(entity.getDescription());
        dto.setLink(entity.getLink());

        return dto;
    }

    public ResponseEntity<ResponseStructure<ServiceDTO>> createService(ServiceEntity entity) {
        ServiceEntity saveEntity = serviceRepository.save(entity);

        ServiceDTO dtoEntity = mapToDto(entity);

        ResponseStructure<ServiceDTO> res = new ResponseStructure<>();
        res.setData(dtoEntity);
        res.setStatus(HttpStatus.CREATED.value());
        res.setMessage("Service is created!!");

        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}