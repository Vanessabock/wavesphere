package de.vanessabock.backend.radioapi.service;

import de.vanessabock.backend.radioapi.model.Station;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Service
public class RadioApiService {
    private final RestClient restClient;

    public RadioApiService(@Value("${app.radioStations.api.url}") String url) {
        restClient = RestClient.builder()
                .baseUrl(url)
                .build();
    }

    public List<Station> getStationsOrderedByVotes(String count) {
        Station[] response;

        response = restClient.get()
                .uri("/search?order=votes&reverse=true&limit=" + count)
                .retrieve()
                .body(Station[].class);

        assert response != null;
        return Arrays.asList(response);
    }
}
