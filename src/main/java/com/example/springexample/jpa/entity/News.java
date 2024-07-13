package com.example.springexample.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category title;

    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private Instant date = Instant.now();
}
