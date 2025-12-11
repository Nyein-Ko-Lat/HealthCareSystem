package edu.gisma.gh1043541.healthcaresystem.service;

import edu.gisma.gh1043541.healthcaresystem.entity.Appointment;
import edu.gisma.gh1043541.healthcaresystem.entity.Appointment;
import edu.gisma.gh1043541.healthcaresystem.entity.DoctorSchedule;
import edu.gisma.gh1043541.healthcaresystem.repository.*;
import edu.gisma.gh1043541.healthcaresystem.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService implements IBaseService<Appointment, Long> {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, DoctorScheduleRepository doctorScheduleRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorScheduleRepository = doctorScheduleRepository;
    }

    @Override
    public Appointment save(Appointment drSch) {
        return appointmentRepository.save(drSch);
    }

    @Override
    public void delete(Long appointmentID) {
        appointmentRepository.delete(appointmentID);
    }

    @Override
    public void delete(Long appointmentID, Long UpdatedBy) {
        appointmentRepository.delete(appointmentID, UpdatedBy);
    }

    @Override
    public Appointment findById(Long appointmentID) {
        return appointmentRepository.findById(appointmentID);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public boolean findAvailableSlot(Long doctorID, LocalDateTime appointmentDate) {
        List<DoctorSchedule> appointments = doctorScheduleRepository.findAvailableSlot(doctorID, appointmentDate);
        return appointments != null && appointments.size() > 0;
    }

    public List<Appointment> findApprovedAppointmentsByDate(LocalDate appointmentDate) {
        List<Appointment> appointments = appointmentRepository.findApprovedAppointmentsByDate(appointmentDate);
        return appointments;
    }
}