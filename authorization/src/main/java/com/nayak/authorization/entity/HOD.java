package com.nayak.authorization.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HOD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hodId;

    private String name;
    private String department;

    @ManyToOne
    @JoinColumn(name = "principal_id")
    private Principal principal;

    @OneToMany(mappedBy = "hod", cascade = CascadeType.ALL)
    private List<Teacher> teachers;

    @Enumerated(EnumType.STRING)
    private Role role;
}
