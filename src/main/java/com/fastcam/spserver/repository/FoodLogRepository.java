package com.fastcam.spserver.repository;

import com.fastcam.spserver.entity.FoodLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.List;

public interface FoodLogRepository extends JpaRepository<FoodLog, Integer> {
    List<FoodLog> findByMemberNum(int mnum);
  
    List<FoodLog> findByMemberNumAndIndateBetween(int mnum, LocalDateTime start, LocalDateTime end);
  
    List<FoodLog> findByMemberNumAndIndateBetweenOrderByIndateDesc(int id, Timestamp start, Timestamp end);
}
