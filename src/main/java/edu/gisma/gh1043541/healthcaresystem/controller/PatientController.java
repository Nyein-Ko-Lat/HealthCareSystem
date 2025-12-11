package edu.gisma.gh1043541.healthcaresystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gisma.gh1043541.healthcaresystem.entity.FamilyHistory;
import edu.gisma.gh1043541.healthcaresystem.entity.Patient;
import edu.gisma.gh1043541.healthcaresystem.service.FamilyHistoryService;
import edu.gisma.gh1043541.healthcaresystem.service.PatientService;
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

    private final PatientService patientService;
    private final UserService userService;
    private final FamilyHistoryService familyHistoryService;

    public PatientController(PatientService patientService, FamilyHistoryService familyHistoryService, UserService userService) {
        this.patientService = patientService;
        this.userService = userService;
        this.familyHistoryService = familyHistoryService;
    }
}

