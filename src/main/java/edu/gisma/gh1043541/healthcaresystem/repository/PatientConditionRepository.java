package edu.gisma.gh1043541.healthcaresystem.repository;

import edu.gisma.gh1043541.healthcaresystem.entity.PatientCondition;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PatientConditionRepository implements IBaseRepository<PatientCondition, Long> {

    private final JdbcTemplate jdbc;

    public PatientConditionRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public PatientCondition save(PatientCondition patientCondition) {
        String sql = "CALL sp_save_patient_condition(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbc.update(sql,
                patientCondition.getConditionID() != null ? patientCondition.getConditionID() : 0,
                patientCondition.getPatient().getPatientID(),
                patientCondition.getDoctor().getDoctorID(),
                patientCondition.getPatientVisit().getVisitID(),
                patientCondition.getDiagnosedDate(),
                patientCondition.getConditionName(),
                patientCondition.getDoctorNote(),
                patientCondition.getStatusCode(),
                patientCondition.getCreatedBy(),
                patientCondition.getUpdatedBy()
        );
        return patientCondition;
    }

    @Override
    public PatientCondition findById(Long patientConditionID) {
        String sql = "CALL sp_get_patient_condition_by_id(?)"; // create SP if not exists
        return jdbc.queryForObject(sql, new Object[]{patientConditionID}, (rs, rowNum) -> fillPatientCondition(rs));
    }

    @Override
    public void delete(Long patientConditionID) {
        String sql = "CALL sp_delete_patient_condition(?, ?)";
        jdbc.update(sql, patientConditionID, 1); // 1 = system admin
    }

    @Override
    public void delete(Long patientConditionID, Long updatedBy) {
        String sql = "CALL sp_delete_patient_condition(?, ?)";
        jdbc.update(sql, patientConditionID, updatedBy); // assume 1 = system user, replace with proper user
    }

    @Override
    public List<PatientCondition> findAll() {
        String sql = "CALL sp_list_patient_conditions()";
        return jdbc.query(sql, (rs, rowNum) -> fillPatientCondition(rs));
    }

    private PatientCondition fillPatientCondition(ResultSet rs) throws SQLException {
        PatientCondition patientCondition = new PatientCondition();

        patientCondition.setConditionID(rs.getLong("PatientCondition ID"));
        patientCondition.setPatient(new PatientRepository(jdbc).findById(rs.getLong("Patient ID")));
        patientCondition.setDoctor(new DoctorRepository(jdbc).findById(rs.getLong("Doctor ID")));
        patientCondition.setPatientVisit(new PatientVisitRepository(jdbc).findById(rs.getLong("PatientVisit ID")));
        patientCondition.setDiagnosedDate(rs.getTimestamp("Diagnosed Date").toLocalDateTime());
        patientCondition.setConditionName(rs.getString("Condition Name"));
        patientCondition.setDoctorNote(rs.getString("Note"));
        patientCondition.setStatusCode(rs.getString("Status"));
        patientCondition.setCreatedBy(rs.getLong("Created By"));
        patientCondition.setUpdatedBy(rs.getLong("Updated By"));
        patientCondition.setCreatedAt(rs.getTimestamp("Created At").toLocalDateTime());
        patientCondition.setUpdatedAt(rs.getTimestamp("Updated At").toLocalDateTime());

        return patientCondition;
    }
}