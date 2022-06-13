package com.springrest.course.polyclinic.controller;

import com.springrest.course.polyclinic.entities.User;
import com.springrest.course.polyclinic.repo.DepartmentsRepo;
import com.springrest.course.polyclinic.repo.PatientsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    private final PatientsRepo patientsRepo;
    private final DepartmentsRepo departmentsRepo;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public MainController(PatientsRepo patientsRepo, DepartmentsRepo departmentsRepo) {
        this.patientsRepo = patientsRepo;
        this.departmentsRepo = departmentsRepo;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();

        if (user != null) {
            data.put("profile", user);
            data.put("patients", patientsRepo.findAll());
            data.put("departments", departmentsRepo.findAll());
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
