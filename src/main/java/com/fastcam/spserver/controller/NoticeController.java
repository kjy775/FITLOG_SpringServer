package com.fastcam.spserver.controller;

import com.fastcam.spserver.entity.Notice;
import com.fastcam.spserver.service.NoticeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    NoticeService ns;

    @PostMapping("/writeNotice")
    public HashMap<String, Object> writeNotice(@RequestBody Notice notice) {
        HashMap<String, Object> map = new HashMap<>();
        int noticeid = ns.writeNotice(notice);
        map.put("noticeid", noticeid);
        return map;
    }

    @PostMapping("/updateNotice")
    public HashMap<String, Object> updateNotice(@RequestBody Notice notice) {
        HashMap<String, Object> map = new HashMap<>();
        ns.updateNotice(notice);
        map.put("msg", "OK");
        return map;
    }

    @DeleteMapping("/deleteNotice/{num}")
    public HashMap<String, Object> deleteNotice(@PathVariable("num") int num) {
        HashMap<String, Object> map = new HashMap<>();
        ns.deleteNotice(num);
        return map;
    }



}
