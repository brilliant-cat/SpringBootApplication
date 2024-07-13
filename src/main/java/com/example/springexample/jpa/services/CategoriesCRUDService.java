package com.example.springexample.jpa.services;

import com.example.springexample.jpa.dto.CategoryDto;
import com.example.springexample.jpa.entity.Category;
import com.example.springexample.jpa.repositories.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoriesCRUDService implements CRUDService<CategoryDto> {

    private final CategoriesRepository categoriesRepository;

    @Override
    public CategoryDto getById(long id) {
        log.info("Get by ID: " + id);
        Category category = categoriesRepository.findById(id).orElseThrow(() -> new NoSuchElementException("News not found with id: " + id));
        return mapToDto(category);
    }

    @Override
    public ConcurrentHashMap<Long, CategoryDto> getAll() {
        log.info("Get All");
        List<Category> categoryList = categoriesRepository.findAll();
        ConcurrentHashMap<Long, CategoryDto> categoryDtoMap = new ConcurrentHashMap<>();
        for (Category category : categoryList) {
            categoryDtoMap.put(category.getId(), mapToDto(category));
        }
        return categoryDtoMap;
    }

    @Override
    public void create(CategoryDto categoryDto) {
        log.info("Created");
        categoriesRepository.save(mapToEntity(categoryDto));
    }

    @Override
    public void update(CategoryDto categoryDto) {
        log.info("Updated");
        categoriesRepository.save(mapToEntity(categoryDto));
    }

    @Override
    public void deleteById(long id) {
        log.info("Deleted: " + id);
        categoriesRepository.deleteById(id);
    }

    public static Category mapToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setTitle(categoryDto.getTitle());
        category.setNewsList(categoryDto.getNewsList()
                .stream()
                .map(NewsCRUDService::mapToEntity)
                .collect(Collectors.toList()));
        return category;
    }

    public static CategoryDto mapToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());
        categoryDto.setNewsList(category.getNewsList()
                .stream()
                .map(NewsCRUDService::mapToDto)
                .collect(Collectors.toList()));
        return categoryDto;
    }
}
