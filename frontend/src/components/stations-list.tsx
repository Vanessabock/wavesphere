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

    useEffect(() => {
        if (search) {
            axios.get(`/api/stations/getStationsByName/${limit}?name=${search}`).then((response) => {
                setStations(response.data)
            })
                .catch(() => console.log("No Result for radio station in database with search name"));
        } else {
            axios.get(`/api/stations/getStations/${limit}`).then((response) => {
                setStations(response.data)
            })
        }
    }, [limit]);

    useEffect(() => {
        user && setFavourites(user.favouriteStations);
    }, [user]);

    const onSearch = (event: { preventDefault: () => void; }) => {
        axios.get(`/api/stations/getStationsByName/${limit}?name=${search}`).then((response) => {
            setStations(response.data)
        })
            .catch(() => {
                console.log("No Result for radio station in database with search name")
            });
        event.preventDefault();
    }

    const onResetSearch = () => {
        setSearch("")
        axios.get(`/api/stations/getStations/${limit}`).then((response) => {
            setStations(response.data)
        })
    }

    const toggleFavourite = (station: Station): void => {
        let updatedFavourites: Station[];
        if (favourites.some(fav => fav.stationuuid === station.stationuuid)) {
            updatedFavourites = favourites.filter((s) => s.stationuuid !== station.stationuuid);
        } else {
            updatedFavourites = [...favourites, station];
        }
        if (user) {
            updateUser({...user, favouriteStations: updatedFavourites});
        }
        setFavourites(updatedFavourites);
    };

    const onShowMore = () => {
        setLimit(limit => limit + 20)
    }

    const addStation = (stationToSave: Station) => {
        axios.post("/api/stations", stationToSave).then((response) => {
            // setStations([...stations, response.data]);
            setFavourites([...favourites, response.data]);
            if (user) {
                updateUser({...user, favouriteStations: [...favourites, response.data]});
            }
        })
            .catch(() => console.log("Add failed. Station already exists in database"));
    };

    return (
        <div
            className="flex flex-col gap-5 p-5 pt-10 items-center bg-gradient-to-br min-h-screen bg-auto from-[#1c4462] to-[#509cb7]">
            {!showFavourites && <form className="flex gap-3 w-2/3 pr-14 justify-end items-center" onSubmit={onSearch}>
                Search station <input value={search} onChange={(event) => setSearch(event.target.value)}
                                      placeholder=""/>
                <button className="border-transparent" type="button" onClick={onResetSearch}>x</button>
            </form>}
            {!showFavourites && <div className="flex flex-row w-2/3 pr-14 p-3 gap-3 justify-end items-center">
                <AddStationModal saveStation={addStation}/>
                <AddStationFromApiModal saveStation={addStation}/>
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