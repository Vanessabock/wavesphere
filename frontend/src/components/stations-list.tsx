import {useEffect, useState} from "react";
import {Station} from "../types/Station.ts";
import axios from "axios";
import {StationCard} from "./station-card.tsx";

type StationsListProps = {
    nowPlaying: Station
    favourites: Station[]
    showFavourites: boolean
    togglePlayPause: (station: Station) => void;
    toggleFavourite: (station: Station) => void;
}

export const StationsList: React.FC<StationsListProps> = ({
                                                              nowPlaying,
                                                              favourites,
                                                              showFavourites,
                                                              togglePlayPause,
                                                              toggleFavourite
                                                          }) => {
    const [stations, setStations] = useState<Station[]>([])
    //const [count, setCount] = useState<number>(5)
    const count: number = 20


    useEffect(() => {
        axios.get(`/api/radioStations?count=${count}`).then((response) => {
            setStations(response.data)
        })
    }, []);

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