package pl.perlaexport.filmmap.integration.rating;

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

public class RatingControllerTest extends IntegrationBase {

    @Test
    @Transactional
    public void deletingRatingMovieThatUserRateShouldReturnStatus400() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(delete("/movie/{id}/delete_rate",11)
                .header("Authorization", "Bearer " + token))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void deletingRatingMovieThatUserNotRateShouldReturnStatus404() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(delete("/movie/{id}/delete_rate",113211)
                .header("Authorization", "Bearer " + token))
                .andExpect(jsonPath("$.message", is("Movie with id: 113211 not found!")))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @Transactional
    public void addingReviewToExistingMovie() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        ReviewDto reviewDto = new ReviewDto();

        mockMvc.perform(put("/movie/{id}/review",11)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(reviewDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void addingReviewToNotExistingMovie() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        ReviewDto reviewDto = new ReviewDto();

        mockMvc.perform(put("/movie/{id}/review",1214)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(reviewDto)))
                .andExpect(jsonPath("$.message", is("Movie with id: 1214 not found!")))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @Transactional
    public void getMovieReviews() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(get("/movie/{id}/reviews?limit=3&page=1",11)
                .header("Authorization", "Bearer " + token))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void getMovieReviewsThatNotExist() throws Exception {
        String token = authenticateUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        mockMvc.perform(get("/movie/{id}/reviews?limit=3&page=1",177)
                .header("Authorization", "Bearer " + token))
                .andExpect(jsonPath("$.message", is("Movie with id: 177 not found!")))
                .andExpect(redirectedUrl(null))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
