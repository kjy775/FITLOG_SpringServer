package com.fastcam.spserver.repository;

import com.fastcam.spserver.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    List<Follow> findByFfrom(int ffrom);

    List<Follow> findByFto(int fto);
}
