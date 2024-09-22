package com.movies.movies_api.api;

import com.movies.movies_api.data.MoviesDTO;
import com.movies.movies_api.manager.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieManager {
    private final MovieService movieService;

    @GetMapping
    public MoviesDTO getMovies(@RequestParam(name = "page", defaultValue = "1") Integer page,
                               @RequestParam(name = "query", defaultValue = "") String query) {
        if (query == null || query.length() == 0)
            return movieService.getMovies(page);
        else return movieService.searchMovies(query, page);
    }


}
