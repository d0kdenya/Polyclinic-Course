package com.springrest.course.polyclinic.controller;

import com.springrest.course.polyclinic.entities.Patients;
import com.springrest.course.polyclinic.exceptions.EmptyPatientsException;
import com.springrest.course.polyclinic.exceptions.NotParamException;
import com.springrest.course.polyclinic.exceptions.PatientNotFoundException;
import com.springrest.course.polyclinic.exceptions.PationsLimitException;
import com.springrest.course.polyclinic.repo.PatientsRepo;
import com.springrest.course.polyclinic.services.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("patients")
public class PatientsController {
    @Autowired
    private PatientsService patientsService;

    @Autowired
    private PatientsRepo patientsRepo;

    @GetMapping
    public List<Patients> list() {
        return patientsRepo.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity getOnePatient(@RequestParam int id) {
        try {
            return ResponseEntity.ok(patientsService.getOne((long) id));
        } catch (PatientNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Patients patient) {
        try {
            return ResponseEntity.ok(patientsService.create(patient));
        } catch (NotParamException | PationsLimitException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity update(
            @PathVariable("id") Patients patientFromDb,
            @RequestBody Patients patient
    ) {
        try {
            return ResponseEntity.ok(patientsService.update(patientFromDb, patient));
        } catch (EmptyPatientsException | PationsLimitException | NotParamException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Patients patient) {
        try {
            return ResponseEntity.ok(patientsService.delete(patient));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
