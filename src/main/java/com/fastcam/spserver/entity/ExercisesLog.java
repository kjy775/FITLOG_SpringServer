package com.fastcam.spserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Data

public class ExercisesLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;

    private float weight;
    @Column(length = 100)
    private String exName;
    private int exerciseTime;
    private int calories;

    @CreationTimestamp
    @Column(columnDefinition = "datetime default now()")
    private Timestamp indate;

    @ManyToOne
    @JoinColumn(name = "mnum")
    private Member member;

}
