package com.example.springexample.jpa.controllers;

import com.example.springexample.jpa.dto.NewsDto;
import com.example.springexample.jpa.services.NewsCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsCRUDService newsCRUDService;

    public NewsController(NewsCRUDService newsCRUDService) {
        this.newsCRUDService = newsCRUDService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable long id) {
        try {
            NewsDto newsDto = newsCRUDService.getById(id);
            return ResponseEntity.ok(newsDto);
        } catch (NoSuchElementException e) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message:", "News not found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }

    @GetMapping
    public ConcurrentHashMap<Long, NewsDto> getAllNews() {
        return newsCRUDService.getAll();
    }

    @PostMapping
    public ResponseEntity<NewsDto> createNew(@RequestBody NewsDto newsDto) {
        long nextId = (newsCRUDService.getAll().isEmpty() ? 0 : newsCRUDService.getAll().size()) + 1L;
        newsDto.setId(nextId);
        newsCRUDService.create(newsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNew(@PathVariable long id, @RequestBody NewsDto newsDto) {
        if (!newsCRUDService.getAll().containsKey(id)) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message:", "News not found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
        newsDto.setId(id);
        newsCRUDService.update(newsDto);
        return ResponseEntity.ok(newsDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNew(@PathVariable long id) {
        if (!newsCRUDService.getAll().containsKey(id)) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message:", "News not found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
        newsCRUDService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
