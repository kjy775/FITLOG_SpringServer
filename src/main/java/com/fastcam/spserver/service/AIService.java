package com.fastcam.spserver.service;

import com.fastcam.spserver.dto.NutritionDto;
import com.fastcam.spserver.dto.RequestDto;
import com.fastcam.spserver.dto.ResponseDto;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.client.ReactorClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Map;

@Service
public class AIService {

    private final WebClient webClient;
    private final RestClient restClient;

    public AIService() {

        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofMinutes(5));

        ReactorClientHttpRequestFactory factory =
                new ReactorClientHttpRequestFactory(httpClient);

        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8000")
                .build();

        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8000")
                .requestFactory(factory)
                .build();
    }


    // 일반 AI 질문
    public ResponseDto askAI(RequestDto req) {

        return webClient.post()
                .uri("/query")
                .bodyValue(req)
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .block();
    }


    // 음식 사진 분석
    public Map<String, Object> findFood(MultipartFile file) {

        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        builder.part("file", file.getResource())
                .filename(file.getOriginalFilename())
                .contentType(
                        MediaType.parseMediaType(file.getContentType())
                );

        return restClient.post()
                .uri("/findFood")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(builder.build())
                .retrieve()
                .body(Map.class);
    }


    // 음식 영양정보 추정
    public NutritionDto findNutrition(String foodName) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/findNutrition")
                        .queryParam("foodName", foodName)
                        .build())
                .retrieve()
                .body(NutritionDto.class);
    }
}