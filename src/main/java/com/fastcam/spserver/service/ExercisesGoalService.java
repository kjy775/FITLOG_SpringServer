package com.fastcam.spserver.service;

import com.fastcam.spserver.entity.ExercisesGoal;
import com.fastcam.spserver.repository.ExercisesGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExercisesGoalService {
    @Autowired
    ExercisesGoalRepository egr;

    public void insertExercisesGoal(ExercisesGoal exercisesGoal) {
        egr.save(exercisesGoal);
    }

    public ExercisesGoal getExercisesGoal(int num) {
        return egr.findByMemberNum(num);
    }
}
