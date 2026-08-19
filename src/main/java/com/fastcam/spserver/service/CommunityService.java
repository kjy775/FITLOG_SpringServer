package com.fastcam.spserver.service;

import com.fastcam.spserver.entity.*;
import com.fastcam.spserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommunityService {
    @Autowired
    CommunityRepository cr;

    @Autowired
    MemberRepository mr;

    public int writePost(Community community, int mnum) {
        Member member = mr.findByNum(mnum);
        community.setMember(member);
        Community insertCommunity = cr.save(community);
        return insertCommunity.getNum();
    }

    @Autowired
    LikeRepository lr;

    public void addLike(Likes likes) {
        Likes findLike = lr.findByLikeAndMemberNumAndCommunityNum(
                likes.getMember().getNum(),
                likes.getCommunity().getNum());
        if(findLike == null) lr.save(likes);
        else lr.delete(findLike);
    }


    public ArrayList<Likes> getLikeList(int num) {
        ArrayList<Likes> list = lr.findByNum(num);
        return list;
    }

    @Autowired
    ReplyRepository rr;

    public void writeReply(Reply reply) {
        Member member = mr.findByNum(reply.getMember().getNum());
        reply.setMember(member);
        rr.save(reply);
    }

    public void deleteReply(int num) {
        Optional<Reply> result = rr.findByNum(num);
        if(result.isPresent()) {
            Reply reply = result.get();
            rr.delete(reply);
        }
    }

    public Object getReplyList(int num) {
        List<Reply> list = rr.findByNumOrderByIdDesc(num);
        return list;
    }

    public List<Community> getUserPost(int mnum) {
        List<Community> list = cr.findByMemberNum(mnum);
        return list;
    }

    public List<Community> getPostList() {
        return cr.findAll();
    }

    @Autowired
    FollowRepository fr;

    public List<Community> getFollowingsPost(int mnum) {
        List<Follow> followList = fr.findByFfrom(mnum);
        List<Integer> mnums = new ArrayList<>();
        for (Follow f : followList) {
            mnums.add(f.getFto());
        }
        return cr.findByMemberNumIn(mnums);
    }

    @Autowired
    ReportRepository repr;

    public void reportPost(Report report) {
        repr.save(report);
    }
}
