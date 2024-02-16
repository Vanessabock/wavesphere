package de.vanessabock.backend.radiostation.repository;

import de.vanessabock.backend.radiostation.model.RadioStation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RadioStationRepo extends MongoRepository<RadioStation, String> {
}
