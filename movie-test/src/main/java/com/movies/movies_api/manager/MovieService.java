package com.movies.movies_api.manager;


import com.movies.movies_api.data.MovieDTO;
import com.movies.movies_api.data.MovieVM;
import com.movies.movies_api.data.MoviesDTO;
import com.movies.movies_api.data.MovieRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepo movieRepo;

    public Pageable getPage(Integer page) {
        int pageNo = page < 1 ? 0 : page - 1;
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.ASC, "releaseYear");
        return pageable;
    }
    @Transactional(readOnly = true)
    public MoviesDTO getMovies(Integer page) {
        Page<MovieDTO> moviePage = movieRepo.findMovies(getPage(page));
        return new MoviesDTO(moviePage);
    }

    @Transactional(readOnly = true)
    public MoviesDTO searchMovies(String query, Integer page) {
        Page<MovieDTO> moviePage = movieRepo.findByTitleContainingIgnoreCase(query, getPage(page));
        return new MoviesDTO(moviePage);
    }

}
