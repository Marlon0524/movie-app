package com.api.movie.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Movies {
    @Id
    @GeneratedValue
    private Integer movie_id;
    @Size(max = 100, message = "El titulo no puede tener más de 100 caracteres")
    private String title;
    @Size(max = 200, message = "La descripción no puede tener más de 200 caracteres")
    private String description;
    private Integer duration;
    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 5, message = "La puntuación máxima es 5")
    private Integer punctuation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories categories;
}
