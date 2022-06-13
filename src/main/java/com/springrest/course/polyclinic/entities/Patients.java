package com.springrest.course.polyclinic.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Patients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname, patronymic;
    private Date dateOfBirth;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departmentId")
    private Departments department;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
}