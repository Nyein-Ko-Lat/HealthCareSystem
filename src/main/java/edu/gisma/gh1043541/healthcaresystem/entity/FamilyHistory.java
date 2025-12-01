package edu.gisma.gh1043541.healthcaresystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "family_history")
public class FamilyHistory extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long FamilyHistoryID;


    @Column(name = "MedicalCondition",nullable = false, unique = true, length = 50)
    private String MedicalCondition;

    @Column(name = "Relationship",nullable = false, unique = true, length = 50)
    private String Relationship;

    public Long getFamilyHistoryID() {return FamilyHistoryID;}

    public void setFamilyHistoryID(Long familyHistoryID) {FamilyHistoryID = familyHistoryID;}

    public String getMedicalCondition() {return MedicalCondition;}

    public void setMedicalCondition(String medicalCondition) {MedicalCondition = medicalCondition;}

    public String getRelationship() {return Relationship;}

    public void setRelationship(String relationship) {Relationship = relationship;}

    @ManyToOne
    @JoinColumn(name = "PatientID")
    private Patient patient;

    public Patient getPatient() {return patient;}

    public void setPatient(Patient patient) {this.patient = patient;}
}
