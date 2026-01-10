package com.nayak.authorization.util;

import com.nayak.authorization.dto.HODDTO;
import com.nayak.authorization.entity.HOD;
import com.nayak.authorization.entity.Teacher;

import java.util.stream.Collectors;

public class HODMapper {

    public static HODDTO toDTO(HOD hod) {
        if (hod == null) return null;

        HODDTO dto = new HODDTO();
        dto.setHodId(hod.getHodId());
        dto.setName(hod.getName());
        dto.setDepartment(hod.getDepartment());

        if (hod.getPrincipal() != null) {
            dto.setPrincipalId(hod.getPrincipal().getPrincipalId());
        }

        if (hod.getTeachers() != null) {
            dto.setTeacherIds(
                    hod.getTeachers()
                            .stream()
                            .map(Teacher::getTeacherId)
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }

    public static HOD toEntity(HODDTO dto) {
        if (dto == null) return null;

        HOD hod = new HOD();
        hod.setHodId(dto.getHodId());
        hod.setName(dto.getName());
        hod.setDepartment(dto.getDepartment());

        return hod;
    }
}
