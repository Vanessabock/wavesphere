package de.vanessabock.backend.station.model;

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

    @Override
    public String toString() {
        return "Station{" +
                "stationuuid='" + stationuuid + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", homepage='" + homepage + '\'' +
                ", favicon='" + favicon + '\'' +
                '}';
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
}
