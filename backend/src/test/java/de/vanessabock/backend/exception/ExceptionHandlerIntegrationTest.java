package de.vanessabock.backend.exception;

import de.vanessabock.backend.radiostation.model.RadioStation;
import de.vanessabock.backend.radiostation.repository.RadioStationRepo;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionHandlerIntegrationTest {

    private static MockWebServer mockWebServer;

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private RadioStationRepo radioStationRepo;

    @BeforeAll
    public static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

    }

    @DynamicPropertySource
    public static void configureUrl(DynamicPropertyRegistry registry) {
        registry.add("app.radioStations.api.url", () -> mockWebServer.url("/").toString());
    }

    @DirtiesContext
    @Test
    void globalExceptionHandlerTest_StationNotFoundException_ApiRequest() throws Exception {
        //Given
        String notExistingName = "Bla";

        mockWebServer.enqueue(new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody(""));

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/radioStations/{search}?limit=10", notExistingName))

                //THEN
                .andExpect(status().is(404));
        }

    @DirtiesContext
    @Test
    void globalExceptionHandlerTest_StationNotFoundException_DatabaseRequest() throws Exception {
        //Given
        String notExistingName = "Bla";

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/stations/getStationsByName/10?name={search}", notExistingName))

                //THEN
                .andExpect(status().is(404));
    }

    @Test
    void globalExceptionHandlerTest_StationAlreadyInDatabaseException() throws Exception {
        //GIVEN
        RadioStation radioStation = new RadioStation("1234", "Radio", "www.radio.mp3", "www.radio.com", "icon");
        radioStationRepo.save(radioStation);
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                         {
                             "stationuuid": "1234",
                             "name": "Radio",
                             "url": "www.radio.mp3",
                             "homepage": "www.radio.com",
                             "favicon": "icon"
                         }
                         """)
                )
                //THEN
                .andExpect(status().isMethodNotAllowed());
    }

}
