package com.fastcam.spserver.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;

    @Column(nullable = false)
    private int cnum;

    @ManyToOne
    @JoinColumn(name = "community_num")
    private Community community;

    @ManyToOne
    @JoinColumn(name = "mnum")
    private Member member;
}
