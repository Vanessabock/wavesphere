package de.vanessabock.backend.user.model;

import de.vanessabock.backend.radioapi.model.Station;
import org.springframework.data.annotation.Id;

import java.util.List;

public record User(
        @Id
        String id,
        Long githubId,
        String name,
        List<Station> favouriteStations
) {
}
