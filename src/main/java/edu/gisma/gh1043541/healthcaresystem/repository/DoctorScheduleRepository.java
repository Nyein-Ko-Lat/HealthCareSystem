package edu.gisma.gh1043541.healthcaresystem.repository;

import edu.gisma.gh1043541.healthcaresystem.entity.Appointment;
import edu.gisma.gh1043541.healthcaresystem.entity.DoctorSchedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<DoctorSchedule> getAvailableDoctor(LocalDateTime appointmentDate) {
        String sql = "CALL sp_get_available_doctors_schedule(?)";
        List<DoctorSchedule> doctorSchedules = new ArrayList<>();
        String p_dayOftheWeek = appointmentDate.getDayOfWeek().toString();
        doctorSchedules =  jdbc.query(sql,new Object[]{p_dayOftheWeek },  (rs, rowNum) -> fillDoctorSchedule(rs));
        return doctorSchedules;
    }

    public Map<String,Integer> getAvailableDoctorsScheduleBySepciality(LocalDateTime appointmentDate) {
        String sql = "CALL sp_get_available_doctors_schedule_by_sepciality(?)";
        Map<String,Integer> doctorSchedules = new HashMap<>();
        String p_dayOftheWeek = appointmentDate.getDayOfWeek().toString();
        doctorSchedules =jdbc.query(sql, new Object[]{p_dayOftheWeek}, new ResultSetExtractor<Map<String, Integer>>() {
            @Override
            public Map<String, Integer> extractData(ResultSet rs) throws SQLException {
                Map<String, Integer> results = new HashMap<>();
                while (rs.next()) {
                    String key = rs.getString("Specialty");
                    Integer value = rs.getInt("DoctorCount");
                    results.put(key, value);
                }
                return results;
            }
        });
        return doctorSchedules;
    }

    public Map<List<String>, Integer> getAvgWaitingBySpeciality() {
        String sql = "CALL sp_get_avg_waiting_by_speciality()";
        Map<List<String>,Integer> doctorSchedules = new HashMap<>();
        doctorSchedules =jdbc.query(sql, new ResultSetExtractor<Map<List<String>, Integer>>() {
            @Override
            public Map<List<String>, Integer> extractData(ResultSet rs) throws SQLException {
                Map<List<String>, Integer> results = new HashMap<>();
                while (rs.next()) {
                    List<String> key = new ArrayList<>();
                    key.add(rs.getString("DiagnosedDate"));
                    key.add(rs.getString("Specialty"));
                    Integer value = rs.getInt("AverageWaiting");
                    results.put(key, value);
                }
                return results;
            }
        });
        return doctorSchedules;
    }


    public Map<List<String>, Integer> getAvgWaitingByDoctor() {
        String sql = "CALL sp_get_avg_waiting_by_doctor()";
        Map<List<String>,Integer> doctorSchedules = new HashMap<>();
        doctorSchedules =jdbc.query(sql, new ResultSetExtractor<Map<List<String>, Integer>>() {
            @Override
            public Map<List<String>, Integer> extractData(ResultSet rs) throws SQLException {
                Map<List<String>, Integer> results = new HashMap<>();
                while (rs.next()) {
                    List<String> key = new ArrayList<>();
                    key.add(rs.getString("Doctor"));
                    key.add(rs.getString("Specialty"));
                    Integer value = rs.getInt("AverageWaiting");
                    results.put(key, value);
                }
                return results;
            }
        });
        return doctorSchedules;
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