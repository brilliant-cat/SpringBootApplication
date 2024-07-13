package com.example.springexample.jpa.dto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@Setter
public class NewsDto {

    private long id;
    private long categoryId;
    private String text;
    private Instant date = Instant.now();
}
