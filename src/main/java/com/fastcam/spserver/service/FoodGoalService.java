package com.fastcam.spserver.service;

import com.fastcam.spserver.entity.FoodGoal;
import com.fastcam.spserver.repository.FoodGoalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FoodGoalService {

    @Autowired
    FoodGoalRepository fgr;

    public void goalSave(FoodGoal foodGoal) {
        fgr.save(foodGoal);
    }

    public FoodGoal getFoodGoal(FoodGoal foodGoal) {
        return fgr.findByNum(foodGoal.getNum());
    }
}
