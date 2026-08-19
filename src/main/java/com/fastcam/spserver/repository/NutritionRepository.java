package com.fastcam.spserver.repository;

import com.fastcam.spserver.entity.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionRepository extends JpaRepository<Nutrition, Integer> {

    Nutrition findByFname(String menu);
}
