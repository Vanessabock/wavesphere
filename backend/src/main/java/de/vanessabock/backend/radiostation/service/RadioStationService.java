package de.vanessabock.backend.radiostation.service;

import de.vanessabock.backend.exception.NoSuchStationException;
import de.vanessabock.backend.exception.StationAlreadyInDatabaseException;
import de.vanessabock.backend.radiostation.model.RadioStation;
import de.vanessabock.backend.radiostation.repository.RadioStationRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RadioStationService {
    private final RadioStationRepo radioStationRepo;

    public RadioStationService(RadioStationRepo radioStationRepo) {
        this.radioStationRepo = radioStationRepo;
    }

    public List<RadioStation> getRadioStations(int limit) {
        return radioStationRepo.findAll().stream().limit(limit).toList();
    }

    public List<RadioStation> getRadioStationsBySearchName(int limit, String search) throws NoSuchStationException {
        List<RadioStation> result = radioStationRepo.findAll()
                .stream()
                .filter(station -> station.getName().toLowerCase().contains(search.toLowerCase()))
                .limit(limit)
                .toList();

        if (result.isEmpty()){
            throw new NoSuchStationException("No stations found with name " + search + ".");
        }

        return result;
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

    public List<String> getAllCountriesForFilter() {
        List<String> countryFilter = new ArrayList<>();
        radioStationRepo.findAll()
                .forEach(station -> {
                    if (!countryFilter.contains(station.getCountry()) && station.getCountry() != null && !station.getCountry().isEmpty()){
                        countryFilter.add(station.getCountry());
                    }
                });
        return countryFilter;
    }
}
