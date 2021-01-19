package pl.perlaexport.filmmap.integration.movie;

import org.junit.Test;
import org.springframework.http.MediaType;
import pl.perlaexport.filmmap.integration.IntegrationBase;
import pl.perlaexport.filmmap.movie.dto.MovieDto;
import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MovieListControllerTest extends IntegrationBase {

    @Test
    @Transactional
    public void getFavouritesShouldReturnListOfFilms() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(get("/movie/favourites?limit=3&page=1")
                .header("Authorization", "Bearer " + token))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void addingMovieToFavourites() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(post("/movie/favourites/add/{movieId}", 11)
                .header("Authorization", "Bearer " + token))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void addingMovieToFavouritesThatNotExist() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(post("/movie/favourites/add/{movieId}", 131)
                .header("Authorization", "Bearer " + token))
                .andExpect(jsonPath("$.message", is("Movie with id: 131 not found!")))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @Transactional
    public void deletingMovieFromFavourites() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(delete("/movie/favourites/delete/{movieId}", 11)
                .header("Authorization", "Bearer " + token))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void deletingMovieFromFavouritesThatNotExist() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(delete("/movie/favourites/delete/{movieId}", 131)
                .header("Authorization", "Bearer " + token))
                .andExpect(jsonPath("$.message", is("Movie with id: 131 not found!")))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @Transactional
    public void getWatchLaterShouldReturnListOfFilms() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(get("/movie/watch_later?limit=3&page=1")
                .header("Authorization", "Bearer " + token))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void addingMovieToWatchLater() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(post("/movie/watch_later/add/{movieId}", 11)
                .header("Authorization", "Bearer " + token))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void addingMovieToWatchLateThatNotExist() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(post("/movie/watch_later/add/{movieId}", 131)
                .header("Authorization", "Bearer " + token))
                .andExpect(jsonPath("$.message", is("Movie with id: 131 not found!")))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @Transactional
    public void deletingMovieToWatchLater() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(delete("/movie/watch_later/delete/{movieId}", 11)
                .header("Authorization", "Bearer " + token))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void deletingMovieFromWatchLaterThatNotExist() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(delete("/movie/watch_later/delete/{movieId}", 131)
                .header("Authorization", "Bearer " + token))
                .andExpect(jsonPath("$.message", is("Movie with id: 131 not found!")))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @Transactional
    public void getRatedMoviesShouldReturnListOfFilms() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(get("/movie/rated?limit=3&page=1")
                .header("Authorization", "Bearer " + token))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
