package de.vanessabock.backend.radiostation.service;

import de.vanessabock.backend.exception.NoSuchStationException;
import de.vanessabock.backend.exception.StationAlreadyInDatabaseException;
import de.vanessabock.backend.radiostation.model.RadioStation;
import de.vanessabock.backend.radiostation.repository.RadioStationRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class RadioStationService {
    private final RadioStationRepo radioStationRepo;

    public RadioStationService(RadioStationRepo radioStationRepo) {
        this.radioStationRepo = radioStationRepo;
    }

    public List<RadioStation> getStations(int limit, String name, String country, String tag) throws NoSuchStationException {
        List<RadioStation> result = radioStationRepo.findAll();

        if (!name.isEmpty()) {
            result = result.stream()
                    .filter(station -> station.getName().toLowerCase().contains(name.toLowerCase()))
                    .limit(limit)
                    .toList();
        }

        if (!country.isEmpty()) {
            result = result.stream().filter(station -> station.getCountry().equals(country)).toList();
        }

        if (!tag.isEmpty()) {
            result = result.stream().filter(station -> station.getTags().toLowerCase().contains(tag.toLowerCase())).toList();
        }

        result = result.stream().limit(limit).toList();

        if (result.isEmpty()){
            throw new NoSuchStationException("No stations found");
        }

        return result;
    }

    public List<String> getAllCountriesForFilter() {
        List<String> countryFilter = new ArrayList<>();
        radioStationRepo.findAll()
                .forEach(station -> {
                    if (!countryFilter.contains(station.getCountry()) && station.getCountry() != null && !station.getCountry().isEmpty()){
                        countryFilter.add(station.getCountry());
                    }
                });
        Collections.sort(countryFilter);
        countryFilter.addFirst("Show all");
        return countryFilter;
    }


    public RadioStation addRadioStation(RadioStation radioStation) throws StationAlreadyInDatabaseException {
        if (radioStation.getStationuuid().isEmpty()){
            return radioStationRepo.save(radioStation.withNewUUID());
        } else {
            if (radioStationRepo.existsRadioStationByStationuuid(radioStation.getStationuuid())){
                throw new StationAlreadyInDatabaseException("Station " + radioStation.getName() + " already exists in database.");
            }
            return radioStationRepo.save(radioStation);
        }
    }
}
