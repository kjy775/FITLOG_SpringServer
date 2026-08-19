package com.fastcam.spserver.service;

import com.fastcam.spserver.entity.FoodLog;
import com.fastcam.spserver.entity.Member;
import com.fastcam.spserver.repository.FoodLogRepository;
import com.fastcam.spserver.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FoodLogService {
    @Autowired
    FoodLogRepository flr;

    @Autowired
    MemberRepository mr;


    public void addFoodLog(FoodLog foodLog) {
        Member member = mr.findByNum(foodLog.getMember().getNum());
        foodLog.setMember(member);
        flr.save(foodLog);
    }

    public List<FoodLog> getFoodLog(int mnum) {
        return flr.findByMemberNum(mnum);
    }

    public void deleteFoodLog(int num) {
        flr.deleteById(num);
    }
}
