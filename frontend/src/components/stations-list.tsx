import React, {useEffect, useState} from "react";
import {Station} from "../types/Station.ts";
import axios from "axios";
import {StationCard} from "./station-card.tsx";
import {User} from "../types/User.ts";
import AddStationModal from "./add-station-modal.tsx";
import AddStationFromApiModal from "./add-station-from-api-modal.tsx";

type StationsListProps = {
    user: User
    nowPlaying: Station
    showFavourites: boolean
    updateUser: (user: User) => void
    togglePlayPause: (station: Station) => void;
}

export const StationsList: React.FC<StationsListProps> = ({
                                                              user,
                                                              nowPlaying,
                                                              showFavourites,
                                                              updateUser,
                                                              togglePlayPause
                                                          }) => {
    const [stations, setStations] = useState<Station[]>([])
    const [favourites, setFavourites] = useState<Station[]>([]);
    const [limit, setLimit] = useState<number>(20)
    const [search, setSearch] = useState<string>("")
    const [countryFilterElems, setCountryFilterElems] = useState<string[]>([]);
    const [country, setCountry] = useState<String>("")

    useEffect(() => {
        if (search) {
            // get limit results of search when limit changed
            axios.get(`/api/stations/getStationsByName/${limit}?name=${search}`).then((response) => {
                setStations(response.data)
            })
                .catch(() => console.log("No Result for radio station in database with search name"));
        } else if (country) {
            // get limit results of country when limit changed
            axios.get(`/api/stations/getStationsByCountry/${limit}?country=${country}`).then(response =>
                setStations(response.data));
        } else {
            // get limit results of all stations when limit changed
            axios.get(`/api/stations/getStations/${limit}`).then((response) => {
                setStations(response.data)
            })
        }
        // get all possible countries from stations to setup filter
        axios.get("/api/stations/getAllCountries").then((response => {
            setCountryFilterElems(response.data)
        }))
    }, [limit]);

    useEffect(() => {
        // set favourites when user changed
        user && setFavourites(user.favouriteStations);
    }, [user]);

    const onSearch = (event: { preventDefault: () => void; }) => {
        // reset country filter when use search field
        setCountry("");
        // search for station name
        axios.get(`/api/stations/getStationsByName/${limit}?name=${search}`).then((response) => {
            setStations(response.data)
        })
            // catch if no station with name in database
            .catch(() => {
                console.log("No Result for radio station in database with search name")
            });
        event.preventDefault();
    }

    const onResetSearch = () => {
        // reset search when "x" is clicked
        setSearch("")
        axios.get(`/api/stations/getStations/${limit}`).then((response) => {
            setStations(response.data)
        })
    }

    const toggleFavourite = (station: Station): void => {
        let updatedFavourites: Station[];
        if (favourites.some(fav => fav.stationuuid === station.stationuuid)) {
            // un-favourite if station is already selected as favourite
            updatedFavourites = favourites.filter((s) => s.stationuuid !== station.stationuuid);
        } else {
            // add station to favourites
            updatedFavourites = [...favourites, station];
        }
        if (user) {
            // update use when user logged in
            updateUser({...user, favouriteStations: updatedFavourites});
        }
        // set frontend favourites
        setFavourites(updatedFavourites);
    };

    const onShowMore = () => {
        // set new limit of results
        setLimit(limit => limit + 20)
    }

    const addStation = (stationToSave: Station) => {
        // add station manual or from api -> differentiation in backend
        axios.post("/api/stations", stationToSave).then((response) => {
            // set added station as favourite to make it easier to find
            setFavourites([...favourites, response.data]);
            if (user) {
                // update user with new favourite
                updateUser({...user, favouriteStations: [...favourites, response.data]});
            }
        })
            // catch if station already in database
            .catch(() => console.log("Add failed. Station already exists in database"));
    };
    const onCountryFilterChanged = (event: { target: { value: any; }; }) => {
        // reset search field when use country filter
        setSearch("")
        // set country use state
        setCountry(event.target.value)
        if (event.target.value === "Show all") {
            // get all stations when "Show all" selected
            axios.get(`/api/stations/getStations/${limit}`).then((response) => {
                setStations(response.data)
            })
        }
        // make filter request with selected country
        axios.get(`/api/stations/getStationsByCountry/${limit}?country=${event.target.value}`).then(response =>
            setStations(response.data));
    }

    return (
        <div
            className="flex flex-col gap-5 p-5 pt-10 items-center bg-gradient-to-br min-h-screen bg-auto from-[#1c4462] to-[#509cb7]">
            {!showFavourites && <form className="flex gap-3 w-2/3 pr-14 justify-end items-center" onSubmit={onSearch}>
                Search station <input className="bg-[#f8f1e6] text-[#17233c]" value={search}
                                      onChange={(event) => setSearch(event.target.value)}
                                      placeholder=""/>
                <button className="border-transparent" type="button" onClick={onResetSearch}>x</button>
            </form>}
            {!showFavourites && <div className="flex flex-row w-2/3 pr-14 p-3 gap-3 justify-end items-center">
                <AddStationModal saveStation={addStation}/>
                <AddStationFromApiModal saveStation={addStation}/>
            </div>}
            {!showFavourites && <div className="flex flex-row w-2/3 pr-14 gap-3 justify-end items-center">
                Select a country to filter
                <select className="bg-[#f8f1e6] text-[#17233c] w-1/2 h-6 rounded" onChange={onCountryFilterChanged}>
                    {countryFilterElems.map(country => <option key={country} value={country}>{country}</option>)}
                </select>
            </div>}
            {!showFavourites && (<div className="flex flex-col justify-center pb-10 w-2/3">
                {stations.map((s) => <StationCard key={s.stationuuid} station={s}
                                                  isPlaying={s.stationuuid === nowPlaying.stationuuid}
                                                  togglePlayPause={togglePlayPause}
                                                  isFavourite={favourites.some(fav => fav.stationuuid === s.stationuuid)}
                                                  toggleFavourite={toggleFavourite}/>)}

            </div>)}
            {showFavourites && (<div className="flex flex-col p-10 w-2/3">
                {favourites.map((s) => <StationCard key={s.stationuuid} station={s}
                                                    isPlaying={s.stationuuid === nowPlaying.stationuuid}
                                                    togglePlayPause={togglePlayPause}
                                                    isFavourite={favourites.some(fav => fav.stationuuid === s.stationuuid)}
                                                    toggleFavourite={toggleFavourite}/>)}

            </div>)}
            {!showFavourites && (
                <button className="flex bg-[#f8f1e6] p-1 m-5 mb-10 text-[#17233c]" onClick={onShowMore}> Show
                    more</button>)}
        </div>
    )
}