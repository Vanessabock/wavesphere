package de.vanessabock.backend.radioapi;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
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
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RadioApiIntegrationTest {

    private static MockWebServer mockWebServer;

    @Autowired
    public MockMvc mockMvc;

    @BeforeAll
    public static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @DynamicPropertySource
    public static void configureUrl(DynamicPropertyRegistry registry) {
        registry.add("app.radioStations.api.url", () -> mockWebServer.url("/").toString());
    }

    @AfterAll
    public static void cleanup() throws IOException {
        mockWebServer.shutdown();
    }

    @DirtiesContext
    @Test
    void getStationsOrderedByVotesTest_WhenCount_ThenReturnListOfCountStations() throws Exception {
        //GIVEN
        mockWebServer.enqueue(new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody("""
                        [
                            {
                                "changeuuid": "599cf698-a707-4d50-8383-e5f342fb9c08",
                                "stationuuid": "78012206-1aa1-11e9-a80b-52543be04c81",
                                "serveruuid": "a67a5b07-6189-4442-b5e8-0100f7f56804",
                                "name": "MANGORADIO",
                                "url": "http://stream.mangoradio.de/",
                                "url_resolved": "https://mangoradio.stream.laut.fm/mangoradio?t302=2024-02-06_03-57-04&uuid=fe4f7927-ca77-4f07-a326-bb2781c5f6dd",
                                "homepage": "https://mangoradio.de/",
                                "favicon": "",
                                "tags": "mango,mangoradio,mongo,mongoradio,public radio,webradio",
                                "country": "Germany",
                                "countrycode": "DE",
                                "iso_3166_2": null,
                                "state": "",
                                "language": "german",
                                "languagecodes": "de",
                                "votes": 673375,
                                "lastchangetime": "2023-11-04 17:12:56",
                                "lastchangetime_iso8601": "2023-11-04T17:12:56Z",
                                "codec": "MP3",
                                "bitrate": 128,
                                "hls": 0,
                                "lastcheckok": 1,
                                "lastchecktime": "2024-02-06 03:59:07",
                                "lastchecktime_iso8601": "2024-02-06T03:59:07Z",
                                "lastcheckoktime": "2024-02-06 03:59:07",
                                "lastcheckoktime_iso8601": "2024-02-06T03:59:07Z",
                                "lastlocalchecktime": "2024-02-05 11:06:28",
                                "lastlocalchecktime_iso8601": "2024-02-05T11:06:28Z",
                                "clicktimestamp": "2024-02-06 08:20:55",
                                "clicktimestamp_iso8601": "2024-02-06T08:20:55Z",
                                "clickcount": 1140,
                                "clicktrend": -13,
                                "ssl_error": 0,
                                "geo_lat": null,
                                "geo_long": null,
                                "has_extended_info": false
                            },
                            {
                                "changeuuid": "fd714cf7-8be8-448e-993e-212c0023b1cf",
                                "stationuuid": "962cc6df-0601-11e8-ae97-52543be04c81",
                                "serveruuid": "bf38aa73-690a-4da6-8ec7-58d7bd9307d3",
                                "name": "Dance Wave!",
                                "url": "https://dancewave.online/dance.mp3",
                                "url_resolved": "http://stream.dancewave.online:8080/dance.mp3",
                                "homepage": "https://dancewave.online/",
                                "favicon": "https://dancewave.online/dw_logo.png",
                                "tags": "club dance electronic house trance",
                                "country": "Hungary",
                                "countrycode": "HU",
                                "iso_3166_2": null,
                                "state": "",
                                "language": "english",
                                "languagecodes": "en",
                                "votes": 461199,
                                "lastchangetime": "2023-12-19 17:32:57",
                                "lastchangetime_iso8601": "2023-12-19T17:32:57Z",
                                "codec": "MP3",
                                "bitrate": 128,
                                "hls": 0,
                                "lastcheckok": 1,
                                "lastchecktime": "2024-02-06 00:21:39",
                                "lastchecktime_iso8601": "2024-02-06T00:21:39Z",
                                "lastcheckoktime": "2024-02-06 00:21:39",
                                "lastcheckoktime_iso8601": "2024-02-06T00:21:39Z",
                                "lastlocalchecktime": "2024-02-06 00:21:39",
                                "lastlocalchecktime_iso8601": "2024-02-06T00:21:39Z",
                                "clicktimestamp": "2024-02-06 08:15:57",
                                "clicktimestamp_iso8601": "2024-02-06T08:15:57Z",
                                "clickcount": 1352,
                                "clicktrend": 23,
                                "ssl_error": 0,
                                "geo_lat": null,
                                "geo_long": null,
                                "has_extended_info": true
                            }
                        ]
                                """));


        //WHEN
        MvcResult mvcResult = mockMvc.perform(get("/api/radioStations?count=2"))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                             {
                                 "stationuuid": "78012206-1aa1-11e9-a80b-52543be04c81",
                                 "name": "MANGORADIO",
                                 "url": "http://stream.mangoradio.de/",
                                 "homepage": "https://mangoradio.de/",
                                 "favicon": ""
                             },
                             {
                                 "stationuuid": "962cc6df-0601-11e8-ae97-52543be04c81",
                                 "name": "Dance Wave!",
                                 "url": "https://dancewave.online/dance.mp3",
                                 "homepage": "https://dancewave.online/",
                                 "favicon": "https://dancewave.online/dw_logo.png"
                             }
                         ]
                        """))
                .andReturn();


        assertEquals(200, mvcResult.getResponse().getStatus());
    }

}
