package com.api.movie.repository;

import com.api.movie.model.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Integer> {
    @Query(value = "SELECT * FROM moviesdb.movies m " +
            "WHERE " +
            "    (m.category_id = ?1 OR ?1 IS NULL) AND " +
            "    (m.punctuation = ?2 OR ?2 IS NULL) AND " +
            "    (m.duration <= ?3 OR ?3 IS NULL)" +
            "LIMIT 0, 1000;", nativeQuery = true)
    List<Movies> findMoviesByFilters(
    Integer categoryId, Integer punctuation, Integer duration);
}
