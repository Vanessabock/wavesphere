package de.vanessabock.backend.station.repository;

import de.vanessabock.backend.station.model.RadioStation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RadioStationRepo extends MongoRepository<RadioStation, String> {
}
