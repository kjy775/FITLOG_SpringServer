package com.fastcam.spserver.service;

import com.fastcam.spserver.entity.Community;
import com.fastcam.spserver.entity.Follow;
import com.fastcam.spserver.entity.Member;
import com.fastcam.spserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

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

    public Member updateKakaoInfo(Member member) {
        Member old = mr.findByNum(member.getNum());
        if (old == null) return null;

        old.setGender(member.getGender());
        old.setBirth(member.getBirth());
        old.setHeight(member.getHeight());
        old.setWeight(member.getWeight());

        return old;
    }

    @Autowired
    ReplyRepository rr;

    @Autowired
    CommunityRepository cr;

    @Autowired
    LikeRepository lr;

    @Transactional
    public void deleteMember(String id) {
        Member member = mr.findById(id);

        if (member == null) {
            throw new RuntimeException("존재하지 않는 회원입니다.");
        }

        int mnum = member.getNum();

        List<Community> posts = cr.findByMemberNum(mnum);

        for (Community community : posts) {
            int cnum = community.getNum();

            rr.deleteByCommunityNum(cnum);
            lr.deleteByCommunityNum(cnum);
        }

        rr.deleteByMemberNum(mnum);
        lr.deleteByMemberNum(mnum);
        cr.deleteByMemberNum(mnum);
        fr.deleteByFfrom(mnum);
        fr.deleteByFto(mnum);

        mr.delete(member);
    }

    @Autowired
    FollowRepository fr;

    public List<Follow> getFollowings(int ffrom) {
        List<Follow> list = fr.findByFfrom(ffrom);
        return list;
    }

    public List<Follow> getFollowers(int fto) {
        List<Follow> list = fr.findByFto(fto);
        return list;
    }

    public void onFollow(Follow follow) {
        fr.save(follow);
    }
}
