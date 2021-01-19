package pl.perlaexport.filmmap.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.perlaexport.filmmap.movie.repository.MovieRepository;
import pl.perlaexport.filmmap.category.repository.CategoryRepository;
import pl.perlaexport.filmmap.rating.repository.RatingRepository;
import pl.perlaexport.filmmap.user.repository.UserRepository;
import pl.perlaexport.filmmap.security.jwt.TokenProvider;

import java.text.SimpleDateFormat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application.properties")
@AutoConfigureMockMvc
public abstract class IntegrationBase {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    protected TokenProvider tokenProvider;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MovieRepository movieRepository;

    @Autowired
    protected RatingRepository ratingRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    protected final ObjectMapper objectMapper = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    protected final String DEFAULT_USERNAME = "test1@test.com";

    protected final String DEFAULT_PASSWORD = "password";

    protected String asJson(Object o) throws JsonProcessingException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(df);
        String jsonString = mapper.writeValueAsString(o);
        return jsonString;
    }

    protected String authenticateUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        String token = tokenProvider.createToken(authentication);
        return token;
    }
}
