package com.fastcam.spserver.service;

import com.fastcam.spserver.entity.Member;
import com.fastcam.spserver.entity.WeightLog;
import com.fastcam.spserver.repository.MemberRepository;
import com.fastcam.spserver.repository.WeightLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WeightLogService {

    @Autowired
    WeightLogRepository wlr;

    @Autowired
    MemberRepository mr;


    public WeightLog getWeightLog(int num) {
        return wlr.findByNum(num);
    }

    public void writeWeightLog(WeightLog weightLog) {
        Member member = mr.findByNum(weightLog.getMember().getNum());
        weightLog.setMember(member);
        wlr.save(weightLog);
    }
}
