package com.movies.movies_api.api;

import com.movies.movies_api.data.MovieRepo;
import com.movies.movies_api.data.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class MovieManagerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    MovieRepo movieRepo;

    @BeforeEach
    void setUp() {
        movieRepo.deleteAllInBatch();
        List<Movie> movieList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            movieList.add(new Movie(null, "Movie " + i + 1, 1988 + i, Instant.now()));
        }
        movieRepo.saveAll(movieList);
    }

    @Container
    private static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:9.6.8")
            .withDatabaseName("databasename");

    @DynamicPropertySource
    static void configureDataSource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    }

    //parameterized test with given inputs
    @ParameterizedTest
    @CsvSource({
            "1,10,0,true",
            "2,10,1,false"
    })
    void shouldGetMovies(int pageNo, int totalElements, int totalPages, int currentPage, boolean hasNext) throws Exception {
        mvc.perform(get("/api/movies?page="+pageNo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", equalTo(totalElements)))
                .andExpect(jsonPath("$.totalPages", equalTo(totalPages)))
                .andExpect(jsonPath("$.currentPage", equalTo(currentPage)))
                .andExpect(jsonPath("$.hasNext", equalTo(hasNext)));
    }

}