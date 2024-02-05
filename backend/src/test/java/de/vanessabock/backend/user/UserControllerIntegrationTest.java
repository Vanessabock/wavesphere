package de.vanessabock.backend.user;

import de.vanessabock.backend.user.model.User;
import de.vanessabock.backend.user.repository.UserRepo;
import de.vanessabock.backend.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    private final UserService userService = Mockito.mock(UserService.class);

    @Test
    void getLoggedInUserTest_shouldReturnUser_whenUserLoggedIn() throws Exception {
        //GIVEN
        userRepo.save(new User("123456", 123456, "user", new ArrayList<>()));

        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
                        .with(oidcLogin().userInfoToken(token -> token.claim("login", "user").claim("id", 123456))))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id":"123456",
                            "githubId":123456,
                            "name":"user",
                            "favouriteStations":[]
                        }
                        """))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}
