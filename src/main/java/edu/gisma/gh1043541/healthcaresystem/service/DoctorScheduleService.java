
package edu.gisma.gh1043541.healthcaresystem.service;

import edu.gisma.gh1043541.healthcaresystem.entity.Doctor;
import edu.gisma.gh1043541.healthcaresystem.entity.DoctorSchedule;
import edu.gisma.gh1043541.healthcaresystem.repository.DoctorRepository;
import edu.gisma.gh1043541.healthcaresystem.repository.DoctorScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class DoctorScheduleService implements IBaseService<DoctorSchedule, Long> {

    private final DoctorScheduleRepository doctorScheduleRepository;

    public DoctorScheduleService(DoctorScheduleRepository doctorScheduleRepository) {
        this.doctorScheduleRepository = doctorScheduleRepository;
    }

    @Override
    public DoctorSchedule save(DoctorSchedule drSch) {
        try {
            doctorScheduleRepository.save(drSch);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            return  drSch;
        }
    }

    @Override
    public void delete(Long doctorScheduleID) {
        doctorScheduleRepository.delete(doctorScheduleID);
    }

    @Override
    public void delete(Long doctorScheduleID, Long UpdatedBy) {
        doctorScheduleRepository.delete(doctorScheduleID,UpdatedBy);
    }

    @Override
    public DoctorSchedule findById(Long doctorScheduleID) {
        return doctorScheduleRepository.findById(doctorScheduleID);
    }

    @Override
    public List<DoctorSchedule> findAll() {
        return doctorScheduleRepository.findAll();
    }

    public DoctorSchedule findByDoctorId(Long doctorId) {
        return doctorScheduleRepository.findBydoctorId(doctorId);
    }

    public List<DoctorSchedule> getAvailableDoctor(LocalDateTime daysOfWeeks) {
        List<DoctorSchedule> availableDoctors = doctorScheduleRepository.getAvailableDoctor(daysOfWeeks);
        availableDoctors.sort((sch1, sch2) ->
                sch1.getDoctor().getSpecialty().compareTo(sch2.getDoctor().getSpecialty())
        );
        return availableDoctors;
    }
    public Map<String,Integer> getAvailableDoctorsScheduleBySepciality(LocalDateTime daysOfWeeks) {
        return doctorScheduleRepository.getAvailableDoctorsScheduleBySepciality(daysOfWeeks);
    }

    public Map<List<String>, Integer> getAvgWaitingBySpeciality() {
        return doctorScheduleRepository.getAvgWaitingBySpeciality();
    }

    public Map<List<String>, Integer> getAvgWaitingByDoctor() {
        return doctorScheduleRepository.getAvgWaitingByDoctor();
    }
}
