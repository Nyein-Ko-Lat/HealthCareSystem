package edu.gisma.gh1043541.healthcaresystem.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentID;

    @ManyToOne
    @JoinColumn(name = "PatientID", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "DoctorID", nullable = false)
    private Doctor doctor;

    @Column(name = "AppointmentDate")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime appointmentDate;

    @Column(name = "Reason")
    private String reason;

    @Column(name = "StatusCode")
    private String statusCode = "USE";

    public Long getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(Long appointmentID) {
        this.appointmentID = appointmentID;
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

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
