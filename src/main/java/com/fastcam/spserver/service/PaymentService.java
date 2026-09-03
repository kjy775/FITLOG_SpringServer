package com.fastcam.spserver.service;

import com.fastcam.spserver.dto.PaymentConfirmRequest;
import com.fastcam.spserver.dto.PaymentConfirmResponse;
import com.fastcam.spserver.entity.Member;
import com.fastcam.spserver.entity.Payment;
import com.fastcam.spserver.entity.Subscription;
import com.fastcam.spserver.repository.PaymentRepository;
import com.fastcam.spserver.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
public class PaymentService {
    @Value("${toss.secret-key}")
    private String secretKey;

    private final RestClient restClient = RestClient.create("https://api.tosspayments.com");

    @Autowired
    PaymentRepository pr;

    @Autowired
    SubscriptionRepository sr;

    public PaymentConfirmResponse confirmPayment(PaymentConfirmRequest request, Member member) {
        String authorization = "Basic " + Base64.getEncoder()
                .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));

        try {
            PaymentConfirmResponse result = restClient.post()
                    .uri("/v1/payments/confirm")
                    .header(HttpHeaders.AUTHORIZATION, authorization)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of(
                            "paymentKey", request.paymentKey(),
                            "orderId", request.orderId(),
                            "amount", request.amount()
                    ))
                    .retrieve()
                    .body(PaymentConfirmResponse.class);

            //결제 승인 성공
            if (result.isDone()) {
                Payment payment = new Payment();
                payment.setMember(member);
                payment.setPaymentKey(result.paymentKey());
                payment.setPrice(result.totalAmount());
                payment.setProductName(request.productName());

                pr.save(payment);

                int days = getSubscriptionDays(result.productName());


            }

        } catch (HttpClientErrorException e) {
            // 토스페이먼츠가 4xx로 내려주는 에러 바디(코드/메시지)를 그대로 전달
            throw new RuntimeException("결제 승인 실패: " + e.getResponseBodyAsString(), e);
        }
    }
}
