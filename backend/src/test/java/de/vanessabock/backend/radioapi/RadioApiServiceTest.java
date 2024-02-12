package de.vanessabock.backend.radioapi;

import de.vanessabock.backend.radioapi.model.Station;
import de.vanessabock.backend.radioapi.service.RadioApiService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RadioApiServiceTest {
    private static MockWebServer mockWebServer;
    private static RadioApiService radioApiService;

    @BeforeAll
    public static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        radioApiService = new RadioApiService(mockWebServer.url("/").toString());
    }

    @AfterAll
    public static void cleanup() throws IOException {
        mockWebServer.shutdown();
    }

    @DirtiesContext
    @Test
    void getStationsOrderedByVotesTest_WhenCount_ThenReturnListOfCountStations() {
        //GIVEN
        mockWebServer.enqueue(new MockResponse().addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).setBody("""
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
        List<Station> actual = radioApiService.getStationsOrderedByVotes("2");

        //THEN
        assertEquals(List.of(new Station("78012206-1aa1-11e9-a80b-52543be04c81", "MANGORADIO", "http://stream.mangoradio.de/", "https://mangoradio.de/", ""),
                new Station("962cc6df-0601-11e8-ae97-52543be04c81", "Dance Wave!", "https://dancewave.online/dance.mp3", "https://dancewave.online/", "https://dancewave.online/dw_logo.png")).toString(),
                actual.toString());
    }

    @DirtiesContext
    @Test
    void getStationsOrderedByVotesTest_WhenResponseNull_ThenReturnEmptyList() {
        //GIVEN
        mockWebServer.enqueue(new MockResponse().addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).setBody(""));

        //WHEN
        List<Station> actual = radioApiService.getStationsOrderedByVotes("2");

        //THEN
        assertEquals(new ArrayList<>().toString(),
                actual.toString());
    }

    @DirtiesContext
    @Test
    void getStationsWithSearchNameTest_WhenCount_ThenReturnListOfCountStations() {
        //GIVEN
        mockWebServer.enqueue(new MockResponse().addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).setBody("""
                [
                    {
                        "changeuuid": "599cf698-a707-4d50-8383-e5f342fb9c08",
                        "stationuuid": "78012206-1aa1-11e9-a80b-52543be04c81",
                        "serveruuid": "a67a5b07-6189-4442-b5e8-0100f7f56804",
                        "name": "MANGORADIO",
                        "url": "http://stream.mangoradio.de/",
                        "url_resolved": "https://mangoradio.stream.laut.fm/mangoradio?t302=2024-02-12_04-33-31&uuid=39515d11-8574-4ef1-a652-ad6ec5098a06",
                        "homepage": "https://mangoradio.de/",
                        "favicon": "",
                        "tags": "mango,mangoradio,mongo,mongoradio,public radio,webradio",
                        "country": "Germany",
                        "countrycode": "DE",
                        "iso_3166_2": null,
                        "state": "",
                        "language": "german",
                        "languagecodes": "de",
                        "votes": 674402,
                        "lastchangetime": "2023-11-04 17:12:56",
                        "lastchangetime_iso8601": "2023-11-04T17:12:56Z",
                        "codec": "MP3",
                        "bitrate": 128,
                        "hls": 0,
                        "lastcheckok": 1,
                        "lastchecktime": "2024-02-12 04:36:08",
                        "lastchecktime_iso8601": "2024-02-12T04:36:08Z",
                        "lastcheckoktime": "2024-02-12 04:36:08",
                        "lastcheckoktime_iso8601": "2024-02-12T04:36:08Z",
                        "lastlocalchecktime": "2024-02-11 11:36:50",
                        "lastlocalchecktime_iso8601": "2024-02-11T11:36:50Z",
                        "clicktimestamp": "2024-02-12 06:34:40",
                        "clicktimestamp_iso8601": "2024-02-12T06:34:40Z",
                        "clickcount": 1142,
                        "clicktrend": 17,
                        "ssl_error": 0,
                        "geo_lat": null,
                        "geo_long": null,
                        "has_extended_info": false
                    },
                    {
                        "changeuuid": "0737afc6-f0db-4477-b250-74de4e936aaf",
                        "stationuuid": "01e06e88-d5e0-4657-bb1f-3821c9f56fee",
                        "serveruuid": "c53fc7d0-458a-4729-9247-a812f3c8787f",
                        "name": "Radio Mango",
                        "url": "https://bcovlive-a.akamaihd.net/19b535b7499a4719a5c19e043063f5d9/ap-southeast-1/6034685947001/playlist.m3u8",
                        "url_resolved": "https://bcovlive-a.akamaihd.net/19b535b7499a4719a5c19e043063f5d9/ap-southeast-1/6034685947001/playlist.m3u8",
                        "homepage": "https://www.radiomango.fm/",
                        "favicon": "",
                        "tags": "",
                        "country": "India",
                        "countrycode": "IN",
                        "iso_3166_2": null,
                        "state": "Kerala",
                        "language": "malayalam",
                        "languagecodes": "ml",
                        "votes": 538,
                        "lastchangetime": "2023-11-12 17:30:19",
                        "lastchangetime_iso8601": "2023-11-12T17:30:19Z",
                        "codec": "AAC",
                        "bitrate": 2113,
                        "hls": 1,
                        "lastcheckok": 1,
                        "lastchecktime": "2024-02-12 02:57:44",
                        "lastchecktime_iso8601": "2024-02-12T02:57:44Z",
                        "lastcheckoktime": "2024-02-12 02:57:44",
                        "lastcheckoktime_iso8601": "2024-02-12T02:57:44Z",
                        "lastlocalchecktime": "2024-02-12 02:57:44",
                        "lastlocalchecktime_iso8601": "2024-02-12T02:57:44Z",
                        "clicktimestamp": "2024-02-12 04:26:58",
                        "clicktimestamp_iso8601": "2024-02-12T04:26:58Z",
                        "clickcount": 39,
                        "clicktrend": -4,
                        "ssl_error": 0,
                        "geo_lat": 9.920493111997189,
                        "geo_long": 76.36596679687501,
                        "has_extended_info": false
                    }
                ]
                """));

        //WHEN
        List<Station> actual = radioApiService.getStationsWithSearchName("mango", "2");

        //THEN
        assertEquals(List.of(new Station("78012206-1aa1-11e9-a80b-52543be04c81", "MANGORADIO", "http://stream.mangoradio.de/", "https://mangoradio.de/", ""),
                        new Station("01e06e88-d5e0-4657-bb1f-3821c9f56fee", "Radio Mango", "https://bcovlive-a.akamaihd.net/19b535b7499a4719a5c19e043063f5d9/ap-southeast-1/6034685947001/playlist.m3u8", "https://www.radiomango.fm/", "")).toString(),
                actual.toString());
    }

    @DirtiesContext
    @Test
    void getStationsWithSearchNameTest_WhenResponseNull_ThenReturnEmptyList() {
        //GIVEN
        mockWebServer.enqueue(new MockResponse().addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).setBody(""));

        //WHEN
        List<Station> actual = radioApiService.getStationsWithSearchName("mango","2");

        //THEN
        assertEquals(new ArrayList<>().toString(),
                actual.toString());
    }
}
