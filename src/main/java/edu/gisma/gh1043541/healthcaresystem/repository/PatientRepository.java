package edu.gisma.gh1043541.healthcaresystem.repository;

import edu.gisma.gh1043541.healthcaresystem.entity.Patient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PatientRepository implements IBaseRepository<Patient, Long> {

    private final JdbcTemplate jdbc;

    public PatientRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Long save(Patient patient) {
        String sql = "CALL sp_save_patient(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbc.queryForObject(sql,new Object[]{
                        patient.getPatientID() != null ? patient.getPatientID() : 0,
                        patient.getFirstName(),
                        patient.getLastName(),
                        patient.getDateOfBirth(),
                        patient.getGender(),
                        patient.getBloodType(),
                        patient.getStatusCode(),
                        patient.getCreatedBy(),
                        patient.getUpdatedBy()
                },
                Long.class);
    }

    @Override
    public void delete(Long patientID) {
        String sql = "CALL sp_delete_patient(?, ?)";
        jdbc.update(sql, patientID, 1); // 1 = system admin
    }

    @Override
    public void delete(Long patientID, Long updatedBy) {
        String sql = "CALL sp_delete_patient(?, ?)";
        jdbc.update(sql, patientID, updatedBy); // assume 1 = system user, replace with proper user
    }

    @Override
    public Patient findById(Long id) {
        String sql = "CALL sp_get_patient_by_id(?)"; // create SP if not exists
        return jdbc.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> fillPatient(rs));
    }

    @Override
    public List<Patient> findAll() {
        String sql = "CALL sp_list_patients()";
        return jdbc.query(sql, (rs, rowNum) -> fillPatient(rs));
    }


    private Patient fillPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();

        patient.setPatientID(rs.getLong("Patient ID"));
        patient.setFirstName(rs.getString("First Name"));
        patient.setLastName(rs.getString("Last Name"));
        patient.setDateOfBirth(rs.getDate("Date of Birth").toLocalDate());
        patient.setGender(rs.getString("Gender"));
        patient.setBloodType(rs.getString("Blood Type"));
        patient.setStatusCode(rs.getString("Status"));
        patient.setCreatedBy(rs.getLong("Created By"));
        patient.setUpdatedBy(rs.getLong("Updated By"));
        patient.setCreatedAt(rs.getTimestamp("Created At").toLocalDateTime());
        patient.setUpdatedAt(rs.getTimestamp("Updated At").toLocalDateTime());

        return patient;
    }
}