package com.api.movie.service;

import com.api.movie.model.ApiResponse;
import com.api.movie.model.Categories;
import com.api.movie.model.Movies;
import com.api.movie.repository.CategoriesRepository;
import com.api.movie.repository.MoviesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoviesService {
    private final MoviesRepository moviesRepository;
    private final CategoriesRepository categoriesRepository;

    public ResponseEntity<ApiResponse<Movies>> createMovies(Movies movies) {
        try {
            //Obtener el Id de la categoria desde la pelicula
            Integer categoryId = movies.getCategories().getCategory_id();

            //verificar si la categoria existe en la base de datos
            Optional<Categories> existingCategoriesOptional = categoriesRepository.findById(categoryId);
            if (existingCategoriesOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(null, "error", "categoria no encontrada con el ID:" + categoryId));
            }
            //obtener la instancia existente de category en la bd
            Categories existingCategories = existingCategoriesOptional.get();
            //Asignar la categoria existente a la pelicula
            movies.setCategories(existingCategories);
            //guardar la pelicula en la bd
            Movies newMovies = moviesRepository.save(movies);
            //construir el objeto apiresponse con la pelicula creada
            ApiResponse<Movies> response = new ApiResponse<>(newMovies, "success", "pelicula creada con éxito");
            //devolver el responseentity con el apiresponse
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            //manejar cualquier excepción que pueda ocurrir
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, "error", "error al crear la pelicula " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<Movies>> getMoviesId(@PathVariable Integer id) {
        try {
            Optional<Movies> optionalMovies = moviesRepository.findById(id);
            if (optionalMovies.isPresent()) {
                Movies movie = optionalMovies.get();
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(movie, "success", "Pelicula encontrada"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(null, "error", "pelicula no encontrada con el id: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, "error", "error al obtener la pelicula" + e.getMessage()));
        }
    }

    public Page<Movies> getAllMovies(Pageable pageable) {
        return moviesRepository.findAll(pageable);
    }

    public List<Movies> filterMovies(Integer categoryId, Integer punctuation, Integer duration) {
        return moviesRepository.findMoviesByFilters(categoryId,punctuation,duration);
    }
}
