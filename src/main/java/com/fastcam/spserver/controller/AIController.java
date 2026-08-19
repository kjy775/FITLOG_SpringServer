package com.fastcam.spserver.controller;

import com.fastcam.spserver.dto.RequestDto;
import com.fastcam.spserver.dto.ResponseDto;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.client.ReactorClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.io.File;
import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/ai")
@CrossOrigin({"http://localhost:8000", "http://localhost:3000"})
public class AIController {
    private final WebClient webClient;

    private final RestClient restClient;

    HttpClient httpClient = HttpClient.create()
            .responseTimeout(Duration.ofMinutes(5));

    ReactorClientHttpRequestFactory factory =
            new ReactorClientHttpRequestFactory(httpClient);

    private AIController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8000")
                .build();
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8000")
                .requestFactory(factory)
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

    @Autowired
    ServletContext sc;




    @PostMapping("/findFood")
    public Map<String, Object> findFood(@RequestParam("image") MultipartFile file){
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
}
