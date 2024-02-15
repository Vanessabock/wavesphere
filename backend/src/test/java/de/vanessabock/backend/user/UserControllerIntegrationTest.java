package de.vanessabock.backend.user;

import de.vanessabock.backend.radiostation.repository.RadioStationRepo;
import de.vanessabock.backend.user.model.User;
import de.vanessabock.backend.user.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

    @Autowired
    private RadioStationRepo radioStationRepo;

    @Test
    void getLoggedInUserTest_shouldReturnUser_whenUserLoggedIn() throws Exception {
        //GIVEN
        userRepo.save(new User("123456", "123456", "user", new ArrayList<>()));

        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
                        .with(oidcLogin().userInfoToken(token -> token.claim("login", "user").claim("id", 123456))))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id":"123456",
                            "githubId": "123456",
                            "name":"user",
                            "favouriteStations":[]
                        }
                        """))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void updateUserTest_shouldUpdatedUser_whenUserIsUpdated() throws Exception {
        //GIVEN
        userRepo.save(new User("123456", "123456", "user", new ArrayList<>()));

        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                                                         
                        {
                            "id":"123456",
                            "githubId":"123456",
                            "name":"user",
                            "favouriteStations":[{
                                "stationuuid": "962cc6df-0601-11e8-ae97-52543be04c81",
                                "name": "Dance Wave!",
                                "url": "https://dancewave.online/dance.mp3",
                                "homepage": "https://dancewave.online/",
                                "favicon": "https://dancewave.online/dw_logo.png"
                                }]
                        }
                                        """))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id":"123456",
                            "githubId":"123456",
                            "name":"user",
                            "favouriteStations":[{
                                "stationuuid": "962cc6df-0601-11e8-ae97-52543be04c81",
                                "name": "Dance Wave!",
                                "url": "https://dancewave.online/dance.mp3",
                                "homepage": "https://dancewave.online/",
                                "favicon": "https://dancewave.online/dw_logo.png"
                                }]
                        }
                        """))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}
