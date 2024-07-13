package com.example.springexample.jpa.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {

    private long id;
    private String title;
    private List<NewsDto> newsList;
}
