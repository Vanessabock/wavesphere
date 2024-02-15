package de.vanessabock.backend.radioapi.service;

import de.vanessabock.backend.radioapi.model.ApiStation;
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

    public List<ApiStation> getStationsOrderedByVotes(String count) {
        ApiStation[] response;

        response = restClient.get()
                .uri("/search?order=votes&reverse=true&limit=" + count)
                .retrieve()
                .body(ApiStation[].class);

        if (response == null){
            return new ArrayList<>();
        }

        return Arrays.asList(response);
    }

    public List<ApiStation> getStationsBySearchName(String count, String search) {
        ApiStation[] response;

        response = restClient.get()
                .uri("/search?order=votes&reverse=true&limit=" + count + "&tag=&name=" + search)
                .retrieve()
                .body(ApiStation[].class);

        if (response == null){
            return new ArrayList<>();
        }

        return Arrays.asList(response);
    }
}
