package de.vanessabock.backend.radioapi.model;

public class Station {
    String stationuuid;
    String name;
    String url;
    String homepage;
    String favicon;
    String countrycode;
    int votes;

    public Station(String stationuuid, String name, String url, String homepage, String favicon, String countrycode, int votes) {
        this.stationuuid = stationuuid;
        this.name = name;
        this.url = url;
        this.homepage = homepage;
        this.favicon = favicon;
        this.countrycode = countrycode;
        this.votes = votes;
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

    public String getCountrycode() {
        return countrycode;
    }

    public int getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return "Station{" +
                "stationuuid='" + stationuuid + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", homepage='" + homepage + '\'' +
                ", favicon='" + favicon + '\'' +
                ", countrycode='" + countrycode + '\'' +
                ", votes=" + votes +
                '}';
    }
}
