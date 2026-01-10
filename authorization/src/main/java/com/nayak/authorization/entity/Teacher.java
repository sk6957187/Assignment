package com.nayak.authorization.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherId;

    private String name;
    private String subject;

    @ManyToOne
    @JoinColumn(name = "hod_id")
    private HOD hod;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Student> students;

    @Enumerated(EnumType.STRING)
    private Role role;
}
