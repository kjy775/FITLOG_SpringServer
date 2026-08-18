package com.fastcam.spserver.repository;

import com.fastcam.spserver.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findById(String id);

    Member findByNum(int num);

    void deleteById(String id);
}
