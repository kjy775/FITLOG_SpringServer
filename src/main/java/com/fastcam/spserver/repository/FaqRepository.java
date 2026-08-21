package com.fastcam.spserver.repository;

import com.fastcam.spserver.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository <Faq, Integer> {
}
