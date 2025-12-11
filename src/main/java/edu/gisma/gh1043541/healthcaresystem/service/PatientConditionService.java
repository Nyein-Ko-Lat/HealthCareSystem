package edu.gisma.gh1043541.healthcaresystem.service;

import edu.gisma.gh1043541.healthcaresystem.entity.PatientVisit;
import edu.gisma.gh1043541.healthcaresystem.entity.PatientCondition;
import edu.gisma.gh1043541.healthcaresystem.repository.PatientVisitRepository;
import edu.gisma.gh1043541.healthcaresystem.repository.DoctorRepository;
import edu.gisma.gh1043541.healthcaresystem.repository.PatientRepository;
import edu.gisma.gh1043541.healthcaresystem.repository.PatientConditionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientConditionService implements IBaseService<PatientCondition, Long> {

    private final PatientConditionRepository patientConditionRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PatientVisitRepository patientVisitRepository;

    public PatientConditionService(PatientConditionRepository patientConditionRepository,
                                   DoctorRepository doctorRepository,
                                   PatientRepository patientRepository,
                                   PatientVisitRepository patientVisitRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.patientConditionRepository = patientConditionRepository;
        this.patientVisitRepository = patientVisitRepository;
    }

    @Override
    public PatientCondition save(PatientCondition pVst) {
        if(pVst.getPatientVisit()!=null){
            PatientVisit patientVisit = patientVisitRepository.findById(pVst.getPatientVisit().getVisitID());
            pVst.setPatientVisit(patientVisit);
            pVst.setPatient(patientRepository.findById(patientVisit.getPatient().getPatientID()));
            pVst.setDoctor(doctorRepository.findById(patientVisit.getDoctor().getDoctorID()));
        }
        pVst.setStatusCode("USE");      //Default for Patient Visit
        return patientConditionRepository.save(pVst);
    }

    @Override
    public void delete(Long patientConditionID) {
        patientConditionRepository.delete(patientConditionID);
    }

    @Override
    public void delete(Long patientConditionID, Long UpdatedBy) {
        patientConditionRepository.delete(patientConditionID, UpdatedBy);
    }

    @Override
    public PatientCondition findById(Long patientConditionID) {
        return patientConditionRepository.findById(patientConditionID);
    }

    @Override
    public List<PatientCondition> findAll() {
        return patientConditionRepository.findAll();
    }
}