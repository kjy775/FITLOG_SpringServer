package com.fastcam.spserver.repository;

import com.fastcam.spserver.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Integer> {

    List<Community> findAllByOrderByIndateDesc();

    List<Community> findByMemberNumOrderByIndateDesc(int mnum);

    List<Community> findByMemberNumInOrderByIndateDesc(List<Integer> mnums);

    List<Community> findByMemberNum(int mnum);

    void deleteByMemberNum(int mnum);

    Optional<Community> findByNum(int num);
}
