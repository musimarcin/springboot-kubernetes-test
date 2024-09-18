package com.movies.movies_api.manager;


import com.movies.movies_api.data.MovieDTO;
import com.movies.movies_api.data.entity.Movie;
import com.movies.movies_api.data.MovieRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepo movieRepo;

    @Transactional(readOnly = true)
    public MovieDTO getMovies(Integer year) {
        Pageable pageable = PageRequest.of(year, 10, Sort.Direction.ASC, "year");
        return new MovieDTO(movieRepo.findAll(pageable));
    }

    public Movie save(Movie movie) {
        return movieRepo.save(movie);
    }

    public void filltest() {
        //save(new Movie("Movie Test", 1999));
        //save(new Movie("Movie Test2", 2001));
        //save(new Movie("Movie Test3", 2222));
    }
}
