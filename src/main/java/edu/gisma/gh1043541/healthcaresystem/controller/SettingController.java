package edu.gisma.gh1043541.healthcaresystem.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gisma.gh1043541.healthcaresystem.entity.Doctor;
import edu.gisma.gh1043541.healthcaresystem.entity.FamilyHistory;
import edu.gisma.gh1043541.healthcaresystem.entity.Patient;
import edu.gisma.gh1043541.healthcaresystem.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/settings")
public class SettingController {
    private final PatientService patientService;
    private final UserService userService;
    private final FamilyHistoryService familyHistoryService;
    private final DoctorService doctorService;

    public SettingController(DoctorService doctorService, PatientService patientService, FamilyHistoryService familyHistoryService, UserService userService) {
        this.patientService = patientService;
        this.userService = userService;
        this.familyHistoryService = familyHistoryService;
        this.doctorService = doctorService;
    }
    //Start Doctor CRUD for initial setup

    @GetMapping("/doctors")
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorService.findAll());
        return "doctors/index";
    }

    @GetMapping("/doctors/new")
    public String createDoctorForm(Model model) {
        model.addAttribute("drSpecialist", StaticDataService.getDrSpecialist());
        model.addAttribute("doctor", new Doctor());
        return "doctors/form";
    }

    @PostMapping("/doctors/save")
    public String saveDoctor(@ModelAttribute  Doctor doctor) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Long userId = userService.findByUsername(username).getId();
        doctor.setCreatedBy(userId);
        doctor.setUpdatedBy(userId);
        doctorService.save(doctor);
        return "redirect:/settings/doctors";
    }

    @GetMapping("/doctors/edit/{id}")
    public String editDoctorForm(@PathVariable Long id, Model model) {
        model.addAttribute("drSpecialist", StaticDataService.getDrSpecialist());
        model.addAttribute("doctor", doctorService.findById(id));
        return "doctors/form";
    }

    @GetMapping("/doctors/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Long userId = userService.findByUsername(username).getId();
        doctorService.delete(id, userId);
        return "redirect:/settings/doctors";
    }

    //End Doctor CRUD for initial setup

    ///Start Patient CRUD for initial setup

    @GetMapping("/patients")
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.findAll());
        return "patients/index";
    }

    @GetMapping("/patients/new")
    public String createPatientForm(Model model) {
        Patient patient = new Patient();
        patient.setDateOfBirth(LocalDate.now());
        patient.getFamilyHistory().add(new FamilyHistory()); // one empty row
        model.addAttribute("patient", patient);
        model.addAttribute("conditions", StaticDataService.getCommonMedicalConditions());
        model.addAttribute("relationships", StaticDataService.getRelationships());
        return "patients/form";
    }

    @PostMapping("/patients/save")
    public String savePatient(
            @ModelAttribute Patient patient,
            @RequestParam("familyHistories") String familyHistoryJson)
            throws Exception{
        /// Start of the controller for saving the Patient

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Long userId = userService.findByUsername(username).getId();

        // Parse JSON of family history
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> rawList = null;
        if(!familyHistoryJson.isEmpty()) {
            rawList = mapper.readValue(
                    familyHistoryJson,
                    new TypeReference<List<Map<String, Object>>>() {
                    }
            );
        }

        if(patient.getPatientID() == null){
            patient.setCreatedBy(userId);
        }
        patient.setUpdatedBy(userId);
        List<FamilyHistory> histories = new ArrayList<>();

        if(rawList != null){
            for (Map<String, Object> map : rawList) {
                FamilyHistory fh = new FamilyHistory();

                fh.setFamilyHistoryID((String)map.get("familyHistoryID") != ""? Long.parseLong((String) map.get("familyHistoryID")):null);
                fh.setRelationship((String) map.getOrDefault("relationship", ""));
                fh.setMedicalCondition((String) map.getOrDefault("medicalCondition", ""));
                fh.setPatient(patient);
                fh.setUpdatedBy(userId);
                if(fh.getFamilyHistoryID() == null){
                    fh.setCreatedBy(userId);
                }
                histories.add(fh);
            }
        }

        patient.setFamilyHistory(histories);
        // Save patient first
        patientService.save(patient);
        return "redirect:/settings/patients";
    }

    @GetMapping("/patients/edit/{id}")
    public String editPatientForm(@PathVariable Long id, Model model) {
        Patient patient = patientService.findById(id);
        patient.setDateOfBirth(LocalDate.now());

        List<FamilyHistory> histories = familyHistoryService.findByPatientId(id);
        model.addAttribute("familyHistories", histories); // <-- add this
        model.addAttribute("patient", patient);
        model.addAttribute("conditions", StaticDataService.getCommonMedicalConditions());
        model.addAttribute("relationships", StaticDataService.getRelationships());
        return "patients/form";
    }

    @GetMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Long userId = userService.findByUsername(username).getId();
        patientService.delete(id, userId);
        return "redirect:/settings/patients";
    }
    //End Patient CRUD for initial setup
}
