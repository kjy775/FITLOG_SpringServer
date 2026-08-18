package com.fastcam.spserver.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;

    @Column(nullable = false)
    private int cnum;

    @Column(nullable = false, length = 500)
    private String content;
}
