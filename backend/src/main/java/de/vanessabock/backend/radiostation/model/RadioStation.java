package de.vanessabock.backend.radiostation.model;

import java.util.Objects;

public class RadioStation {
    String stationuuid;
    String name;
    String url;
    String homepage;
    String favicon;

    public RadioStation(String stationuuid, String name, String url, String homepage, String favicon) {
        this.stationuuid = stationuuid;
        this.name = name;
        this.url = url;
        this.homepage = homepage;
        this.favicon = favicon;
    }

    public String getStationuuid() {
        return stationuuid;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getFavicon() {
        return favicon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RadioStation that = (RadioStation) o;
        return Objects.equals(stationuuid, that.stationuuid) && Objects.equals(name, that.name) && Objects.equals(url, that.url) && Objects.equals(homepage, that.homepage) && Objects.equals(favicon, that.favicon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationuuid, name, url, homepage, favicon);
    }
}
