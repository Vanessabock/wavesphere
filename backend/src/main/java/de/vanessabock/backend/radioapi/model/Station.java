package de.vanessabock.backend.radioapi.model;

public class Station {
    String stationuuid;
    String name;
    String url;
    String homepage;
    String favicon;

    public Station(String stationuuid, String name, String url, String homepage, String favicon) {
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
    public String toString() {
        return "Station{" +
                "stationuuid='" + stationuuid + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", homepage='" + homepage + '\'' +
                ", favicon='" + favicon + '\'' +
                '}';
    }
}
