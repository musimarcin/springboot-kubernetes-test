package com.movies.movies_api.data;

import com.movies.movies_api.data.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepo extends JpaRepository<Movie, Long> {
}
