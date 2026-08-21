package com.fastcam.spserver.repository;

import com.fastcam.spserver.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Integer> {

    List<Community> findByMemberNum(int mnum);

    void deleteByMemberNum(int mnum);

    Optional<Community> findByNum(int num);

    List<Community> findByStatusOrderByIndateDesc(String y);

    List<Community> findByMemberNumAndStatusOrderByIndateDesc(int num, String status);

    List<Community> findByMemberNumInAndStatusOrderByIndateDesc(List<Integer> mnums, String y);
}
