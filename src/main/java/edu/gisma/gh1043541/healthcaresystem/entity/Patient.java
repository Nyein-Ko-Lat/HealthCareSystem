package edu.gisma.gh1043541.healthcaresystem.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PatientID;

    @Column(name = "FirstName",nullable = false, unique = true, length = 50)
    private String FirstName;

    @Column(name = "LastName",nullable = false, unique = true, length = 50)
    private String LastName;

    @Column(name = "DateOfBirth",nullable = false, unique = true, length = 50)
    private LocalDate DateOfBirth;

    @Column(name = "Gender",nullable = false, unique = true, length = 50)
    private String Gender;

    @Column(name = "BloodType",nullable = false, unique = true, length = 50)
    private String BloodType;

    public List<FamilyHistory> getFamilyHistory() {
        return familyHistory;
    }

    // Getters and Setters

    public Long getPatientID() {return PatientID;}

    public void setPatientID(Long patientID) {PatientID = patientID;}

    public String getFirstName() {return FirstName;}

    public void setFirstName(String firstName) {FirstName = firstName;}

    public String getLastName() {return LastName;}

    public void setLastName(String lastName) {LastName = lastName;}

    public LocalDate getDateOfBirth() {return DateOfBirth;}

    public void setDateOfBirth(LocalDate dateOfBirth) {DateOfBirth = dateOfBirth;}

    public String getGender() {return Gender;}

    public void setGender(String gender) {Gender = gender;}

    public String getBloodType() {return BloodType;}

    public void setBloodType(String bloodType) {BloodType = bloodType;}

    public void setFamilyHistory(List<FamilyHistory> familyHistory) {
        this.familyHistory = familyHistory;
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FamilyHistory> familyHistory = new ArrayList<>();


}

