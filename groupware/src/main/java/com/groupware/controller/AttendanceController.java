package com.groupware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.groupware.security.CustomUserDetails;
import com.groupware.service.AttendanceService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/checkIn")
    @ResponseBody
    public Map<String, Object> checkIn(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Map<String, Object> response = new HashMap<>();
        try {
            attendanceService.checkIn(userDetails.getEmployeeDTO().getEmployeeId());
            response.put("success", true);
            response.put("message", "출근이 안전하게 등록되었습니다.");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }

    @PostMapping("/checkOut")
    @ResponseBody
    public Map<String, Object> checkOut(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Map<String, Object> response = new HashMap<>();
        try {
            attendanceService.checkOut(userDetails.getEmployeeDTO().getEmployeeId());
            response.put("success", true);
            response.put("message", "퇴근이 기록되었습니다. 고생하셨습니다!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }
}