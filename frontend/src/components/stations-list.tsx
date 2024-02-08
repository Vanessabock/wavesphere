import {useEffect, useState} from "react";
import {Station} from "../types/Station.ts";
import axios from "axios";
import {StationCard} from "./station-card.tsx";
import {User} from "../types/User.ts";

type StationsListProps = {
    user: User
    updateUser: (updatedUser: User) => void;
}

export const StationsList: React.FC<StationsListProps> = ({user, updateUser}) => {
    const [stations, setStations] = useState<Station[]>([])
    const [nowPlayingStation, setNowPlayingStation] = useState<Station>({
        stationuuid: "",
        name: "",
        url: "",
        homepage: "",
        favicon: "",
    })
    const [favourites, setFavourites] = useState<Station[]>([]);

    //const [count, setCount] = useState<number>(5)
    const count: number = 20

    const togglePlayPause = (station: Station) => {
        if (nowPlayingStation.stationuuid === station.stationuuid) {
            setNowPlayingStation({
                stationuuid: "",
                name: "",
                url: "",
                homepage: "",
                favicon: "",
            })
        } else {
            setNowPlayingStation(station)
        }
    }

    const setFavourite = (station: Station): void => {
        const updatedFavourites: Station[] = [...favourites, station];
        if (user) {
            updateUser({...user, favouriteStations: updatedFavourites});
        }
        setFavourites(updatedFavourites);
    };

    const removeFavourite = (station: Station): void => {
        const updatedFavourites: Station[] = favourites.filter((s) => s.stationuuid !== station.stationuuid);
        if (user) {
            updateUser({...user, favouriteStations: updatedFavourites});
        }
        setFavourites(updatedFavourites);
    };

    useEffect(() => {
        axios.get(`/api/radioStations?count=${count}`).then((response) => {
            setStations(response.data)
        })
        user && setFavourites(user.favouriteStations);
    }, [user]);

    return (
        <div className="flex justify-center bg-gradient-to-br min-h-screen bg-auto from-[#1c4462] to-[#509cb7]">
            <div className="flex flex-col  p-10 w-2/3">
                {stations.map((s) => <StationCard key={s.stationuuid} station={s}
                                                  isPlaying={s.stationuuid === nowPlayingStation.stationuuid}
                                                  togglePlayPause={togglePlayPause}
                                                  isFavourite={favourites.some(fav => fav.stationuuid === s.stationuuid)}
                                                  removeFavourite={removeFavourite}
                                                  setFavourite={setFavourite}/>)}
            </div>
        </div>
    )
}