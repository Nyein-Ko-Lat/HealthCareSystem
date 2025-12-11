package edu.gisma.gh1043541.healthcaresystem.service;

import java.util.Arrays;
import java.util.List;

public class StaticDataService {
    // ---------------------------
    // Medical Conditions
    // ---------------------------
    private static final List<String> COMMON_MEDICAL_CONDITIONS = Arrays.asList(
            "Diabetes",
            "Hypertension",
            "Asthma",
            "Heart Disease",
            "Cancer",
            "Chronic Kidney Disease",
            "Liver Disease",
            "Stroke",
            "Arthritis",
            "Thyroid Disorder"
    );

    // ---------------------------
    // Relationship Types
    // ---------------------------
    private static final List<String> RELATIONSHIPS = Arrays.asList(
            "Father",
            "Mother",
            "Brother",
            "Sister",
            "Son",
            "Daughter",
            "Spouse",
            "Grandparent",
            "Other Relative"
    );

    // ---------------------------
    // Relationship Types
    // ---------------------------
    private static final List<String> DrSpecialist = Arrays.asList(
            "General Practice",
            "Pediatric",
            "Cardiology",
            "Surgery",
            "Neurology",
            "Gastroenterology",
            "Dermatology",
            "Ophthalmology",
            "Endocrinology",
            "Pulmonology",
            "Nephrology"
    );
    // ---------------------------
    // Relationship Types
    // ---------------------------
    private static final List<String> PatientCondition = Arrays.asList(
            "General Checkup",
            "Follow-Up",
            "Transfer to Specialist",
            "Emergency Visit",
            "Medication Review",
            "Lab Test Required",
            "Imaging Required (X-ray, MRI, USG)",
            "Chronic Disease Management",
            "Post-Procedure Review",
            "Vaccination / Immunization",
            "Others"
    );

    public static List<String> getCommonMedicalConditions() {
        return COMMON_MEDICAL_CONDITIONS;
    }

    public static List<String> getRelationships() {
        return RELATIONSHIPS;
    }
    public static List<String> getDrSpecialist() {return DrSpecialist;}
    public static List<String> getPatientCondition() {return PatientCondition;}


}
