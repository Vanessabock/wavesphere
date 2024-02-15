package de.vanessabock.backend.station;

import de.vanessabock.backend.radiostation.model.RadioStation;
import de.vanessabock.backend.radiostation.repository.RadioStationRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RadioStationControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RadioStationRepo radioStationRepo;

    @Test
    void getStationsByLimitTest_WhenLimit_ThenReturnLimitResults() throws Exception {
        //GIVEN
        RadioStation radioStation = new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon");
        radioStationRepo.save(radioStation);

        //WHEN
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/stations/getStations/1"))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
                             "id": "1234",
                             "name": "Radio",
                             "url": "www.radio.mp3",
                             "homepage": "www.radio.com",
                             "favicon": "icon"
                         }]
                        """))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}

