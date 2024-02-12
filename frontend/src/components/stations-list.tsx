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
    const [count, setCount] = useState<number>(20)
    const [search, setSearch] = useState<string>("")


    useEffect(() => {
        if (search){
            axios.get(`/api/radioStations/${search}?count=${count}`).then((response) => {
                setStations(response.data)
            })
        } else {
            axios.get(`/api/radioStations?count=${count}`).then((response) => {
                setStations(response.data)
            })
        }
        user && setFavourites(user.favouriteStations);
    }, [user, count]);

    const onSearch = (event: { preventDefault: () => void; }) => {
        axios.get(`/api/radioStations/${search}?count=${count}`).then((response) => {
            setStations(response.data)
        })
        event.preventDefault();
    }

    const onResetSearch = () => {
        setSearch("")
        axios.get(`/api/radioStations?count=${count}`).then((response) => {
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
        setCount(count + 20)
    }

    return (
        <div
            className="flex flex-col p-5 items-center bg-gradient-to-br min-h-screen bg-auto from-[#1c4462] to-[#509cb7]">
                <form className="flex gap-3 w-2/3 pr-14 items-end justify-end" onSubmit={onSearch}>
                    Search station <input value={search} onChange={(event) => setSearch(event.target.value)}
                                          placeholder=""/>
                    <button className="border-transparent" type="button" onClick={onResetSearch}>x</button>
                </form>
            {!showFavourites && (<div className="flex flex-col justify-center p-10 w-2/3">
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