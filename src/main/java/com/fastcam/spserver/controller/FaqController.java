package com.fastcam.spserver.controller;

import com.fastcam.spserver.entity.Faq;
import com.fastcam.spserver.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/faq")
public class FaqController {

    @Autowired
    FaqService fs;

    @GetMapping("/getFaq")
    public HashMap<String, Object> getFaq(){
        HashMap<String, Object> map = new HashMap<>();
        List<Faq> list =  fs.getFaq();
        map.put("faqList", list);
        return map;
    }


}
