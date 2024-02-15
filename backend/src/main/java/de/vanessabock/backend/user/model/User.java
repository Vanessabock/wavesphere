package de.vanessabock.backend.user.model;

import de.vanessabock.backend.radioapi.model.ApiStation;
import de.vanessabock.backend.station.model.RadioStation;
import org.springframework.data.annotation.Id;

import java.util.List;

public record User(
        @Id
        String id,
        String githubId,
        String name,
        List<RadioStation> favouriteStations
) {
}
