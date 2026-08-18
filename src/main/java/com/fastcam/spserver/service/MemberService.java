package com.fastcam.spserver.service;

import com.fastcam.spserver.entity.Member;
import com.fastcam.spserver.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    MemberRepository mr;

    public Member getMember(String id) {
        Member member = mr.findById(id);
        return member;
    }

    public void insertMember(Member member) {
        mr.save(member);
    }
}
