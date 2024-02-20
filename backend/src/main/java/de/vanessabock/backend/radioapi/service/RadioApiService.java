package de.vanessabock.backend.radioapi.service;

import de.vanessabock.backend.exceptions.NoSuchStationException;
import de.vanessabock.backend.radiostation.model.RadioStation;
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

    public List<RadioStation> getStationsOrderedByVotes(String limit) {
        RadioStation[] response;

        response = restClient.get()
                .uri("/search?order=votes&reverse=true&limit=" + limit)
                .retrieve()
                .body(RadioStation[].class);

        if (response == null){
            return new ArrayList<>();
        }

        return Arrays.asList(response);
    }

    public List<RadioStation> getStationsBySearchName(String limit, String search) throws NoSuchStationException {
        RadioStation[] response;

        response = restClient.get()
                .uri("/search?order=votes&reverse=true&limit=" + limit + "&tag=&name=" + search)
                .retrieve()
                .body(RadioStation[].class);

        if (response == null){
            throw new NoSuchStationException("No stations found with name " + search + ".");
        }

        return Arrays.asList(response);
    }
}
