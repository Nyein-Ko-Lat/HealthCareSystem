
package edu.gisma.gh1043541.healthcaresystem.service;

import edu.gisma.gh1043541.healthcaresystem.entity.FamilyHistory;
import edu.gisma.gh1043541.healthcaresystem.entity.Patient;
import edu.gisma.gh1043541.healthcaresystem.repository.FamilyHistoryRepository;
import edu.gisma.gh1043541.healthcaresystem.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService implements IBaseService<Patient, Long> {

    private final PatientRepository patientRepo;
    private final FamilyHistoryRepository fhRepo;

    public PatientService(PatientRepository patientRepo, FamilyHistoryRepository fhRepo) {
        this.patientRepo = patientRepo;
        this.fhRepo = fhRepo;
    }

    @Override
    public Patient save(Patient patient) {

        Patient saved = patientRepo.save(patient);

        for (FamilyHistory fh : patient.getFamilyHistory()) {
            fh.setPatient(patientRepo.findById(saved.getPatientID()));
            fh.setCreatedBy(saved.getCreatedBy());
            fh.setUpdatedBy(saved.getUpdatedBy());
            fhRepo.save(fh);
        }
        return saved;
    }

    @Override
    public void delete(Long patientID, Long UpdatedBy) {
        // also delete family histories if needed
        List<FamilyHistory> histories = fhRepo.findByPatientId(patientID);
        for (FamilyHistory fh : histories) {
            fhRepo.delete(fh.getFamilyHistoryID(),UpdatedBy);
        }
        patientRepo.delete(patientID,UpdatedBy);
    }
    @Override
    public void delete(Long patientID) {
        // also delete family histories if needed
        List<FamilyHistory> histories = fhRepo.findByPatientId(patientID);
        for (FamilyHistory fh : histories) {
            fhRepo.delete(fh.getFamilyHistoryID(),1L);
        }
        patientRepo.delete(patientID,1L);
    }

    @Override
    public Patient findById(Long id) {
        Patient newPatient = patientRepo.findById(id);
        newPatient.setFamilyHistory(fhRepo.findByPatientId(newPatient.getPatientID()));
        return newPatient;
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> newPatientList = patientRepo.findAll();
        for (Patient patient : newPatientList) {
            patient.setFamilyHistory(fhRepo.findByPatientId(patient.getPatientID()));
        }
        return newPatientList;
    }
}
