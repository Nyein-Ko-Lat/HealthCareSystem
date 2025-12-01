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
            "Relative",
            "Other"
    );

    public static List<String> getCommonMedicalConditions() {
        return COMMON_MEDICAL_CONDITIONS;
    }

    public static List<String> getRelationships() {
        return RELATIONSHIPS;
    }


}
