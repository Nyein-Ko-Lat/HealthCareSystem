package edu.gisma.gh1043541.healthcaresystem.controller;

import edu.gisma.gh1043541.healthcaresystem.entity.Doctor;
import edu.gisma.gh1043541.healthcaresystem.entity.DoctorSchedule;
import edu.gisma.gh1043541.healthcaresystem.service.DoctorScheduleService;
import edu.gisma.gh1043541.healthcaresystem.service.DoctorService;
import edu.gisma.gh1043541.healthcaresystem.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorScheduleService scheduleService;
    private final UserService userService;

    public DoctorController(DoctorService doctorService, DoctorScheduleService scheduleService, UserService userService) {
        this.doctorService = doctorService;
        this.scheduleService = scheduleService;
        this.userService = userService;
    }

    @GetMapping("/schedule/list")
    public String listSchedules(Model model) {
        List<DoctorSchedule> schedules = scheduleService.findAll();
        model.addAttribute("schedules", schedules);
        return "doctorschedules/index"; // HTML page
    }
    // CREATE FORM
    @GetMapping("/schedule/new")
    public String createScheduleForm(Model model) {
        model.addAttribute("schedule", new DoctorSchedule());
        model.addAttribute("doctors", doctorService.findAll()); // dropdown
        return "doctorschedules/form"; // HTML page
    }
    // EDIT FORM
    @GetMapping("/schedule/edit/{id}")
    public String editScheduleForm(@PathVariable Long id, Model model) {
        DoctorSchedule schedule = scheduleService.findById(id);
        model.addAttribute("schedule", schedule);
        model.addAttribute("doctors", doctorService.findAll());
        return "doctorschedules/form";
    }

    // SAVE (CREATE + UPDATE)
    @PostMapping("/schedule/save")
    public String saveDoctorSchedule(@ModelAttribute("doctorSchedule") DoctorSchedule doctorSchedule) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Long userId = userService.findByUsername(username).getId();
        doctorSchedule.setCreatedBy(userId);
        doctorSchedule.setUpdatedBy(userId);
        scheduleService.save(doctorSchedule);
        return "redirect:/doctors/schedule/list";
    }


    @GetMapping("/schedule/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Long userId = userService.findByUsername(username).getId();
        scheduleService.delete(id,userId);
        return "redirect:/doctors/schedule/list";
    }
}

