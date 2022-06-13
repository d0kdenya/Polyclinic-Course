package com.springrest.course.polyclinic.repo;

import com.springrest.course.polyclinic.entities.Departments;
import com.springrest.course.polyclinic.entities.Patients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientsRepo extends JpaRepository<Patients, Long> {
}
