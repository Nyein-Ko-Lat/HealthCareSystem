package edu.gisma.gh1043541.healthcaresystem.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_visits")
public class PatientVisit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitID;

    @ManyToOne
    @JoinColumn(name = "PatientID", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "DoctorID", nullable = false)
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "AppointmentID", nullable = true)
    private Appointment appointment;

    @Column(name = "PatientVisitDate")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime patientVisitDate;

    @Column(name = "VisitReason")
    private String visitReason;

    public Long getVisitID() {
        return visitID;
    }

    public void setVisitID(Long appointmentID) {
        this.visitID = appointmentID;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public LocalDateTime getPatientVisitDate() {
        return patientVisitDate;
    }

    public void setPatientVisitDate(LocalDateTime appointmentDate) {
        this.patientVisitDate = appointmentDate;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String reason) {
        this.visitReason = reason;
    }

}
