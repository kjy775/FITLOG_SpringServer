package com.fastcam.spserver.repository;

import com.fastcam.spserver.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
