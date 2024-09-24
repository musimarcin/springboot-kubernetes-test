package com.movies.movies_api.data;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
public class MovieDTO {

    private Long id;
    private String title;
    private Integer releaseYear;
    private Instant addedWhen;
}
