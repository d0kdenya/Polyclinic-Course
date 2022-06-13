package com.springrest.course.polyclinic.controller;

import com.springrest.course.polyclinic.entities.Departments;
import com.springrest.course.polyclinic.exceptions.DepartmentNotFoundException;
import com.springrest.course.polyclinic.exceptions.MoreThanOneTreeTopException;
import com.springrest.course.polyclinic.exceptions.NotParamException;
import com.springrest.course.polyclinic.exceptions.TreeHaveChildrenException;
import com.springrest.course.polyclinic.repo.DepartmentsRepo;
import com.springrest.course.polyclinic.services.DepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentsController {
    @Autowired
    private DepartmentsService departmentsService;

    @Autowired
    private DepartmentsRepo departmentsRepo;

    @GetMapping
    public List<Departments> list() {
        return departmentsRepo.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity getOneDepartment(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(departmentsService.getOne(id));
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Departments department) {
        try {
            return ResponseEntity.ok(departmentsService.create(department));
        } catch (MoreThanOneTreeTopException | DepartmentNotFoundException | NotParamException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity update(
            @PathVariable("id") Departments departmentFromDb,
            @RequestBody Departments department
    ) {
        try {
            return ResponseEntity.ok(departmentsService.update(departmentFromDb, department));
        } catch (NotParamException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Departments department) {
        try {
            return ResponseEntity.ok(departmentsService.delete(department));
        } catch (TreeHaveChildrenException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
