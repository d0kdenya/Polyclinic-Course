package com.springrest.course.polyclinic.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
public class Departments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name, address;

    private int limitOfPatients, countOfPatients, parentId;

    @OneToMany
    private List<Patients> patients;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
}
