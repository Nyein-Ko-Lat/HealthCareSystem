package edu.gisma.gh1043541.healthcaresystem.repository;

import edu.gisma.gh1043541.healthcaresystem.entity.FamilyHistory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FamilyHistoryRepository implements IBaseRepository<FamilyHistory, Long> {

    private final JdbcTemplate jdbc;

    public FamilyHistoryRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Long save(FamilyHistory fh) {
        String sql = "CALL sp_save_family_history(?, ?, ?, ?, ?, ?, ?)";
        return jdbc.queryForObject(sql, new Object[]{
                        fh.getFamilyHistoryID() != null ? fh.getFamilyHistoryID() : 0,
                        fh.getPatient().getPatientID(),
                        fh.getMedicalCondition(),
                        fh.getRelationship(),
                        fh.getStatusCode(),
                        fh.getCreatedBy(),
                        fh.getUpdatedBy()
                },
                Long.class
        );
    }


    @Override
    public void delete(Long familyHistoryID) {
        String sql = "CALL sp_delete_family_history(?, ?)";
        jdbc.update(sql, familyHistoryID, 1); // system user
    }

    @Override
    public void delete(Long familyHistoryID, Long updatedBy) {
        String sql = "CALL sp_delete_family_history(?, ?)";
        jdbc.update(sql, familyHistoryID, updatedBy); // system user
    }

    @Override
    public FamilyHistory findById(Long id) {
        String sql = "SELECT * FROM v_family_history WHERE `FamilyHistoryID` = ?";
        return jdbc.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> fillFamilyHistory(rs));
    }

    @Override
    public List<FamilyHistory> findAll() {
        String sql = "SELECT * FROM v_family_history ORDER BY `Patient Name`";
        return jdbc.query(sql, (rs, rowNum) -> fillFamilyHistory(rs));
    }

    public List<FamilyHistory> findByPatientId(Long patientId) {
        String sql = "CALL sp_list_family_history_by_patientId(?)";
        return jdbc.query(sql, new Object[]{patientId}, (rs, rowNum) -> fillFamilyHistory(rs));
    }

    private FamilyHistory fillFamilyHistory(ResultSet rs) throws SQLException{
        FamilyHistory fh = new FamilyHistory();
        fh.setFamilyHistoryID(rs.getLong("Family History ID"));
        fh.setPatient(new PatientRepository(jdbc).findById(rs.getLong("Patient ID")) );
        fh.setMedicalCondition(rs.getString("Condition"));
        fh.setRelationship(rs.getString("Relationship"));
        fh.setStatusCode(rs.getString("Status"));
        fh.setCreatedBy(rs.getLong("Created By"));
        fh.setUpdatedBy(rs.getLong("Updated By"));
        fh.setCreatedAt(rs.getTimestamp("Created At").toLocalDateTime());
        fh.setUpdatedAt(rs.getTimestamp("Updated At").toLocalDateTime());
        return fh;
    }
}
