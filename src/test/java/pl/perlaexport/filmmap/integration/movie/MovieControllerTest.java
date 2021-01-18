package pl.perlaexport.filmmap.integration.movie;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import pl.perlaexport.filmmap.integration.IntegrationBase;
import pl.perlaexport.filmmap.movie.dto.MovieDto;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MovieControllerTest extends IntegrationBase {

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
