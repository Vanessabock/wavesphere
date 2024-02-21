package de.vanessabock.backend.radiostation;

import de.vanessabock.backend.radiostation.model.RadioStation;
import de.vanessabock.backend.radiostation.repository.RadioStationRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
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

    @DirtiesContext
    @Test
    void getStationsByLimitTest_WhenLimit1_ThenReturnListWith1Object() throws Exception {
        //GIVEN
        RadioStation radioStation = new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon", "music", "country");
        radioStationRepo.save(radioStation);

        //WHEN
        mvc.perform(MockMvcRequestBuilders.get("/api/stations/getStations/1"))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
                             "stationuuid": "1234",
                             "name": "Radio",
                             "url_resolved": "www.radio.mp3",
                             "homepage": "www.radio.com",
                             "favicon": "icon",
                             "tags": "music",
                             "country": "country"
                         }]
                        """));
    }

    @DirtiesContext
    @Test
    void getStationsBySearchNameWithLimitTest_WhenSearchNameBayAndLimit1_ThenReturnListWith1Object() throws Exception {
        //GIVEN
        RadioStation radioStation = new RadioStation("1234", "Bayern 3", "www.radio.mp3", "www.radio.com", "icon", "music", "country");
        radioStationRepo.save(radioStation);

        //WHEN
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/stations/getStationsByName/1?name=Bay"))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
                             "stationuuid": "1234",
                             "name": "Bayern 3",
                             "url_resolved": "www.radio.mp3",
                             "homepage": "www.radio.com",
                             "favicon": "icon",
                             "tags": "music",
                             "country": "country"
                         }]
                        """))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @DirtiesContext
    @Test
    void getAllCountriesForFilterTest_WhenStationsWith2DiffCountriesInDB_ThenReturnListWith2Countries() throws Exception {
        //GIVEN
        RadioStation germanRadioStation = new RadioStation("1234", "RadioGer", "www.radio.mp3", "www.radio.com", "icon", "music", "Germany");
        RadioStation spanishRadioStation = new RadioStation("1234", "RadioEs", "www.radio.mp3", "www.radio.com", "icon", "music", "Spain");
        radioStationRepo.save(germanRadioStation);
        radioStationRepo.save(spanishRadioStation);

        //WHEN
        mvc.perform(MockMvcRequestBuilders.get("/api/stations/getAllCountries"))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            "Show all",
                            "Germany",
                            "Spain"
                        ]
                        """));
    }

    @DirtiesContext
    @Test
    void addStationTest() throws Exception {
        //GIVEN
        //WHEN
        mvc.perform(MockMvcRequestBuilders.post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                         {
                             "stationuuid": "",
                             "name": "Radio",
                             "url_resolved": "www.radio.mp3",
                             "homepage": "www.radio.com",
                             "favicon": "icon",
                             "tags": "music",
                             "country": "country"
                         }
                         """)
                )
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""

                          {
                             "name": "Radio",
                             "url_resolved": "www.radio.mp3",
                             "homepage": "www.radio.com",
                             "favicon": "icon",
                             "tags": "music",
                             "country": "country"
                          }
                        """))
                .andExpect(jsonPath("$.stationuuid").isNotEmpty());
    }
}

