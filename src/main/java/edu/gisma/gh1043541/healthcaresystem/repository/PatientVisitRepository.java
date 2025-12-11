package edu.gisma.gh1043541.healthcaresystem.repository;

import edu.gisma.gh1043541.healthcaresystem.entity.Appointment;
import edu.gisma.gh1043541.healthcaresystem.entity.PatientVisit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class PatientVisitRepository implements IBaseRepository<PatientVisit, Long> {

    private final JdbcTemplate jdbc;

    public PatientVisitRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public PatientVisit save(PatientVisit patientVisit) {
        String sql = "CALL sp_save_patient_visit(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbc.update(sql,
                patientVisit.getVisitID() != null ? patientVisit.getVisitID() : 0,
                patientVisit.getPatient().getPatientID(),
                patientVisit.getDoctor().getDoctorID(),
                patientVisit.getAppointment().getAppointmentID(),
                patientVisit.getPatientVisitDate(),
                patientVisit.getVisitReason(),
                patientVisit.getStatusCode(),
                patientVisit.getCreatedBy(),
                patientVisit.getUpdatedBy()
        );
        return patientVisit;
    }

    @Override
    public PatientVisit findById(Long patientVisitID) {
        String sql = "CALL sp_get_patient_visit_by_id(?)"; // create SP if not exists
        return jdbc.queryForObject(sql, new Object[]{patientVisitID}, (rs, rowNum) -> fillPatientVisit(rs));
    }

    @Override
    public void delete(Long patientVisitID) {
        String sql = "CALL sp_delete_patient_visit(?, ?)";
        jdbc.update(sql, patientVisitID, 1); // 1 = system admin
    }

    @Override
    public void delete(Long patientVisitID, Long updatedBy) {
        String sql = "CALL sp_delete_patient_visit(?, ?)";
        jdbc.update(sql, patientVisitID, updatedBy); // assume 1 = system user, replace with proper user
    }

    @Override
    public List<PatientVisit> findAll() {
        String sql = "CALL sp_list_patient_visits()";
        return jdbc.query(sql, (rs, rowNum) -> fillPatientVisit(rs));
    }

    public List<PatientVisit> findApprovedPatientVisitsByDate(LocalDate visitDate) {
        String sql = "CALL sp_get_approved_patient_visit_by_date(?)";
        return jdbc.query(sql,new Object[]{visitDate} ,(rs, rowNum) -> fillPatientVisit(rs));
    }

    private PatientVisit fillPatientVisit(ResultSet rs) throws SQLException {
        PatientVisit patientVisit = new PatientVisit();

        patientVisit.setVisitID(rs.getLong("Visit ID"));
        patientVisit.setPatient(new PatientRepository(jdbc).findById(rs.getLong("Patient ID")));
        patientVisit.setDoctor(new DoctorRepository(jdbc).findById(rs.getLong("Doctor ID")));
        Long AppointmentID = rs.getLong("Appointment ID");
        if(AppointmentID != null && AppointmentID != 0){
            patientVisit.setAppointment(new AppointmentRepository(jdbc).findById(AppointmentID));
        }
        patientVisit.setPatientVisitDate(rs.getTimestamp("Visit Date").toLocalDateTime());
        patientVisit.setVisitReason(rs.getString("Reason"));
        patientVisit.setStatusCode(rs.getString("Status"));
        patientVisit.setCreatedBy(rs.getLong("Created By"));
        patientVisit.setUpdatedBy(rs.getLong("Updated By"));
        patientVisit.setCreatedAt(rs.getTimestamp("Created At").toLocalDateTime());
        patientVisit.setUpdatedAt(rs.getTimestamp("Updated At").toLocalDateTime());

        return patientVisit;
    }
}