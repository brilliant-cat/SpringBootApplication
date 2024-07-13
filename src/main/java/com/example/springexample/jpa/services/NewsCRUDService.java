package com.example.springexample.jpa.services;

import com.example.springexample.jpa.dto.NewsDto;
import com.example.springexample.jpa.entity.Category;
import com.example.springexample.jpa.entity.News;
import com.example.springexample.jpa.repositories.CategoriesRepository;
import com.example.springexample.jpa.repositories.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
@Service
public class NewsCRUDService implements CRUDService<NewsDto>{

    private final NewsRepository newsRepository;
    private final CategoriesRepository categoriesRepository;

    @Override
    public NewsDto getById(long id) {
        log.info("Get by ID: " + id);
        News news = newsRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Categories not found with id: " + id));
        return mapToDto(news);
    }

    @Override
    public ConcurrentHashMap<Long, NewsDto> getAll() {
        log.info("Get All");
        List<News> newsList = newsRepository.findAll();
        ConcurrentHashMap<Long, NewsDto> newsDtoMap = new ConcurrentHashMap<>();
        for (News news : newsList) {
            newsDtoMap.put(news.getId(), mapToDto(news));
        }
        return newsDtoMap;
    }

    @Override
    public void create(NewsDto newsDto) {
        log.info("Created");
        News news = mapToEntity(newsDto);
        long categoryId = newsDto.getCategoryId();
        Category category = categoriesRepository.findById(categoryId).orElseThrow(() -> new NoSuchElementException("Categories not found with id: " + newsDto.getId()));
        news.setTitle(category);
        newsRepository.save(news);
    }

    @Override
    public void update(NewsDto newsDto) {
        log.info("Updated");
        News news = mapToEntity(newsDto);
        long categoryId = newsDto.getCategoryId();
        Category category = categoriesRepository.findById(categoryId).orElseThrow(() -> new NoSuchElementException("Categories not found with id: " + newsDto.getId()));
        news.setTitle(category);
        newsRepository.save(news);
    }

    @Override
    public void deleteById(long id) {
        log.info("Deleted: " + id);
        newsRepository.deleteById(id);
    }

    public static NewsDto mapToDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setText(news.getText());
        newsDto.setCategoryId(news.getTitle().getId());
        newsDto.setDate(news.getDate());
        return newsDto;
    }

    public static News mapToEntity(NewsDto newsDto) {
        News news = new News();
        news.setId(newsDto.getId());
        news.setText(newsDto.getText());
        news.setDate(newsDto.getDate());
        return news;
    }
}
