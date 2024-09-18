package com.movies.movies_api.manager;


import com.movies.movies_api.data.MovieDTO;
import com.movies.movies_api.data.entity.Movie;
import com.movies.movies_api.data.MovieRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepo movieRepo;

    @Transactional(readOnly = true)
    public MovieDTO getMovies(Integer page) {
        int pageNo = page > 1 ? 0 : page - 1;
        Pageable pageable = PageRequest.of(pageNo, 20, Sort.Direction.ASC, "releaseYear");
        return new MovieDTO(movieRepo.findAll(pageable));
    }

    //@EventListener(ApplicationStartedEvent.class)
    public void filltest() {
        for (int i = 0; i <= 30; i++) {
            movieRepo.save(new Movie(null, "Movie Test " + i, 1988 + i, Instant.now()));
        }
    }
}
