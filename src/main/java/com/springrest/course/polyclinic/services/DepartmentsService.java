package com.springrest.course.polyclinic.services;

import com.springrest.course.polyclinic.entities.Departments;
import com.springrest.course.polyclinic.entities.Patients;
import com.springrest.course.polyclinic.exceptions.DepartmentNotFoundException;
import com.springrest.course.polyclinic.exceptions.MoreThanOneTreeTopException;
import com.springrest.course.polyclinic.exceptions.NotParamException;
import com.springrest.course.polyclinic.exceptions.TreeHaveChildrenException;
import com.springrest.course.polyclinic.repo.DepartmentsRepo;
import com.springrest.course.polyclinic.repo.PatientsRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepartmentsService {

    @Autowired
    private DepartmentsRepo departmentsRepo;

    @Autowired
    private PatientsRepo patientsRepo;

    public Departments getOne(Long id) throws DepartmentNotFoundException {
        Departments department = departmentsRepo.findById(id).get();
        if (department == null) {
            throw new DepartmentNotFoundException("Отделение не найден!");
        }
        return department;
    }

    public Departments create(Departments department) throws MoreThanOneTreeTopException, DepartmentNotFoundException, NotParamException {
        List<Departments> allDepartments = departmentsRepo.findAll();

        if (department.getName().equals("") || department.getAddress().equals("") || department.getLimitOfPatients() == 0) {
            throw new NotParamException("Заполните обязательные поля!");
        }

        if (allDepartments.size() > 0 && department.getParentId() == 0) {
            throw new MoreThanOneTreeTopException("Больше одного главного отделения быть не может!");
        }

        department.setCreationDate(LocalDateTime.now());

        return departmentsRepo.save(department);
    }

    public Departments update(Departments departmentFromDb, Departments department) throws NotParamException {
        if (department.getName().equals("") || department.getAddress().equals("") || department.getLimitOfPatients() == 0) {
            throw new NotParamException("Заполните обязательные поля!");
        }

        BeanUtils.copyProperties(department, departmentFromDb, "id");

        return departmentsRepo.save(departmentFromDb);
    }

    public Long delete(Departments department) throws TreeHaveChildrenException {
        List<Patients> patients = patientsRepo.findAll();
        List<Departments> departments = departmentsRepo.findAll();

        if (departments.size() > 0) {
            for (Departments child : departments) {
                if (child.getParentId() == department.getId()) {
                    throw new TreeHaveChildrenException("Невозможно удалить родителя, который имеет дочерние элементы!");
                }
            }
        }


        if (patients.size() > 0) {
            for (Patients patient : patients) {
                if (patient.getDepartment() != null && patient.getDepartment().getId() == department.getId()) {
                    patient.setDepartment(null);
                }
            }
        }

        departmentsRepo.delete(department);
        return department.getId();
    }

    public void changeCountOfPatients(String method, Departments department) {
        switch (method) {
            case "+": {
                department.setCountOfPatients(department.getCountOfPatients() + 1);
                departmentsRepo.save(department);
                break;
            }
            case "-": {
                department.setCountOfPatients(department.getCountOfPatients() - 1);
                departmentsRepo.save(department);
                break;
            }
            default: {
                System.out.println("Ошибка!");
                break;
            }
        }
    }
}
