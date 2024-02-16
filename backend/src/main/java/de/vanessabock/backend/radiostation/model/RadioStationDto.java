package de.vanessabock.backend.radiostation.model;

import java.util.Objects;

public class RadioStationDto {
    String name;
    String url;
    String homepage;
    String favicon;

    public RadioStationDto(String name, String url, String homepage, String favicon) {
        this.name = name;
        this.url = url;
        this.homepage = homepage;
        this.favicon = favicon;
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
        RadioStationDto that = (RadioStationDto) o;
        return Objects.equals(name, that.name) && Objects.equals(url, that.url) && Objects.equals(homepage, that.homepage) && Objects.equals(favicon, that.favicon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, homepage, favicon);
    }
}
