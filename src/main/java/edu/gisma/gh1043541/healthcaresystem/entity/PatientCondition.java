package edu.gisma.gh1043541.healthcaresystem.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_conditions")
public class PatientCondition extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conditionID;

    @ManyToOne
    @JoinColumn(name = "PatientID", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "DoctorID", nullable = false)
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "PatientVisitID", nullable = true)
    private PatientVisit patientVisit;

    @Column(name = "DiagnosedDate")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime diagnosedDate;

    @Column(name = "DoctorNote")
    private String doctorNote;

    @Column(name = "ConditionName")
    private String conditionName;

    public Long getConditionID() {
        return conditionID;
    }

    public void setConditionID(Long patientVisitID) {
        this.conditionID = patientVisitID;
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

    public PatientVisit getPatientVisit() {
        return patientVisit;
    }

    public void setPatientVisit(PatientVisit patientVisit) {
        this.patientVisit = patientVisit;
    }

    public LocalDateTime getDiagnosedDate() {
        return diagnosedDate;
    }

    public void setDiagnosedDate(LocalDateTime patientVisitDate) {
        this.diagnosedDate = patientVisitDate;
    }

    public String getDoctorNote() {
        return doctorNote;
    }

    public void setDoctorNote(String doctorNote) {
        this.doctorNote = doctorNote;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

}
