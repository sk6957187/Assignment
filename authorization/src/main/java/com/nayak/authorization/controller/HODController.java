package com.nayak.authorization.controller;

import com.nayak.authorization.dto.HODDTO;
import com.nayak.authorization.service.HODService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hods")
@RequiredArgsConstructor
public class HODController {

    private final HODService hodService;

    @PostMapping
    public HODDTO create(@RequestBody HODDTO dto) {
        return hodService.createHOD(dto);
    }
}

