package edu.gisma.gh1043541.healthcaresystem.controller;

import edu.gisma.gh1043541.healthcaresystem.entity.Appointment;
import edu.gisma.gh1043541.healthcaresystem.entity.Doctor;
import edu.gisma.gh1043541.healthcaresystem.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final UserService userService;

    public AppointmentController(AppointmentService service,
                                 PatientService patientService,
                                 DoctorService doctorService,
                                 UserService userService) {
        this.service = service;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("appointments", service.findAll());
        return "appointment/index";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Long id, Model model) {
        model.addAttribute("drSpecialist", StaticDataService.getDrSpecialist());
        Appointment a = id != null ? service.findById(id) : new Appointment();
        model.addAttribute("appointment", a);
        model.addAttribute("patients", patientService.findAll());
        model.addAttribute("doctors", doctorService.findAll());
        return "appointment/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Appointment appointment, Model model) {
        boolean availableSlot = service.findAvailableSlot(appointment.getDoctor().getDoctorID(),appointment.getAppointmentDate());
        //Check whether available time slot and book before 1 hours
        if(!availableSlot || appointment.getAppointmentDate().isBefore(LocalDateTime.now().plusHours(1L))) {
            model.addAttribute("error", "Selected time slot is not available!");
            model.addAttribute("appointment", appointment);
            model.addAttribute("patients", patientService.findAll());
            model.addAttribute("doctors", doctorService.findAll());
            return "appointment/form"; // Back to form
        }
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        Long userId = userService.findByUsername(username).getId();
        appointment.setUpdatedBy(userId);
        appointment.setCreatedBy(userId);

        service.save(appointment);
        return "redirect:/appointments";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        service.delete(id);
        return "redirect:/appointments";
    }

    // ---------- Load doctors by speciality (AJAX) ----------
    @GetMapping("/get_doctors_by_speciality/{speciality}")
    @ResponseBody
    public List<Doctor> loadDoctors(@PathVariable String speciality) {
        return doctorService.findBySpecialityId(speciality);
    }
}
