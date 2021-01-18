package pl.perlaexport.filmmap.unit.user.registration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.perlaexport.filmmap.user.model.AuthType;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.registration.controller.RegistrationController;
import pl.perlaexport.filmmap.user.registration.dto.RegistrationDto;
import pl.perlaexport.filmmap.user.registration.service.RegistrationService;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTests {
    @Mock
    RegistrationService registrationService;

    @InjectMocks
    RegistrationController controller;
    MockMvc mockMvc;
    MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void registerUserAccount() throws Exception {
        request.setServerName("server");
        request.setServerPort(1000);
        request.setContextPath("contextPath");
        UserEntity user = new UserEntity();
        user.setEmail("test@test.com");
        user.setName("name");
        user.setAuthType(AuthType.LOCAL);
        user.setId(1L);

        RegistrationDto dto = new RegistrationDto();
        dto.setName("name");
        dto.setEmail("test@test.com");
        dto.setPassword("password");
        dto.setMatchingPassword("password");


        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(dto)))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void registerUserAccountNotValidDto() throws Exception {
        request.setServerName("server");
        request.setServerPort(1000);
        request.setContextPath("contextPath");

        RegistrationDto dto = new RegistrationDto();
        dto.setName("name");
        dto.setEmail("test@test.com");
        //to short password
        dto.setPassword("pass");
        dto.setMatchingPassword("pass");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(dto)))
                .andExpect(status().isBadRequest());
    }
    private String asJson(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }

}
