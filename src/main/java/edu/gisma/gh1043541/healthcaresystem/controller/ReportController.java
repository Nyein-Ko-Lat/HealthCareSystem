package edu.gisma.gh1043541.healthcaresystem.controller;

import edu.gisma.gh1043541.healthcaresystem.entity.DoctorSchedule;
import edu.gisma.gh1043541.healthcaresystem.service.AppointmentService;
import edu.gisma.gh1043541.healthcaresystem.service.DoctorScheduleService;
import edu.gisma.gh1043541.healthcaresystem.service.DoctorService;
import edu.gisma.gh1043541.healthcaresystem.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @GetMapping("/doctor/details")
    public String listSchedules(Model model) {
        return "reports/doctor"; // HTML page
    }

    @GetMapping("/averagewaitingtimesp")
    public String averagewaitingtimesp(Model model) {
        return "reports/averagewaitingtimesp"; // HTML page
    }

    @GetMapping("/averagewaitingtimedr")
    public String averagewaitingtimedr(Model model) {
        return "reports/averagewaitingtimedr"; // HTML page
    }

    @GetMapping("/topreports")
    public String topreports(Model model) {
        return "reports/topreport"; // HTML page
    }

}
