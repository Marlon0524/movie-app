package com.api.movie.service;

import com.api.movie.model.ApiResponse;
import com.api.movie.model.Categories;
import com.api.movie.model.Movies;
import com.api.movie.repository.CategoriesRepository;
import com.api.movie.repository.MoviesRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoviesServiceTest {
    @Mock
    private MoviesRepository moviesRepository;
    @Mock
    private CategoriesRepository categoriesRepository;
    @InjectMocks
    private MoviesService moviesService;

    @Test
    void createMovies() {
        // Arrange
        Movies inputMovies = new Movies();
        Categories categories = new Categories();
        categories.setCategory_id(1);
        inputMovies.setCategories(categories);

        when(categoriesRepository.findById(1)).thenReturn(Optional.of(categories));
        when(moviesRepository.save(inputMovies)).thenReturn(inputMovies);

        // Act
        ResponseEntity<ApiResponse<Movies>> responseEntity = moviesService.createMovies(inputMovies);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("pelicula creada con éxito", responseEntity.getBody().getMessage());
        assertEquals(inputMovies, responseEntity.getBody().getData());
    }


    @Test
    void getMoviesId() {
        // Arrange
        Movies expectedMovie = new Movies();
        when(moviesRepository.findById(1)).thenReturn(Optional.of(expectedMovie));

        // Act
        ResponseEntity<ApiResponse<Movies>> responseEntity = moviesService.getMoviesId(1);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("Pelicula encontrada", responseEntity.getBody().getMessage());
        assertEquals(expectedMovie, responseEntity.getBody().getData());
    }

    @Test
    void getAllMovies() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        List<Movies> moviesList = new ArrayList<>();
        Page<Movies> expectedPage = new PageImpl<>(moviesList);
        when(moviesRepository.findAll(pageable)).thenReturn(expectedPage);

        // Act
        Page<Movies> resultPage = moviesService.getAllMovies(pageable);

        // Assert
        assertEquals(expectedPage, resultPage);
    }

    @Test
    void filterMovies() {
        // Arrange
        List<Movies> expectedMoviesList = new ArrayList<>();
        when(moviesRepository.findMoviesByFilters(anyInt(), anyInt(), anyInt())).thenReturn(expectedMoviesList);

        // Act
        List<Movies> resultBooksList = moviesService.filterMovies(1, 2, 120);

        // Assert
        assertEquals(expectedMoviesList, resultBooksList);
    }
}