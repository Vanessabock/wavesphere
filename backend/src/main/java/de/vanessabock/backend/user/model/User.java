package de.vanessabock.backend.user.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public record User(
        @Id
        String id,
        int githubId,
        String name,
        List<String> favouriteStations
) {
}
