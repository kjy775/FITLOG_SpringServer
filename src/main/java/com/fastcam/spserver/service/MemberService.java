package com.fastcam.spserver.service;

import com.fastcam.spserver.dto.MemberDto;
import com.fastcam.spserver.entity.Community;
import com.fastcam.spserver.entity.Follow;
import com.fastcam.spserver.entity.Member;
import com.fastcam.spserver.entity.MemberRole;
import com.fastcam.spserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    @Autowired
    MemberRepository mr;

    @Autowired
    MemberRoleRepository mrr;

    public Member getMemberById(String id) {
        Member member = mr.findById(id);
        return member;
    }

    public List<MemberRole> getMemberRole(int mnum) {
        return mrr.findByMemberNum(mnum);
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

        old.setId(member.getId());
        old.setPass(member.getPass());
        old.setName(member.getName());
        old.setPhone(member.getPhone());
        old.setProfileImg(member.getProfileImg());

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
        mrr.deleteByMemberNum(mnum);
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
        Optional<Follow> result =
                fr.findByFfromAndFto(follow.getFfrom(), follow.getFto());

        if (result.isPresent()) {
            fr.delete(result.get());
        } else {
            fr.save(follow);
        }
    }

    public String findId(String name, String phone) {
        Member member = mr.findByNameAndPhone(name, phone);
        if(member == null) return null;
        return member.getId();
    }

    public int checkUser(String id, String name, String phone) {
        return mr.countByIdAndNameAndPhone(id, name, phone);
    }

    public int resetPass(Member member) {
        Member oldMember = mr.findById(member.getId());
        if(oldMember == null) return 0;
        oldMember.setPass(member.getPass());
        return 1;
    }

    public Member getMemberByNum(int num) {
        return mr.findByNum(num);
    }

    public void insertMemberRole(Member mdto) {
        MemberRole memberRole = new MemberRole();
        memberRole.setMember(mdto);
        memberRole.setRoleName("member");

        mrr.save(memberRole);
    }

    public Member getMemberBySnsid(String id) {
        return mr.findBySnsid(id);
    }
}
