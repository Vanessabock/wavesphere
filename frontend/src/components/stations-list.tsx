import React, {useEffect, useState} from "react";
import {Station} from "../types/Station.ts";
import axios from "axios";
import {StationCard} from "./station-card.tsx";
import {User} from "../types/User.ts";

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
    //const [count, setCount] = useState<number>(5)
    const count: number = 20


    useEffect(() => {
        axios.get(`/api/radioStations?count=${count}`).then((response) => {
            setStations(response.data)
        })
        user && setFavourites(user.favouriteStations);
    }, [user]);

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

    return (
        <div className="flex justify-center bg-gradient-to-br min-h-screen bg-auto from-[#1c4462] to-[#509cb7]">
            {!showFavourites && (<div className="flex flex-col  p-10 w-2/3">
                {stations.map((s) => <StationCard key={s.stationuuid} station={s}
                                                  isPlaying={s.stationuuid === nowPlaying.stationuuid}
                                                  togglePlayPause={togglePlayPause}
                                                  isFavourite={favourites.some(fav => fav.stationuuid === s.stationuuid)}
                                                  toggleFavourite={toggleFavourite}/>)}

            </div>)}
            {showFavourites && (<div className="flex flex-col  p-10 w-2/3">
                {favourites.map((s) => <StationCard key={s.stationuuid} station={s}
                                                    isPlaying={s.stationuuid === nowPlaying.stationuuid}
                                                    togglePlayPause={togglePlayPause}
                                                    isFavourite={favourites.some(fav => fav.stationuuid === s.stationuuid)}
                                                    toggleFavourite={toggleFavourite}/>)}

            </div>)}
        </div>
    )
}