package com.nayak.authorization.service;

import com.nayak.authorization.dto.PrincipalDTO;
import com.nayak.authorization.entity.Principal;
import com.nayak.authorization.repository.PrincipalRepository;
import com.nayak.authorization.util.PrincipalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrincipalService {

    private final PrincipalRepository principalRepository;

    public PrincipalDTO createPrincipal(PrincipalDTO dto) {
        Principal principal = PrincipalMapper.toEntity(dto);
        return PrincipalMapper.toDTO(principalRepository.save(principal));
    }

    public List<PrincipalDTO> getAllPrincipals() {
        return principalRepository.findAll()
                .stream()
                .map(PrincipalMapper::toDTO)
                .collect(Collectors.toList());
    }
}
