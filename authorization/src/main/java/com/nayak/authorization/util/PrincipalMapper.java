package com.nayak.authorization.util;

import com.nayak.authorization.dto.PrincipalDTO;
import com.nayak.authorization.entity.HOD;
import com.nayak.authorization.entity.Principal;

import java.util.stream.Collectors;

public class PrincipalMapper {

    public static PrincipalDTO toDTO(Principal principal) {
        if (principal == null) return null;

        PrincipalDTO dto = new PrincipalDTO();
        dto.setPrincipalId(principal.getPrincipalId());
        dto.setName(principal.getName());
        dto.setEmail(principal.getEmail());

        if (principal.getHods() != null) {
            dto.setHodIds(
                    principal.getHods()
                            .stream()
                            .map(HOD::getHodId)
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }

    public static Principal toEntity(PrincipalDTO dto) {
        if (dto == null) return null;

        Principal principal = new Principal();
        principal.setPrincipalId(dto.getPrincipalId());
        principal.setName(dto.getName());
        principal.setEmail(dto.getEmail());

        return principal;
    }
}
