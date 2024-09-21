package com.movies.movies_api.data;

import com.movies.movies_api.data.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepo extends JpaRepository<Movie, Long> {

    //for getting only specified columns
    @Query("select new com.movies.movies_api.data.MovieDTO(m.id, m.title, m.releaseYear, m.addedWhen) from Movie m")
    Page<MovieDTO> findMovies(Pageable pageable);

}
