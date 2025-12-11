
package edu.gisma.gh1043541.healthcaresystem.service;

import edu.gisma.gh1043541.healthcaresystem.entity.Doctor;
import edu.gisma.gh1043541.healthcaresystem.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService implements IBaseService<Doctor, Long> {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor save(Doctor dr) {
        return doctorRepository.save(dr);
    }

    @Override
    public void delete(Long doctorID) {
        doctorRepository.delete(doctorID);
    }

    @Override
    public void delete(Long doctorID, Long UpdatedBy) {
        doctorRepository.delete(doctorID,UpdatedBy);
    }

    @Override
    public Doctor findById(Long doctorID) {
        return doctorRepository.findById(doctorID);
    }

    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public List<Doctor> findBySpecialityId(String speciality) {return doctorRepository.findBySpecialityId(speciality);    }
}
