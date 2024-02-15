package de.vanessabock.backend.station.service;

import de.vanessabock.backend.station.model.RadioStation;
import de.vanessabock.backend.station.repository.RadioStationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RadioStationService {
    private final RadioStationRepo radioStationRepo;

    public RadioStationService(RadioStationRepo radioStationRepo) {
        this.radioStationRepo = radioStationRepo;
    }

    public List<RadioStation> getRadioStations(int limit){
        return radioStationRepo.findAll().subList(0, limit);
    }
}
