package com.fastcam.spserver.controller;

import com.fastcam.spserver.dto.PaymentConfirmRequest;
import com.fastcam.spserver.dto.PaymentConfirmResponse;
import com.fastcam.spserver.entity.Member;
import com.fastcam.spserver.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/charge")
public class PaymentController {
    @Autowired
    PaymentService ps;

    @PostMapping("/confirm")
    public HashMap<String, Object> confirmPayment(@RequestBody PaymentConfirmRequest request, @RequestParam("mnum") int mnum) {
        HashMap<String, Object> map = new HashMap<>();

        PaymentConfirmResponse result = ps.confirmPayment(request, mnum);
        if(result.isDone()) {
            map.put("msg", "ok");
        } else {
            map.put("msg", result.status());
        }
        return map;
    }

}
