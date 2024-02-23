package de.vanessabock.backend.radiostation.model;

import java.util.Objects;
import java.util.UUID;

public class RadioStation {
    String stationuuid;
    String name;
    String url_resolved;
    String homepage;
    String favicon;
    String tags;
    String country;

    public RadioStation(String stationuuid, String name, String url_resolved, String homepage, String favicon, String tags, String country) {
        this.stationuuid = stationuuid;
        this.name = name;
        this.url_resolved = url_resolved;
        this.homepage = homepage;
        this.favicon = favicon;
        this.tags = tags;
        this.country = country;
    }

    public RadioStation withNewUUID() {
        return new RadioStation(UUID.randomUUID().toString(),
                this.getName(), this.getUrl_resolved(), this.getHomepage(),
                this.getFavicon(), this.getTags(), this.getCountry());
    }

    public String getStationuuid() {
        return stationuuid;
    }

    public String getName() {
        return name;
    }

    public String getUrl_resolved() {
        return url_resolved;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getFavicon() {
        return favicon;
    }

    public String getTags() {
        return tags;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RadioStation that = (RadioStation) o;
        return Objects.equals(stationuuid, that.stationuuid) && Objects.equals(name, that.name) && Objects.equals(url_resolved, that.url_resolved) && Objects.equals(homepage, that.homepage) && Objects.equals(favicon, that.favicon) && Objects.equals(tags, that.tags) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationuuid, name, url_resolved, homepage, favicon, tags, country);
    }
}
