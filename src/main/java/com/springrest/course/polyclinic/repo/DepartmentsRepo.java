package com.springrest.course.polyclinic.repo;

import com.springrest.course.polyclinic.entities.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentsRepo extends JpaRepository<Departments, Long> {
}
