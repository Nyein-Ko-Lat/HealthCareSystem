package edu.gisma.gh1043541.healthcaresystem.controller;

import edu.gisma.gh1043541.healthcaresystem.entity.Appointment;
import edu.gisma.gh1043541.healthcaresystem.entity.PatientVisit;
import edu.gisma.gh1043541.healthcaresystem.entity.Doctor;
import edu.gisma.gh1043541.healthcaresystem.service.*;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/patientvisits")
public class PatientVisitsController {

    private final PatientVisitService service;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final UserService userService;

    public PatientVisitsController(PatientVisitService service,
                                   PatientService patientService,
                                   DoctorService doctorService,
                                   AppointmentService appointmentService,
                                   UserService userService) {
        this.service = service;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {
        List<Appointment> bookings = appointmentService.findApprovedAppointmentsByDate(LocalDate.now());
        model.addAttribute("bookings", bookings);
        return "patientvisits/index";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Long id, Model model) {
        model.addAttribute("drSpecialist", StaticDataService.getDrSpecialist());
        Appointment a = id != null ? appointmentService.findById(id) : new Appointment();

        PatientVisit v = new PatientVisit();
        v.setAppointment(a);
        v.setPatient(a.getPatient());
        v.setDoctor(a.getDoctor());
        v.setVisitReason(a.getReason());
        v.setPatientVisitDate(LocalDateTime.now());
        model.addAttribute("patientvisit", v);
        model.addAttribute("patients", patientService.findAll());
        model.addAttribute("doctors", doctorService.findAll());
        return "patientvisits/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute PatientVisit patientvisits, Model model) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        Long userId = userService.findByUsername(username).getId();
        patientvisits.setUpdatedBy(userId);
        patientvisits.setCreatedBy(userId);

        service.save(patientvisits);
        return "redirect:/patientvisits";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        service.delete(id);
        return "redirect:/patientvisits";
    }

    // ---------- Load doctors by speciality (AJAX) ----------
    @GetMapping("/get_doctors_by_speciality/{speciality}")
    @ResponseBody
    public List<Doctor> loadDoctors(@PathVariable String speciality) {
        return doctorService.findBySpecialityId(speciality);
    }
}
