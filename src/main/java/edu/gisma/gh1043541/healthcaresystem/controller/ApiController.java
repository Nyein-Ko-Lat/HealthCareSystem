package edu.gisma.gh1043541.healthcaresystem.controller;

import edu.gisma.gh1043541.healthcaresystem.entity.DoctorSchedule;
import edu.gisma.gh1043541.healthcaresystem.service.AppointmentService;
import edu.gisma.gh1043541.healthcaresystem.service.DoctorScheduleService;
import edu.gisma.gh1043541.healthcaresystem.service.DoctorService;
import edu.gisma.gh1043541.healthcaresystem.service.PatientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final AppointmentService service;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final DoctorScheduleService doctorScheduleService;

    ApiController(AppointmentService service,
                  PatientService patientService,
                  DoctorService doctorService,
                  DoctorScheduleService doctorScheduleService) {
        this.service = service;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.doctorScheduleService = doctorScheduleService;
    }
    @GetMapping("/doctors/schedule/{doctorId}")
    @ResponseBody
    public DoctorSchedule getDoctorSchedule(@PathVariable Long doctorId) {
        return doctorScheduleService.findByDoctorId(doctorId);
    }
}
