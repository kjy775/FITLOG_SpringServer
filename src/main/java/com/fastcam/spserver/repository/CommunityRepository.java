package com.fastcam.spserver.repository;

import com.fastcam.spserver.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Integer> {

    List<Community> findByMemberNum(int mnum);

    List<Community> findByMemberNumIn(List<Integer> mnums);

    List<Community> findByTitleContainingOrContentContaining(String keyword, String keyword1);
}
