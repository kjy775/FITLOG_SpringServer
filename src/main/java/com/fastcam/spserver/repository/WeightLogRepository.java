package com.fastcam.spserver.repository;

import com.fastcam.spserver.entity.WeightLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightLogRepository extends JpaRepository<WeightLog, Integer> {
    WeightLog findByNum(int num);

    WeightLog findTopByMember_NumOrderByIndateDesc(int id);
}
