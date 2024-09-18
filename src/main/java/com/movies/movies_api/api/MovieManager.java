package com.movies.movies_api.api;

import com.movies.movies_api.data.MovieDTO;
import com.movies.movies_api.data.entity.Movie;
import com.movies.movies_api.manager.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieManager {
    private final MovieService movieService;

    public MovieDTO getMovies(@RequestParam(name = "year", defaultValue = "1999") Integer year) {
        return movieService.getMovies(year);
    }


}
