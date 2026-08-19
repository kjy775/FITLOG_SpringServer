package com.fastcam.spserver.repository;

import com.fastcam.spserver.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciesRepository extends JpaRepository<Member, Integer> {

}
