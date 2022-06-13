package com.springrest.course.polyclinic.services;

import com.springrest.course.polyclinic.dto.EventType;
import com.springrest.course.polyclinic.dto.ObjectType;
import com.springrest.course.polyclinic.entities.Patients;
import com.springrest.course.polyclinic.exceptions.*;
import com.springrest.course.polyclinic.repo.PatientsRepo;
import com.springrest.course.polyclinic.util.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.BiConsumer;

@Service
public class PatientsService {

    @Autowired
    private PatientsRepo patientsRepo;

    @Autowired
    private DepartmentsService departmentsService;

    private final BiConsumer<EventType, Patients> wsSender;

    @Autowired
    public PatientsService(WsSender wsSender) {
        this.wsSender = wsSender.getSender(ObjectType.PATIENT);
    }

    public Patients getOne(Long id) throws PatientNotFoundException {
        Patients patient = patientsRepo.findById(id).get();
        if (patient == null) {
            throw new PatientNotFoundException("Пациент не найден!");
        }
        return patient;
    }

    public Patients create(Patients patient) throws DepartmentNotFoundException, PationsLimitException, NotParamException {
        if (patient.getDepartment() == null || patient.getLastname().equals("") || patient.getName().equals("")) {
            throw new NotParamException("Заполните обязательные поля!");
        }

        patient.setCreationDate(LocalDateTime.now());
        Patients updatedPatient = patientsRepo.save(patient);

        if (patient.getDepartment().getCountOfPatients() < patient.getDepartment().getLimitOfPatients()) {
            departmentsService.changeCountOfPatients("+", patient.getDepartment());
        } else {
            throw new PationsLimitException("Отделение заполнено!");
        }

        wsSender.accept(EventType.CREATE, updatedPatient);

        return updatedPatient;
    }

    public Patients update(Patients patientFromDb, Patients patient) throws PationsLimitException, EmptyPatientsException, NotParamException {
        boolean updateDepartment = false;

        if (patient.getDepartment() == null || patient.getLastname().equals("") || patient.getName().equals("")) {
            throw new NotParamException("Заполните обязательные поля!");
        }

        if (patientFromDb.getDepartment() == patient.getDepartment()) {
            updateDepartment = false;
        } else {
            updateDepartment = true;
        }

        if (patientFromDb.getDepartment() != null) {
            if (patientFromDb.getDepartment().getCountOfPatients() > 0 && updateDepartment) {
                departmentsService.changeCountOfPatients("-", patientFromDb.getDepartment());
            } else {
                throw new EmptyPatientsException("Отделение пусто!");
            }

            if (patient.getDepartment().getCountOfPatients() < patient.getDepartment().getLimitOfPatients() && updateDepartment) {
                departmentsService.changeCountOfPatients("+", patient.getDepartment());
            } else {
                throw new PationsLimitException("Отделение заполнено!");
            }
        }

        BeanUtils.copyProperties(patient, patientFromDb, "id");

        Patients updatedPatient = patientsRepo.save(patientFromDb);

        wsSender.accept(EventType.UPDATE, updatedPatient);

        return updatedPatient;
    }

    public Long delete(Patients patient) throws EmptyPatientsException {
        patientsRepo.delete(patient);

        if (patient.getDepartment() != null && patient.getDepartment().getCountOfPatients() > 0) {
            departmentsService.changeCountOfPatients("-", patient.getDepartment());
        } else {
            throw new EmptyPatientsException("Отделение заполнено!");
        }

        wsSender.accept(EventType.REMOVE, patient);
        return patient.getId();
    }
}
