package com.fastcam.spserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Entity
@Data
@DynamicInsert
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;
    @Column(nullable = false)
    private String id;
    private String pass;
    @Column(nullable = false)
    private String name;
    private Timestamp birth;
    private String phone;
    private String add1;
    private String add2;
    private String add3;
    private String height;
    private String weight;
    @Column(length = 500)
    private String profileImg;
    @Column(columnDefinition = "varchar(20) default 'LOCAL'")
    private String provider;
}
