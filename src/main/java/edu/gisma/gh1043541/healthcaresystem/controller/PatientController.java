package edu.gisma.gh1043541.healthcaresystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gisma.gh1043541.healthcaresystem.entity.FamilyHistory;
import edu.gisma.gh1043541.healthcaresystem.entity.Patient;
import edu.gisma.gh1043541.healthcaresystem.service.FamilyHistoryService;
import edu.gisma.gh1043541.healthcaresystem.service.PatientServiceI;
import edu.gisma.gh1043541.healthcaresystem.service.StaticDataService;
import edu.gisma.gh1043541.healthcaresystem.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientServiceI patientService;
    private final UserService userService;
    private final FamilyHistoryService familyHistoryService;

    public PatientController(PatientServiceI patientService, FamilyHistoryService familyHistoryService, UserService userService) {
        this.patientService = patientService;
        this.userService = userService;
        this.familyHistoryService = familyHistoryService;
    }

    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.findAll());
        return "patients/index";
    }

    @GetMapping("/new")
    public String createPatientForm(Model model) {
        Patient patient = new Patient();
        patient.setDateOfBirth(LocalDate.now());
        patient.getFamilyHistory().add(new FamilyHistory()); // one empty row
        model.addAttribute("patient", patient);
        model.addAttribute("conditions", StaticDataService.getCommonMedicalConditions());
        model.addAttribute("relationships", StaticDataService.getRelationships());
        return "patients/form";
    }

    @PostMapping("/save")
    public String save(
            @ModelAttribute  Patient patient,
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
        List<Map<String, Object>> rawList = mapper.readValue(
                familyHistoryJson,
                new TypeReference<List<Map<String, Object>>>() {}
        );

        if(patient.getPatientID() == null){
            patient.setCreatedBy(userId);
        }
        patient.setUpdatedBy(userId);
        List<FamilyHistory> histories = new ArrayList<>();

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
        patient.setFamilyHistory(histories);
        // Save patient first
        patientService.save(patient);
        return "redirect:/patients";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Patient patient = patientService.findById(id);
        patient.setDateOfBirth(LocalDate.now());

        List<FamilyHistory> histories = familyHistoryService.findByPatientId(id);
        model.addAttribute("familyHistories", histories); // <-- add this
        model.addAttribute("patient", patient);
        model.addAttribute("conditions", StaticDataService.getCommonMedicalConditions());
        model.addAttribute("relationships", StaticDataService.getRelationships());
        return "patients/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        patientService.delete(id);
        return "redirect:/patients";
    }
}

