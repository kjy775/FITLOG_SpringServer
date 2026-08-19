package com.fastcam.spserver.controller;

import com.fastcam.spserver.dto.NutritionDto;
import com.fastcam.spserver.dto.RequestDto;
import com.fastcam.spserver.dto.ResponseDto;
import com.fastcam.spserver.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/ai")
@CrossOrigin({"http://localhost:8000", "http://localhost:3000"})
public class AIController {

    @Autowired
    AIService aiService;

    @PostMapping("/query")
    public ResponseEntity<ResponseDto> askAI(@RequestBody RequestDto req) {
        System.out.println("요청 : " + req.getQuery());
        ResponseDto res = aiService.askAI(req);
        System.out.println("응답 : " + res);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/findFood")
    public Map<String, Object> findFood(@RequestParam("image") MultipartFile file) {
        return aiService.findFood(file);
    }

}