import {useEffect, useState} from "react";
import {Station} from "../types/Station.ts";
import {StationCard} from "./station-card.tsx";
import {User} from "../types/User.ts";

type StationsListProps = {
    user: User
    updateUser: (updatedUser: User) => void;
}

export const FavouritesList: React.FC<StationsListProps> = ({user, updateUser}) => {
    const [nowPlayingStation, setNowPlayingStation] = useState<Station>({
        stationuuid: "",
        name: "",
        url: "",
        homepage: "",
        favicon: "",
    })
    const [favourites, setFavourites] = useState<Station[]>([]);

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
        user && setFavourites(user.favouriteStations);
    }, [user]);

    return (
        <div className="flex justify-center bg-gradient-to-br min-h-screen bg-auto from-[#1c4462] to-[#509cb7]">
            <div className="flex flex-col  p-10 w-2/3">
                {favourites.map((s) => <StationCard key={s.stationuuid} station={s}
                                                  isPlaying={s.stationuuid === nowPlayingStation.stationuuid}
                                                  togglePlayPause={togglePlayPause}
                                                  isFavourite={favourites.some(fav => fav.stationuuid === s.stationuuid)}
                                                  removeFavourite={removeFavourite}
                                                  setFavourite={setFavourite}/>)}
            </div>
        </div>
    )
}