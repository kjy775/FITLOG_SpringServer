package com.fastcam.spserver.controller;

import com.fastcam.spserver.service.ExerciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/exercies")
public class ExerciesController {

    @Autowired
    ExerciesService es;

}
