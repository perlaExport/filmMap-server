package pl.perlaexport.filmmap.integration.movie;

import org.junit.Test;
import org.springframework.http.MediaType;
import pl.perlaexport.filmmap.integration.IntegrationBase;
import pl.perlaexport.filmmap.movie.dto.MovieDto;
import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MovieControllerTest extends IntegrationBase {

    @Test
    @Transactional
    public void givenMovieShouldAddToDataBase() throws Exception {
        MovieDto movieDto = new MovieDto("123321", "test", "/test", List.of("test","test"));

        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(post("/movie/add")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(movieDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void givenMovieThatExistShouldRetrurnStatus409() throws Exception {
        MovieDto movieDto = new MovieDto("15", "test", "/test", List.of("test","test"));

        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(get("/movie/add")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(movieDto)))
                .andExpect(jsonPath("$.message", is("Movie with id: 15 already exists")))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    @Transactional
    public void gettingMovieThatExistInDataBase() throws Exception {
        String id = "15";

        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(get("/movie/" + id)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void gettingMovieThatNotExistInDataBaseShouldReturn() throws Exception {
        String id = "14315134";

        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(get("/movie/" + id)
                .header("Authorization", "Bearer " + token))
                .andExpect(jsonPath("$.message", is("Movie with id: 14315134 not found!")))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @Transactional
    public void givenLoggedUserShouldReciveRandomMovieFromDataBase() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(get("/movie/random")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
