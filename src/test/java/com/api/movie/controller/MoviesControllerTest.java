package com.api.movie.controller;

import com.api.movie.model.ApiResponse;
import com.api.movie.model.Movies;
import com.api.movie.repository.MoviesRepository;
import com.api.movie.service.MoviesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoviesControllerTest {
    @Mock
    private MoviesService moviesService;

    @Mock
    private MoviesRepository moviesRepository;

    @InjectMocks
    private MoviesController moviesController;
    @Test
    void createMovies() {
        Movies movies = new Movies();
        ApiResponse<Movies> expectedResponse = new ApiResponse<>(movies, "success", "Pelicula creada satisfactoriamente");
        when(moviesService.createMovies(movies)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        ResponseEntity<ApiResponse<Movies>> actualResponse = moviesController.createMovies(movies);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    void getMoviesById() {
        Integer id = 1;
        Movies movies = new Movies();
        ApiResponse<Movies> expectedResponse = new ApiResponse<>(movies, "success", "Pelicula obtenida satisfactoriamente");
        when(moviesService.getMoviesId(id)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        ResponseEntity<ApiResponse<Movies>> actualResponse = moviesController.getMoviesById(id);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    void getAllMovies() {
        int page = 0;
        int size = 10;
        List<Movies> moviesList = Arrays.asList(new Movies(), new Movies());
        Page<Movies> moviesPage = new PageImpl<>(moviesList);
        ApiResponse<Page<Movies>> expectedResponse = new ApiResponse<>(moviesPage, "success", "Peliculas obtenidos satisfactoriamente");
        when(moviesService.getAllMovies(PageRequest.of(page, size))).thenReturn(moviesPage);

        ResponseEntity<ApiResponse<Page<Movies>>> actualResponse = moviesController.getAllMovies(page, size);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    void getMoviesByFilters() {
        Integer categoryId = 1;
        Integer punctuation = 5;
        Integer duration = 120;
        List<Movies> moviesList = Arrays.asList(new Movies(), new Movies());
        ApiResponse<List<Movies>> expectedResponse = new ApiResponse<>(moviesList, "success", "Peliculas obtenidas satisfactoriamente");
        when(moviesRepository.findMoviesByFilters(categoryId, punctuation, duration)).thenReturn(moviesList);

        ResponseEntity<ApiResponse<List<Movies>>> actualResponse = moviesController.getMoviesByFilters(categoryId, punctuation, duration);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
    }
}