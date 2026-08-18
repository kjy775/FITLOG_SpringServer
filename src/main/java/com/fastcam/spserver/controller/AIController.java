package com.fastcam.spserver.controller;

import com.fastcam.spserver.dto.RequestDto;
import com.fastcam.spserver.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@CrossOrigin({"http://localhost:8000", "http://localhost:3000"})
public class AIController {
    private final WebClient webClient;

    private AIController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8000")
                .build();
    }

    @PostMapping("/query")
    public ResponseEntity<ResponseDto> askAI(@RequestBody RequestDto req) {
        System.out.println("요청 : " + req.getQuery());
        ResponseDto res = webClient.post()
                .uri("/query")
                .bodyValue(req)
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .block();
        System.out.println("응답 : " + res);
        return ResponseEntity.ok(res);
    }
}
