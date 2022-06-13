package com.springrest.course.polyclinic.repo;

import com.springrest.course.polyclinic.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<User, String> {
}