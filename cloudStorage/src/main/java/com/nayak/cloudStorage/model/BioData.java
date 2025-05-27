package com.nayak.cloudStorage.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Entity
public class BioData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int age;
    private LocalDate dob;
    private String address;
    private String image;
    private String video;
    private String audio;
    
    @Lob
    @Column(columnDefinition = "CLOB")
    private String textFile;

    private LocalDateTime createdTime;
    private LocalDateTime updateTime;


}
