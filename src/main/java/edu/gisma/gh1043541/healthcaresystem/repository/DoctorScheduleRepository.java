package edu.gisma.gh1043541.healthcaresystem.repository;

import edu.gisma.gh1043541.healthcaresystem.entity.Appointment;
import edu.gisma.gh1043541.healthcaresystem.entity.DoctorSchedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DoctorScheduleRepository implements IBaseRepository<DoctorSchedule, Long> {

    private final JdbcTemplate jdbc;

    public DoctorScheduleRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public DoctorSchedule save(DoctorSchedule doctorSchedule) {
        String sql = "CALL sp_save_doctor_schedule(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbc.update(sql,
                doctorSchedule.getDoctorScheduleID() != null ? doctorSchedule.getDoctorScheduleID() : 0,
                doctorSchedule.getDoctor().getDoctorID(),
                doctorSchedule.getIsMon(),
                doctorSchedule.getIsTue(),
                doctorSchedule.getIsWed(),
                doctorSchedule.getIsThu(),
                doctorSchedule.getIsFri(),
                doctorSchedule.getIsSat(),
                doctorSchedule.getIsSun(),
                doctorSchedule.getStartTime(),
                doctorSchedule.getEndTime(),
                doctorSchedule.getStatusCode(),
                doctorSchedule.getCreatedBy(),
                doctorSchedule.getUpdatedBy()
        );

        return doctorSchedule;
    }

    @Override
    public DoctorSchedule findById(Long doctorScheduleID) {
        String sql = "CALL sp_get_doctor_schedule_by_id(?)"; // create SP if not exists
        return jdbc.queryForObject(sql, new Object[]{doctorScheduleID}, (rs, rowNum) -> fillDoctorSchedule(rs));
    }

    @Override
    public void delete(Long doctorScheduleID) {
        String sql = "CALL sp_delete_schedule_doctor(?, ?)";
        jdbc.update(sql, doctorScheduleID, 1); // 1 = system admin
    }

    @Override
    public void delete(Long doctorScheduleID, Long updatedBy) {
        String sql = "CALL sp_delete_doctor_schedule(?, ?)";
        jdbc.update(sql, doctorScheduleID, updatedBy); // assume 1 = system user, replace with proper user
    }

    @Override
    public List<DoctorSchedule> findAll() {
        String sql = "CALL sp_list_doctor_schedules()";
        return jdbc.query(sql, (rs, rowNum) -> fillDoctorSchedule(rs));
    }

    public List<DoctorSchedule> findAvailableSlot(Long doctorID, LocalDateTime appointmentDate) {
        String sql = "CALL sp_get_available_slots(?, ?, ?)";
        List<DoctorSchedule> doctorSchedules = new ArrayList<>();
        LocalTime time = appointmentDate.toLocalTime();
        String p_dayOftheWeek = appointmentDate.getDayOfWeek().toString();
        String formatted = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        doctorSchedules =  jdbc.query(sql,new Object[]{doctorID, p_dayOftheWeek, formatted },  (rs, rowNum) -> fillDoctorSchedule(rs));
        return doctorSchedules;
    }

    public DoctorSchedule findBydoctorId(Long doctorId) {
        String sql = "CALL sp_get_doctor_schedule_by_doctor_id(?)"; // create SP if not exists
        return jdbc.queryForObject(sql, new Object[]{doctorId}, (rs, rowNum) -> fillDoctorSchedule(rs));
    }

    private DoctorSchedule fillDoctorSchedule(ResultSet rs) throws SQLException {
        DoctorSchedule doctorSchedule = new DoctorSchedule();


        doctorSchedule.setDoctorScheduleID(rs.getLong("Schedule ID"));
        doctorSchedule.setIsMon(rs.getBoolean("Monday Flag"));
        doctorSchedule.setIsTue(rs.getBoolean("Tuesday Flag"));
        doctorSchedule.setIsWed(rs.getBoolean("Wednesday Flag"));
        doctorSchedule.setIsThu(rs.getBoolean("Thursday Flag"));
        doctorSchedule.setIsFri(rs.getBoolean("Friday Flag"));
        doctorSchedule.setIsSat(rs.getBoolean("Saturday Flag"));
        doctorSchedule.setIsSun(rs.getBoolean("Sunday Flag"));
        doctorSchedule.setStartTime(rs.getTime("Start Time").toLocalTime());
        doctorSchedule.setEndTime(rs.getTime("End Time").toLocalTime());
        doctorSchedule.setStatusCode(rs.getString("Status"));
        doctorSchedule.setCreatedBy(rs.getLong("Created By"));
        doctorSchedule.setUpdatedBy(rs.getLong("Updated By"));
        doctorSchedule.setCreatedAt(rs.getTimestamp("Created At").toLocalDateTime());
        doctorSchedule.setUpdatedAt(rs.getTimestamp("Updated At").toLocalDateTime());
        doctorSchedule.setDoctor( new DoctorRepository(jdbc).findById(rs.getLong("Doctor ID")));

        return doctorSchedule;
    }

}