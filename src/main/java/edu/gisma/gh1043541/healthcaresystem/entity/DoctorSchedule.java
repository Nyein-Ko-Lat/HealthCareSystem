package edu.gisma.gh1043541.healthcaresystem.entity;

import jakarta.persistence.*;

import javax.print.Doc;
import java.time.LocalTime;

@Entity
@Table(name = "doctor_schedules")
public class DoctorSchedule extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DoctorScheduleID;

    @Column(name = "IsMon",nullable = false)
    private boolean IsMon;

    @Column(name = "IsTue",nullable = false)
    private boolean IsTue;

    @Column(name = "IsWed",nullable = false)
    private boolean IsWed;

    @Column(name = "IsThu",nullable = false)
    private boolean IsThu;

    @Column(name = "IsFri",nullable = false)
    private boolean IsFri;

    @Column(name = "IsSat",nullable = false)
    private boolean IsSat;

    @Column(name = "IsSun",nullable = false)
    private boolean IsSun;

    @Column(name = "StartTime",nullable = false)
    private LocalTime StartTime;

    @Column(name = "EndTime",nullable = false)
    private LocalTime EndTime;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @ManyToOne
    @JoinColumn(name = "DoctorID")
    private Doctor doctor;

    public Long getDoctorScheduleID() {
        return DoctorScheduleID;
    }

    public void setDoctorScheduleID(Long doctorScheduleID) {
        DoctorScheduleID = doctorScheduleID;
    }

    public boolean getIsMon() {
        return IsMon;
    }

    public void setIsMon(boolean mon) {
        IsMon = mon;
    }

    public boolean getIsTue() {
        return IsTue;
    }

    public void setIsTue(boolean tue) {
        IsTue = tue;
    }

    public boolean getIsWed() {
        return IsWed;
    }

    public void setIsWed(boolean wed) {
        IsWed = wed;
    }

    public boolean getIsThu() {
        return IsThu;
    }

    public void setIsThu(boolean thu) {
        IsThu = thu;
    }

    public boolean getIsFri() {
        return IsFri;
    }

    public void setIsFri(boolean fri) {
        IsFri = fri;
    }

    public boolean getIsSat() {
        return IsSat;
    }

    public void setIsSat(boolean sat) {
        IsSat = sat;
    }

    public boolean getIsSun() {
        return IsSun;
    }

    public void setIsSun(boolean sun) {
        IsSun = sun;
    }

    public LocalTime getStartTime() {
        return StartTime;
    }

    public void setStartTime(LocalTime startTime) {
        StartTime = startTime;
    }

    public LocalTime getEndTime() {
        return EndTime;
    }

    public void setEndTime(LocalTime endTime) {
        EndTime = endTime;
    }
}
