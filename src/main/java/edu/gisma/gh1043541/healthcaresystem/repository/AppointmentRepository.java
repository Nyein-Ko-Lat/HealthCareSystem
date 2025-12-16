package edu.gisma.gh1043541.healthcaresystem.repository;

import edu.gisma.gh1043541.healthcaresystem.entity.Appointment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class AppointmentRepository implements IBaseRepository<Appointment, Long> {

    private final JdbcTemplate jdbc;

    public AppointmentRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Long save(Appointment appointment) {
        String sql = "CALL sp_save_appointment(?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbc.queryForObject(sql, new Object[]{
                appointment.getAppointmentID() != null ? appointment.getAppointmentID() : 0,
                appointment.getPatient().getPatientID(),
                appointment.getDoctor().getDoctorID(),
                appointment.getAppointmentDate(),
                appointment.getReason(),
                appointment.getStatusCode(),
                appointment.getCreatedBy(),
                appointment.getUpdatedBy()
                },
                Long.class
        );
    }

    @Override
    public Appointment findById(Long appointmentID) {
        String sql = "CALL sp_get_appointment_by_id(?)"; // create SP if not exists
        return jdbc.queryForObject(sql, new Object[]{appointmentID}, (rs, rowNum) -> fillAppointment(rs));
    }

    @Override
    public void delete(Long appointmentID) {
        String sql = "CALL sp_delete_appointment(?, ?)";
        jdbc.update(sql, appointmentID, 1); // 1 = system admin
    }

    @Override
    public void delete(Long appointmentID, Long updatedBy) {
        String sql = "CALL sp_delete_appointment(?, ?)";
        jdbc.update(sql, appointmentID, updatedBy); // assume 1 = system user, replace with proper user
    }

    @Override
    public List<Appointment> findAll() {
        String sql = "CALL sp_list_appointments()";
        return jdbc.query(sql, (rs, rowNum) -> fillAppointment(rs));
    }

    private Appointment fillAppointment(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();

        appointment.setAppointmentID(rs.getLong("Appointment ID"));
        appointment.setPatient(new PatientRepository(jdbc).findById(rs.getLong("Patient ID")));
        appointment.setDoctor(new DoctorRepository(jdbc).findById(rs.getLong("Doctor ID")));
        appointment.setAppointmentDate(rs.getTimestamp("Appointment Date").toLocalDateTime());
        appointment.setReason(rs.getString("Reason"));
        appointment.setStatusCode(rs.getString("Status"));
        appointment.setCreatedBy(rs.getLong("Created By"));
        appointment.setUpdatedBy(rs.getLong("Updated By"));
        appointment.setCreatedAt(rs.getTimestamp("Created At").toLocalDateTime());
        appointment.setUpdatedAt(rs.getTimestamp("Updated At").toLocalDateTime());

        return appointment;
    }

    public List<Appointment> findApprovedAppointmentsByDate(LocalDate appointmentDate) {
        String sql = "CALL sp_get_approved_appointment_by_date(?)";
        return jdbc.query(sql,new Object[]{appointmentDate} ,(rs, rowNum) -> fillAppointment(rs));
    }
}