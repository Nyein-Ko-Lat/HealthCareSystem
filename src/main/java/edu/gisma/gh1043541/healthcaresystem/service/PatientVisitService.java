package edu.gisma.gh1043541.healthcaresystem.service;

import edu.gisma.gh1043541.healthcaresystem.entity.Appointment;
import edu.gisma.gh1043541.healthcaresystem.entity.PatientVisit;
import edu.gisma.gh1043541.healthcaresystem.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientVisitService implements IBaseService<PatientVisit, Long> {

    private final PatientVisitRepository patientVisitRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public PatientVisitService(PatientVisitRepository patientVisitRepository,
                               DoctorRepository doctorRepository,
                               PatientRepository patientRepository,
                               AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.patientVisitRepository = patientVisitRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public PatientVisit save(PatientVisit pVst) {
        if(pVst.getAppointment().getAppointmentID()!=null){
            Appointment appointment = appointmentRepository.findById(pVst.getAppointment().getAppointmentID());
            pVst.setAppointment(appointment);
            pVst.setPatient(patientRepository.findById(appointment.getPatient().getPatientID()));
            pVst.setDoctor(doctorRepository.findById(appointment.getDoctor().getDoctorID()));
        }
        pVst.setStatusCode("APR");      //Default for Patient Visit
        try{
            patientVisitRepository.save(pVst);
        }
        catch (Exception ex){

        }
        finally {
            return  pVst;
        }
    }

    @Override
    public void delete(Long patientVisitID) {
        patientVisitRepository.delete(patientVisitID);
    }

    @Override
    public void delete(Long patientVisitID, Long UpdatedBy) {
        patientVisitRepository.delete(patientVisitID, UpdatedBy);
    }

    @Override
    public PatientVisit findById(Long patientVisitID) {
        return patientVisitRepository.findById(patientVisitID);
    }

    @Override
    public List<PatientVisit> findAll() {
        return patientVisitRepository.findAll();
    }

    public List<PatientVisit> findApprovedPatientVisitsByDate(LocalDate visitDate) {
        List<PatientVisit> patientVisit = patientVisitRepository.findApprovedPatientVisitsByDate(visitDate);
        return patientVisit;
    }
}