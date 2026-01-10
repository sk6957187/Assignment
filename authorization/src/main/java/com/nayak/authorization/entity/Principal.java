package com.nayak.authorization.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long principalId;

    private String name;
    private String email;

    @OneToMany(mappedBy = "principal", cascade = CascadeType.ALL)
    private List<HOD> hods;
    @Enumerated(EnumType.STRING)
    private Role role;
}
