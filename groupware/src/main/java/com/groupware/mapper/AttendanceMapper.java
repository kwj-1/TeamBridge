package com.groupware.mapper;

import java.time.LocalDate;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.groupware.dto.AttendanceDTO;

@Mapper
public interface AttendanceMapper {
    AttendanceDTO selectTodayAttendance(@Param("employeeId") int employeeId, @Param("today") LocalDate today);
    void insertCheckIn(@Param("employeeId") int employeeId, @Param("today") LocalDate today, 
                       @Param("checkInTime") String checkInTime, @Param("status") String status);
    void updateCheckOut(@Param("employeeId") int employeeId, @Param("today") LocalDate today, 
                        @Param("checkOutTime") String checkOutTime);
}