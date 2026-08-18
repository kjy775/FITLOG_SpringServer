package com.fastcam.spserver.service;

import com.fastcam.spserver.entity.Member;
import com.fastcam.spserver.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    @Autowired
    MemberRepository mr;

    public Member getMemberById(String id) {
        Member member = mr.findById(id);
        return member;
    }

    public void insertMember(Member member) {
        mr.save(member);
    }

    public void updateMember(Member member) {
        Member oldMember = mr.findByNum(member.getNum());
        oldMember.setPass(member.getPass());
        oldMember.setName(member.getName());
        oldMember.setPhone(member.getPhone());
        oldMember.setProfileImg(member.getProfileImg());
        oldMember.setBirth(member.getBirth());

        oldMember.setHeight(member.getHeight());
        oldMember.setWeight(member.getWeight());

        oldMember.setZipNum(member.getZipNum());
        oldMember.setAdd1(member.getAdd1());
        oldMember.setAdd2(member.getAdd2());
        oldMember.setAdd3(member.getAdd3());
    }

    public void deleteMember(String id) {
        mr.deleteById(id);
    }
}
