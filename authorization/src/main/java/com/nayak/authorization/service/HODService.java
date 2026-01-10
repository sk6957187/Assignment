package com.nayak.authorization.service;

import com.nayak.authorization.dto.HODDTO;
import com.nayak.authorization.entity.HOD;
import com.nayak.authorization.entity.Principal;
import com.nayak.authorization.repository.HODRepository;
import com.nayak.authorization.repository.PrincipalRepository;
import com.nayak.authorization.util.HODMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HODService {

    private final HODRepository hodRepository;
    private final PrincipalRepository principalRepository;

    public HODDTO createHOD(HODDTO dto) {
        HOD hod = HODMapper.toEntity(dto);

        Principal principal = principalRepository.findById(dto.getPrincipalId())
                .orElseThrow(() -> new RuntimeException("Principal not found"));
        hod.setPrincipal(principal);

        return HODMapper.toDTO(hodRepository.save(hod));
    }
}
