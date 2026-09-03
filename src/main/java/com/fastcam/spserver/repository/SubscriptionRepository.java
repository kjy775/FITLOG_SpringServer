package com.fastcam.spserver.repository;

import com.fastcam.spserver.entity.Member;
import com.fastcam.spserver.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository
        extends JpaRepository<Subscription, Integer> {

    Optional<Subscription> findByMember(Member member);
}