package com.fastcam.spserver.service;

import com.fastcam.spserver.entity.ExercisesLog;
import com.fastcam.spserver.entity.Member;
import com.fastcam.spserver.repository.ExercisesLogRepository;
import com.fastcam.spserver.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExercisesLogService {
    @Autowired
    ExercisesLogRepository elr;

    @Autowired
    MemberRepository mr;

    public void addExercisesLog(ExercisesLog exercisesLog) {
        Member member = mr.findByNum(exercisesLog.getMember().getNum());
        exercisesLog.setMember(member);
        elr.save(exercisesLog);
    }

    public List<ExercisesLog> getExercisesLogs(int mnum) {
        return elr.findByMemberNum(mnum);
    }

    public void deleteExercisesLog(int num) {
        elr.deleteById(num);
    }
}
