package com.fastcam.spserver.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MemberDto {
    private int num;
    private String id;
    private String pass;
    private String name;
    private LocalDate birth;
    private String phone;
    private String zipNum;
    private String add1;
    private String add2;
    private String add3;
    private String height;
    private String weight;
    private String gender;
    private String profileImg;
    private String provider;
    private List<String> role_names;

}

