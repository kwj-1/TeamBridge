package com.groupware.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groupware.dto.NoticeDTO;
import com.groupware.mapper.DashboardMapper;

@Service
public class DashboardService {

    // 대시보드 "최신 공지 3건"에 보여줄 개수 - 공지사항 목록 페이지(NoticeService)의
    // 페이지 크기(10건)와는 다른 대시보드 전용 값이라 여기 따로 둠
    private static final int DASHBOARD_NOTICE_COUNT = 3;

    private final DashboardMapper dashboardMapper;
    private final NoticeService noticeService;

    public DashboardService(DashboardMapper dashboardMapper, NoticeService noticeService) {
        this.dashboardMapper = dashboardMapper;
        this.noticeService = noticeService;
    }

    // 대시보드 화면에 필요한 데이터 모음
    @Transactional(readOnly = true)
    public Map<String, Object> getMainDashboardData(int employeeId) {
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("employee", null);
        resultMap.put("attendance", null);
        // 공지 목록 1페이지(고정글 우선 + 최신순, 최대 10건)를 그대로 재사용해서
        // 앞의 3건만 잘라 씀 - 대시보드 전용 쿼리를 새로 만들 필요가 없음
        List<NoticeDTO> latestNotices = noticeService.getNoticeList(null, 1);
        resultMap.put("notices", latestNotices.subList(0, Math.min(DASHBOARD_NOTICE_COUNT, latestNotices.size())));
        resultMap.put("waitCount", 0);
        resultMap.put("progressCount", 0);
        resultMap.put("approvals", new java.util.ArrayList<>());
        resultMap.put("todayEvents", new java.util.ArrayList<>());
        resultMap.put("miniCalendarEvents", new java.util.ArrayList<>());

        return resultMap;
    }
}