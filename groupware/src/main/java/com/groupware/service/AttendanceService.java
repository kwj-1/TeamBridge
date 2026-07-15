package com.groupware.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groupware.dto.AttendanceDTO;
import com.groupware.mapper.AttendanceMapper;

@Service
public class AttendanceService {

    private final AttendanceMapper attendanceMapper;

    public AttendanceService(AttendanceMapper attendanceMapper) {
        this.attendanceMapper = attendanceMapper;
    }

    @Transactional
    public void checkIn(int employeeId) {
        LocalDate today = LocalDate.now();

        if (attendanceMapper.selectTodayAttendance(employeeId, today) != null) {
            throw new IllegalStateException("이미 오늘 출근 처리가 완료되었습니다.");
        }

        LocalTime nowTime = LocalTime.now();
        // 서버 시간을 기준으로 지각 판정
        String status = nowTime.isAfter(LocalTime.of(9, 0, 0)) ? "LATE" : "NORMAL";
        String formattedTime = nowTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        attendanceMapper.insertCheckIn(employeeId, today, formattedTime, status);
    }

    @Transactional
    public void checkOut(int employeeId) {
        LocalDate today = LocalDate.now();
        AttendanceDTO attendance = attendanceMapper.selectTodayAttendance(employeeId, today);

        if (attendance == null) {
            throw new IllegalStateException("출근 기록이 없어 퇴근 처리가 불가능합니다.");
        }
        if (attendance.getCheckOutTime() != null) {
            throw new IllegalStateException("이미 오늘 퇴근 처리가 완료되었습니다.");
        }

        String formattedTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        attendanceMapper.updateCheckOut(employeeId, today, formattedTime);
    }
}