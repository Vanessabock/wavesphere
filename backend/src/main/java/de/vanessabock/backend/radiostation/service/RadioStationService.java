package de.vanessabock.backend.radiostation.service;

import de.vanessabock.backend.exceptions.NoSuchStationException;
import de.vanessabock.backend.radiostation.model.RadioStation;
import de.vanessabock.backend.radiostation.repository.RadioStationRepo;
import org.springframework.stereotype.Service;

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

    public RadioStation addRadioStation(RadioStation radioStation) {
        if (radioStation.getStationuuid().isEmpty()){
            return radioStationRepo.save(radioStation.withNewUUID());
        } else {
            return radioStationRepo.save(radioStation);
        }
    }
}
