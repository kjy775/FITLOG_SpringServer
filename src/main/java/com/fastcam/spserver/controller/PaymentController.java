package com.fastcam.spserver.controller;

import com.fastcam.spserver.dto.PaymentConfirmRequest;
import com.fastcam.spserver.dto.PaymentConfirmResponse;
import com.fastcam.spserver.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/charge")
public class PaymentController {
    @Autowired
    PaymentService ps;



    @PostMapping("/confirm")
    public HashMap<String, Object> confirmPayment(@RequestBody PaymentConfirmRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        PaymentConfirmResponse result = ps.confirmPayment(request);
        if(result.isDone()) {
            map.put("msg", "ok");
        } else {
            map.put("msg", result.status());
        }
        return map;
    }

}
