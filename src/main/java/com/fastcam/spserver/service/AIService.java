package com.fastcam.spserver.service;

import com.fastcam.spserver.dto.ExerciseDto;
import com.fastcam.spserver.dto.NutritionDto;
import com.fastcam.spserver.dto.RequestDto;
import com.fastcam.spserver.dto.ResponseDto;
import com.fastcam.spserver.entity.Chat;
import com.fastcam.spserver.entity.FoodGoal;
import com.fastcam.spserver.entity.FoodLog;
import com.fastcam.spserver.entity.Nutrition;
import com.fastcam.spserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.client.ReactorClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIService {

    private final WebClient webClient;
    private final RestClient restClient;

    @Autowired
    FoodGoalRepository fgr;

    @Autowired
    FoodLogRepository flr;

    @Autowired
    NutritionRepository nr;

    @Autowired
    ChatRepository cr;

    @Autowired
    MemberRepository mr;

    public AIService( @Value("${fastapi.base-url}") String fastApiBaseUrl) {


        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofMinutes(5));

        ReactorClientHttpRequestFactory factory =
                new ReactorClientHttpRequestFactory(httpClient);

        this.webClient = WebClient.builder()
                .baseUrl(fastApiBaseUrl)
                .build();

        this.restClient = RestClient.builder()
                .baseUrl(fastApiBaseUrl)
                .requestFactory(factory)
                .build();
    }


    // 일반 AI 질문
    public ResponseDto query(RequestDto req) {

        Chat userChat = new Chat();
        userChat.setMember(mr.findByNum(req.getUserId()));
        userChat.setContent(req.getUserChat());
        userChat.setSender("user");
        cr.save(userChat);

        ResponseDto res = webClient.post()
                .uri("/query")
                .bodyValue(req)
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .block();

        Chat aiChat = new Chat();
        if (res != null) {
            aiChat.setMember(mr.findByNum(req.getUserId()));
            aiChat.setContent(res.getAnswer());
            aiChat.setSender("ai");
        }
        cr.save(aiChat);
        return res;
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

    // 운동 소모칼로리 추정
    public ExerciseDto findExercise(String exName) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/findExercise")
                        .queryParam("exName", exName)
                        .build())
                .retrieve()
                .body(ExerciseDto.class);
    }

    public HashMap<String, Object> getUserGoal(int mnum) {
        HashMap<String, Object> res = new HashMap<>();
        FoodGoal fg = fgr.findByMemberNum(mnum);
        if (fg == null) {
            res.put("targetCalories", null);
            res.put("targetCarbsG", null);
            res.put("targetProteinG", null);
            res.put("targetFatG", null);
            return res;
        }
        res.put("targetCalories",fg.getGoalCalories());
        res.put("targetCarbsG",fg.getGoalCarbs());
        res.put("targetProteinG",fg.getGoalProtein());
        res.put("targetFatG",fg.getGoalFat());
        return res;
    }

    public HashMap<String, Object> getConsumedToday(int mnum) {
        HashMap<String, Object> res = new HashMap<>();
        LocalDate today = LocalDate.now();

        List<FoodLog> flList =
                flr.findByMemberNumAndIndate(mnum, today);

        int cal = 0;
        float carbs = 0.0f;
        float protein = 0.0f;
        float fat = 0.0f;
        for(FoodLog fl : flList){
            cal += fl.getCalories();
            carbs += fl.getCarbs();
            protein += fl.getProtein();
            fat += fl.getFat();
        }

        res.put("calories",cal);
        res.put("carbsG",carbs);
        res.put("proteinG",protein);
        res.put("fatG",fat);

        return res;
    }

    public List<Nutrition> queryFoodCandidates(int mc) {
        HashMap<String, Object> res = new HashMap<>();
        return nr.findByKcalLessThanEqual(mc);
    }
}