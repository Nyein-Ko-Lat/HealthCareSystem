package edu.gisma.gh1043541.healthcaresystem.repository;

import edu.gisma.gh1043541.healthcaresystem.entity.Doctor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DoctorRepository implements IBaseRepository<Doctor, Long> {

    private final JdbcTemplate jdbc;

    public DoctorRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Doctor save(Doctor doctor) {
        String sql = "CALL sp_save_doctor(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbc.update(sql,
                doctor.getDoctorID() != null ? doctor.getDoctorID() : 0,
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getSpecialty(),
                doctor.getPhoneNumber(),
                doctor.getEmail(),
                doctor.getStatusCode(),
                doctor.getCreatedBy(),
                doctor.getUpdatedBy()
        );
        return doctor;
    }

    @Override
    public Doctor findById(Long doctorID) {
        String sql = "CALL sp_get_doctor_by_id(?)"; // create SP if not exists
        return jdbc.queryForObject(sql, new Object[]{doctorID}, (rs, rowNum) -> fillDoctor(rs));
    }

    @Override
    public void delete(Long doctorID) {
        String sql = "CALL sp_delete_doctor(?, ?)";
        jdbc.update(sql, doctorID, 1); // 1 = system admin
    }

    @Override
    public void delete(Long doctorID, Long updatedBy) {
        String sql = "CALL sp_delete_doctor(?, ?)";
        jdbc.update(sql, doctorID, updatedBy); // assume 1 = system user, replace with proper user
    }

    @Override
    public List<Doctor> findAll() {
        String sql = "CALL sp_list_doctors()";
        return jdbc.query(sql, (rs, rowNum) -> fillDoctor(rs));
    }

    public List<Doctor> findBySpecialityId(String speciality) {
        String sql = "CALL sp_get_doctor_by_Speciality(?)"; // create SP if not exists
        return jdbc.query(sql, new Object[]{speciality}, (rs, rowNum) -> fillDoctor(rs));
    }


    private Doctor fillDoctor(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();

        doctor.setDoctorID(rs.getLong("Doctor ID"));
        doctor.setFirstName(rs.getString("First Name"));
        doctor.setLastName(rs.getString("Last Name"));
        doctor.setSpecialty(rs.getString("Specialty"));
        doctor.setPhoneNumber(rs.getString("Phone Number"));
        doctor.setEmail(rs.getString("Email"));
        doctor.setStatusCode(rs.getString("Status"));
        doctor.setCreatedBy(rs.getLong("Created By"));
        doctor.setUpdatedBy(rs.getLong("Updated By"));
        doctor.setCreatedAt(rs.getTimestamp("Created At").toLocalDateTime());
        doctor.setUpdatedAt(rs.getTimestamp("Updated At").toLocalDateTime());

        return doctor;
    }
}