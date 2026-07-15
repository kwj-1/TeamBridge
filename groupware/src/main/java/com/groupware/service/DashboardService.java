package com.groupware.service;

import com.groupware.dto.NoticeDTO;
import com.groupware.mapper.DashboardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final DashboardMapper dashboardMapper;

    public DashboardService(DashboardMapper dashboardMapper) {
        this.dashboardMapper = dashboardMapper;
    }

    // 대시보드 화면에 필요한 데이터 모음
    @Transactional(readOnly = true)
    public Map<String, Object> getMainDashboardData(int employeeId) {
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("employee", null);
        resultMap.put("attendance", null);
        resultMap.put("notices", new java.util.ArrayList<>());
        resultMap.put("waitCount", 0);
        resultMap.put("progressCount", 0);
        resultMap.put("approvals", new java.util.ArrayList<>());
        resultMap.put("todayEvents", new java.util.ArrayList<>());
        resultMap.put("miniCalendarEvents", new java.util.ArrayList<>());

        return resultMap;
    }
}