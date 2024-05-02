package com.api.movie.controller;

import com.api.movie.model.ApiResponse;
import com.api.movie.model.Movies;
import com.api.movie.repository.MoviesRepository;
import com.api.movie.service.MoviesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MoviesController {
    private final MoviesService moviesService;
    private final MoviesRepository moviesRepository;
    private static final Logger logger = Logger.getLogger(MoviesController.class.getName());

    @PostMapping
    public ResponseEntity<ApiResponse<Movies>> createMovies(@RequestBody Movies movies) {
        return moviesService.createMovies(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Movies>> getMoviesById(@PathVariable Integer id) {
        return moviesService.getMoviesId(id);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Movies>>> getAllMovies(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        logger.info("Llamada al endpoint GET /api recibida.");
        Page<Movies> moviesPage = moviesService.getAllMovies(PageRequest.of(page, size));

        if (moviesPage != null && moviesPage.hasContent()) {
            ApiResponse<Page<Movies>> response = new ApiResponse<>(moviesPage, "success", "Peliculas obtenidos satisfactoriamente");
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Page<Movies>> response = new ApiResponse<>(null, "error", "No se encontraron peliculas");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @GetMapping("/filters")
    public ResponseEntity<ApiResponse<List<Movies>>> getMoviesByFilters(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer punctuation,
            @RequestParam(required = false) Integer duration) {

        List<Movies> movies = moviesRepository.findMoviesByFilters(categoryId, punctuation, duration);

        if (movies.isEmpty()) {
            // Si la lista de libros está vacía, devuelve un ResponseEntity con un mensaje apropiado
            ApiResponse<List<Movies>> response = new ApiResponse<>(null, "error", "No se encontraron peliculas con los filtros proporcionados");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            // Si se encontraron libros, devuelve un ResponseEntity con los libros y un mensaje de éxito
            ApiResponse<List<Movies>> response = new ApiResponse<>(movies, "success", "Peliculas obtenidas satisfactoriamente");
            return ResponseEntity.ok().body(response);
        }
    }



}
