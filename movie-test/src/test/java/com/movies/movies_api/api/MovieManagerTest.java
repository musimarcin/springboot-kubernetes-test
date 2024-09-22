package com.movies.movies_api.api;

import com.movies.movies_api.data.MovieRepo;
import com.movies.movies_api.data.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
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
import org.testcontainers.utility.DockerImageName;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//mock mvc for testing without server as if handling with real http
@AutoConfigureMockMvc
//one method to set container
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:14-alpine:///testdb"
})
//second method to set container (with @Container and @DynamicPropertySource)
@Testcontainers
class MovieManagerTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14-alpine"));
    @DynamicPropertySource
    static void propertiesSource(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        propertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mvc;

    @Autowired
    MovieRepo movieRepo;

    private List<Movie> movieList;
    @BeforeEach
    void addTestData() {
        movieRepo.deleteAllInBatch();
        movieList = new ArrayList<>();

        for (int i = 0; i < 30; i++)
            movieList.add(new Movie(null, "Movie " + i, 1988 + i, Instant.now()));

        movieRepo.saveAll(movieList);
    }
    //parameterized test with given inputs
    @ParameterizedTest
    @CsvSource({
            "1,10,3,0,true",
            "3,10,3,2,false"
    })
    void testGetMovies(int pageNo, int totalElements, int totalPages, int currentPage, boolean hasNext) throws Exception {
        mvc.perform(get("/api/movies?page="+pageNo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", equalTo(totalElements)))
                .andExpect(jsonPath("$.totalPages", equalTo(totalPages)))
                .andExpect(jsonPath("$.currentPage", equalTo(currentPage)))
                .andExpect(jsonPath("$.hasNext", equalTo(hasNext)));
    }

}