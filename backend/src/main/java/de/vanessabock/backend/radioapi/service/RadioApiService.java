package de.vanessabock.backend.radioapi.service;

import de.vanessabock.backend.radioapi.model.Station;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
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

        if (response == null){
            return new ArrayList<>();
        }

        return Arrays.asList(response);
    }

    public List<Station> getStationsWithSearchName(String count, String search) {
        Station[] response;

        response = restClient.get()
                .uri("/search?order=votes&reverse=true&limit=" + count + "&tag=&name=" + search)
                .retrieve()
                .body(Station[].class);

        if (response == null){
            return new ArrayList<>();
        }

        return Arrays.asList(response);
    }
}
