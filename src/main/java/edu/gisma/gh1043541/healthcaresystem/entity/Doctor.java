package edu.gisma.gh1043541.healthcaresystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DoctorID;

    @Column(name = "FirstName",nullable = false, unique = true, length = 50)
    private String FirstName;

    @Column(name = "LastName",nullable = false, unique = true, length = 50)
    private String LastName;

    @Column(name = "Specialty",nullable = false, unique = true, length = 50)
    private String Specialty;

    @Column(name = "PhoneNumber",nullable = false, unique = true, length = 50)
    private String PhoneNumber;

    @Column(name = "Email",nullable = false, unique = true, length = 50)
    private String Email;

    // Getters and Setters

    public Long getDoctorID() {return DoctorID;}

    public void setDoctorID(Long doctorID) {DoctorID = doctorID;}

    public String getFirstName() {return FirstName;}

    public void setFirstName(String firstName) {FirstName = firstName;}

    public String getLastName() {return LastName;}

    public void setLastName(String lastName) {LastName = lastName;}

    public String getSpecialty() {return Specialty;}

    public void setSpecialty(String specialty) {Specialty = specialty;}

    public String getPhoneNumber() {return PhoneNumber;}

    public void setPhoneNumber(String phoneNumber) {PhoneNumber = phoneNumber;}

    public String getEmail() {return Email;}

    public void setEmail(String email) {Email = email;}


}

