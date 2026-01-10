package com.nayak.authorization.controller;

import com.nayak.authorization.dto.PrincipalDTO;
import com.nayak.authorization.service.PrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/principals")
@RequiredArgsConstructor
public class PrincipalController {

    private final PrincipalService principalService;

    @PostMapping
    public PrincipalDTO create(@RequestBody PrincipalDTO dto) {
        return principalService.createPrincipal(dto);
    }

    @GetMapping
    public List<PrincipalDTO> getAll() {
        return principalService.getAllPrincipals();
    }
}
