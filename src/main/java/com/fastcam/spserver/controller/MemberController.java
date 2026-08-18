package com.fastcam.spserver.controller;

import com.fastcam.spserver.entity.Member;
import com.fastcam.spserver.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/member")

public class MemberController {

    @Autowired
    MemberService ms;

    // 로그인
    @PostMapping("/loginLocal")
    public HashMap<String, Object> loginLocal(
            @RequestParam("id") String id,
            @RequestParam("pass") String pass) {
        HashMap<String, Object> map = new HashMap<>();
        Member mdto = ms.getMember(id);
        if (mdto == null)
            map.put("msg", "아이디/패스워드를 확인하세요");
        else if (!mdto.getPass().equals(pass))
            map.put("msg", "아이디/패스워드를 확인하세요");
        else {
            map.put("msg", "OK");
            map.put("loginUser", mdto);
            System.out.println(mdto);
        }
        return map;
    }

    // 회원가입
    @PostMapping("/join")
    public HashMap<String, Object> join(@RequestBody  Member member){
        HashMap<String, Object> map = new HashMap<String, Object>();
        ms.insertMember(member);
        map.put("msg", "OK");
        return map;
    }
}
