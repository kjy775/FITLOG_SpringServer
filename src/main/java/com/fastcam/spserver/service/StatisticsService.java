package com.fastcam.spserver.service;

import com.fastcam.spserver.entity.WeightLog;
import com.fastcam.spserver.repository.ExercisesLogRepository;
import com.fastcam.spserver.repository.FoodLogRepository;
import com.fastcam.spserver.repository.WeightLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

@Service
@Transactional
public class StatisticsService {
    @Autowired
    WeightLogRepository wlr;

    @Autowired
    ExercisesLogRepository elr;

    @Autowired
    FoodLogRepository flr;


    public HashMap<String, Object> getSummary(int id, String period, String date) {
        HashMap<String, Object> result = new HashMap<>();

        //건강 통계 요약 - 현재 체중
        WeightLog weightLog = wlr.findTopByMember_NumOrderByIndateDesc(id);
        if(weightLog != null) {
            result.put("currentWeight", weightLog.getWeight());
        } else {
            result.put("currentWeight", null);
        }

        //통계 조회 기간(주간/월간)
        LocalDate selectedDate = LocalDate.parse(date);
        LocalDate startDate;
        LocalDate endDate;
        int days;
        if("month".equals(period)) {
            YearMonth yearMonth = YearMonth.from(selectedDate);

            startDate = yearMonth.atDay(1);
            endDate = yearMonth.atEndOfMonth();

            days = yearMonth.lengthOfMonth();
        } else if ("week".equals(period)) {
            startDate = selectedDate.minusDays(selectedDate.getDayOfWeek().getValue() - 1);

            endDate = selectedDate;
            days = (int)(endDate.toEpochDay() - startDate.toEpochDay()) + 1;
        }

        //건강 통계 요약 - 총 운동 소비


        //건강 통계 요약 - 일평균 식사 섭취



        return result;
    }
}
