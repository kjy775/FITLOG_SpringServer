package com.fastcam.spserver.repository;

import com.fastcam.spserver.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    Optional<Reply> findByNum(int num);

    List<Reply> findByNumOrderByIdDesc(int num);
}
