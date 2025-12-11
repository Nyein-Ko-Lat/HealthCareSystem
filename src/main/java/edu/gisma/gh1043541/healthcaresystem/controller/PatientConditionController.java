package edu.gisma.gh1043541.healthcaresystem.controller;

import edu.gisma.gh1043541.healthcaresystem.entity.PatientVisit;
import edu.gisma.gh1043541.healthcaresystem.entity.Doctor;
import edu.gisma.gh1043541.healthcaresystem.entity.PatientCondition;
import edu.gisma.gh1043541.healthcaresystem.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/patientcondition")
public class PatientConditionController {

    private final PatientConditionService service;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final PatientVisitService patientVisitService;
    private final UserService userService;

    public PatientConditionController(PatientConditionService service,
                                      PatientService patientService,
                                      DoctorService doctorService,
                                      PatientVisitService patientVisitService,
                                      UserService userService) {
        this.service = service;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.patientVisitService = patientVisitService;
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {
        List<PatientVisit> pVisits = patientVisitService.findApprovedPatientVisitsByDate(LocalDate.now());
        model.addAttribute("pVisits", pVisits);
        return "patientcondition/index";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Long id, Model model) {
        model.addAttribute("drSpecialist", StaticDataService.getDrSpecialist());
        PatientVisit a = id != null ? patientVisitService.findById(id) : new PatientVisit();

        PatientCondition dig = new PatientCondition();
        dig.setPatientVisit(a);
        dig.setPatient(a.getPatient());
        dig.setDoctor(a.getDoctor());
        dig.setDiagnosedDate(LocalDateTime.now());
        model.addAttribute("patientcondition", dig);
        model.addAttribute("patients", patientService.findAll());
        model.addAttribute("doctors", doctorService.findAll());
        model.addAttribute("conditions", StaticDataService.getPatientCondition());
        return "patientcondition/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute PatientCondition patientcondition, Model model) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        Long userId = userService.findByUsername(username).getId();
        patientcondition.setUpdatedBy(userId);
        patientcondition.setCreatedBy(userId);

        service.save(patientcondition);
        return "redirect:/patientcondition";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        service.delete(id);
        return "redirect:/patientcondition";
    }

    // ---------- Load doctors by speciality (AJAX) ----------
    @GetMapping("/get_doctors_by_speciality/{speciality}")
    @ResponseBody
    public List<Doctor> loadDoctors(@PathVariable String speciality) {
        return doctorService.findBySpecialityId(speciality);
    }
}
