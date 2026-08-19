package com.fastcam.spserver.controller;

import com.fastcam.spserver.entity.*;
import com.fastcam.spserver.service.CommunityService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

@RestController
@RequestMapping("/community")
public class CommunityController {
    @Autowired
    CommunityService cs;

    @PostMapping("/writePost")
    public HashMap<String, Object> writePost(@RequestBody Community community) {
        HashMap<String, Object> map = new HashMap<>();
        int mnum = community.getMember().getNum();
        int postid = cs.writePost(community, mnum);
        map.put("postid", postid);

        return map;
    }

    @Autowired
    ServletContext sc;

    @PostMapping("/fileupload")
    public HashMap<String, Object> fileupload(@RequestParam("image") MultipartFile file) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String path = sc.getRealPath("/community");
        Calendar today = Calendar.getInstance();
        long dt = today.getTimeInMillis();
        String filename = file.getOriginalFilename();
        String f1 = filename.substring(0, filename.lastIndexOf("."));
        String f2 = filename.substring(filename.lastIndexOf("."));
        String uploadPath = path + "/" + f1 + dt + f2;
        try {
            file.transferTo(new File(uploadPath));
            map.put("filename", f1 + dt + f2);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @PostMapping("/addLike")
    public HashMap<String, Object> addLike(@RequestBody Likes likes) {
        HashMap<String, Object> map = new HashMap<>();
        cs.addLike(likes);
        map.put("msg", "OK");
        return map;
    }

    @GetMapping("/getLikeList")
    public HashMap<String, Object> getLikeList(@RequestParam("num") int num) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("likeList", cs.getLikeList(num));
        return map;
    }

    @PostMapping("/writeReply")
    public HashMap<String, Object> writeReply(@RequestBody Reply reply) {
        HashMap<String, Object> map = new HashMap<>();
        cs.writeReply(reply);
        return map;
    }

    @DeleteMapping("/deleteReply/{num}")
    public HashMap<String, Object> deleteReply(@PathVariable("num") int num) {
        HashMap<String, Object> map = new HashMap<>();
        cs.deleteReply(num);
        return map;
    }

    @GetMapping("/getReplyList")
    public HashMap<String, Object> getReplyList(@RequestParam("num") int num) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("replyList", cs.getReplyList(num));
        return map;
    }

    @GetMapping("/userPost")
    public HashMap<String, Object> userPost(@RequestParam("mnum") int mnum) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("postList", cs.getUserPost(mnum));
        return map;
    }


    @GetMapping("/getPostList")
    public HashMap<String, Object> getPostList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("postList", cs.getPostList());
        return map;
    }

    @GetMapping("/followingPost")
    public HashMap<String, Object> followingsPost(@RequestParam("mnum") int mnum) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("postList", cs.getFollowingsPost(mnum));
        return map;
    }

    @PostMapping("/report")
    public HashMap<String, Object> reportPost(@RequestBody Report report) {
        HashMap<String, Object> map = new HashMap<>();
        cs.reportPost(report);
        map.put("msg", "OK");
        return map;
    }


}
