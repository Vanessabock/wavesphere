package de.vanessabock.backend.radiostation;

import de.vanessabock.backend.radiostation.model.RadioStation;
import de.vanessabock.backend.radiostation.repository.RadioStationRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RadioStationControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RadioStationRepo radioStationRepo;

    @Test
    void getStationsByLimitTest_WhenLimit1_ThenReturnListWith1Object() throws Exception {
        //GIVEN
        RadioStation radioStation = new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon");
        radioStationRepo.save(radioStation);

        //WHEN
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/stations/getStations/1"))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
                             "stationuuid": "1234",
                             "name": "Radio",
                             "url": "www.radio.mp3",
                             "homepage": "www.radio.com",
                             "favicon": "icon"
                         }]
                        """))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void getStationsBySearchNameWithLimitTest_WhenSearchNameBayAndLimit1_ThenReturnListWith1Object() throws Exception {
        //GIVEN
        RadioStation radioStation = new RadioStation("1234", "Bayern 3", "www.radio.mp3", "www.radio.com", "icon");
        radioStationRepo.save(radioStation);

        //WHEN
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/stations/getStationsByName/1?name=Bay"))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
                             "stationuuid": "1234",
                             "name": "Bayern 3",
                             "url": "www.radio.mp3",
                             "homepage": "www.radio.com",
                             "favicon": "icon"
                         }]
                        """))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void addStationTest() throws Exception {
        //GIVEN
        //WHEN
        mvc.perform(MockMvcRequestBuilders.post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                         {
                             "name": "Radio",
                             "url": "www.radio.mp3",
                             "homepage": "www.radio.com",
                             "favicon": "icon"
                         }
                         """)
                )
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""

                          {
                             "name": "Radio",
                             "url": "www.radio.mp3",
                             "homepage": "www.radio.com",
                             "favicon": "icon"
                          }
                        """))
                .andExpect(jsonPath("$.stationuuid").isNotEmpty());
    }
}

