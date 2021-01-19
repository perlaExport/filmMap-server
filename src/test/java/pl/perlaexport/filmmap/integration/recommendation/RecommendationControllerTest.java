package pl.perlaexport.filmmap.integration.recommendation;

import org.junit.Test;
import org.springframework.http.MediaType;
import pl.perlaexport.filmmap.integration.IntegrationBase;
import pl.perlaexport.filmmap.rating.dto.ReviewDto;
import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecommendationControllerTest extends IntegrationBase {

    @Test
    @Transactional
    public void getTopFiveRecommendation() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(get("/movie/recommendation")
                .header("Authorization", "Bearer " + token))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void getRecommendationForMovie() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(get("/movie/{id}/recommendation", 769)
                .header("Authorization", "Bearer " + token))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void getRecommendationForMovieThatWasAlreadyRated() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(get("/movie/{id}/recommendation", 11)
                .header("Authorization", "Bearer " + token))
                .andExpect(jsonPath("$.message", is("Movie with id: 11 is already rated by user")))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @Transactional
    public void getRecommendationForMovieThatNotExist() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(get("/movie/{id}/recommendation", 1214)
                .header("Authorization", "Bearer " + token))
                .andExpect(jsonPath("$.message", is("Movie with id: 1214 not found!")))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}