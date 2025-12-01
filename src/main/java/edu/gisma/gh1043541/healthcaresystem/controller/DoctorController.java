package edu.gisma.gh1043541.healthcaresystem.controller;

import edu.gisma.gh1043541.healthcaresystem.entity.Doctor;
import edu.gisma.gh1043541.healthcaresystem.service.DoctorServiceI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorServiceI doctorService;

    public DoctorController(DoctorServiceI doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorService.findAll());
        return "doctors/index";
    }

    @GetMapping("/new")
    public String createDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctors/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute  Doctor doctor) {
        doctorService.save(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", doctorService.findById(id));
        return "doctors/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        doctorService.delete(id);
        return "redirect:/doctors";
    }
}

