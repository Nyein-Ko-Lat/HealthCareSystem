package edu.gisma.gh1043541.healthcaresystem.controller;

import edu.gisma.gh1043541.healthcaresystem.entity.Appointment;
import edu.gisma.gh1043541.healthcaresystem.entity.DoctorSchedule;
import edu.gisma.gh1043541.healthcaresystem.service.AppointmentService;
import edu.gisma.gh1043541.healthcaresystem.service.DoctorScheduleService;
import edu.gisma.gh1043541.healthcaresystem.service.DoctorService;
import edu.gisma.gh1043541.healthcaresystem.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final AppointmentService service;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final DoctorScheduleService doctorScheduleService;
    private final AppointmentService appointmentService;

    ApiController(AppointmentService service,
                  PatientService patientService,
                  DoctorService doctorService,
                  DoctorScheduleService doctorScheduleService,
                  AppointmentService appointmentService) {
        this.service = service;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.doctorScheduleService = doctorScheduleService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/doctors/schedule/{doctorId}")
    @ResponseBody
    public DoctorSchedule getDoctorSchedule(@PathVariable Long doctorId) {
        return doctorScheduleService.findByDoctorId(doctorId);
    }

    @GetMapping("/doctors/getcount")
    @ResponseBody
    public int getDoctorCount() {
        return doctorScheduleService.findAll().size();
    }

    @GetMapping("/doctors/getavailablecount")
    @ResponseBody
    public List<DoctorSchedule> getAvailableDoctorCount() {
        return doctorScheduleService.getAvailableDoctor(LocalDateTime.now());
    }

    @GetMapping("/patients/getavailablecount")
    @ResponseBody
    public List<Appointment> getAppointmentCount() {
        return appointmentService.findApprovedAppointmentsByDate(LocalDate.now());
    }
}
