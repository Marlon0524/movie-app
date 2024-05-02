package com.api.movie.controller;

import com.api.movie.model.ApiResponse;
import com.api.movie.model.Categories;
import com.api.movie.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoriesService categoriesService;

    @PostMapping
    public ResponseEntity<ApiResponse<Categories>> createCategories (@RequestBody Categories categories){
        return categoriesService.createCategories(categories);
    }
}
