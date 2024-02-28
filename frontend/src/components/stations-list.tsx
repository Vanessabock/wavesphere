import React, { useEffect, useState } from "react";
import { Station } from "../types/Station.ts";
import axios from "axios";
import { StationCard } from "./station-card.tsx";
import { User } from "../types/User.ts";
import AddStationModal from "./add-station-modal.tsx";
import AddStationFromApiModal from "./add-station-from-api-modal.tsx";

type StationsListProps = {
  user: User;
  nowPlaying: Station;
  showFavourites: boolean;
  updateUser: (user: User) => void;
  togglePlayPause: (station: Station) => void;
};

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
    const [country, setCountry] = useState<string>("")
    const [tag, setTag] = useState<string>("");

    useEffect(() => {
      // get Data from DB
      fetchData();
      // get all possible countries from stations to setup filter
      axios.get("/api/stations/getAllCountries").then((response) => {
        setCountryFilterElems(response.data);
      });
    }, [limit, country]);

    useEffect(() => {
      // set favourites when user changed
      user && setFavourites(user.favouriteStations);
    }, [user]);

    const fetchData = () => {
      axios
        .get(
          `/api/stations/getStations/${limit}?name=${search}&country=${country}&tag=${tag}`,
        )
        .then((response) => {
          setStations(response.data);
        });
    };

    const onSearch = (event: { preventDefault: () => void }) => {
      fetchData();
      event.preventDefault();
    };

    const onResetSearch = () => {
      // reset search when "x" is clicked
      setSearch("");
    };

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
    const onCountryFilterChanged = (event: {
      target: { value: React.SetStateAction<string> };
    }) => {
      if (event.target.value === "Show all") {
        setCountry("");
      } else {
          setCountry(event.target.value);
      }
    };

  const onTagSearch = (event: { preventDefault: () => void }) => {
    fetchData();
    event.preventDefault();
  }

  const onResetTag = () => {
    setTag("")
  }

  return (
      <div className="flex min-h-screen flex-col items-center gap-5 bg-gradient-to-br from-[#1c4462] to-[#509cb7] bg-auto p-5 pt-10 text-[#f8f1e6]">
        {!showFavourites && (
          <form
            className="flex w-2/3 items-center justify-end gap-3 pr-14"
            onSubmit={onSearch}
          >
            Search station{" "}
            <input
              className="bg-[#f8f1e6] text-[#17233c]"
              value={search}
              onChange={(event) => setSearch(event.target.value)}
              placeholder=""
            />
            <button
              className="border-transparent"
              type="button"
              onClick={onResetSearch}
            >
              x
            </button>
          </form>
        )}
        {!showFavourites && (
          <form
            className="flex w-2/3 items-center justify-end gap-3 pr-14"
            onSubmit={onTagSearch}
          >
            Search for genre{" "}
            <input
              className="bg-[#f8f1e6] text-[#17233c]"
              value={tag}
              onChange={(event) => setTag(event.target.value)}
              placeholder=""
            />
            <button
              className="border-transparent"
              type="button"
              onClick={onResetTag}
            >
              x
            </button>
          </form>
        )}
        {!showFavourites && (
          <div className="flex w-2/3 flex-row items-center justify-end gap-3 pr-14">
            <p> Select a country to filter </p>
            <select
              className="h-6 w-1/2 rounded bg-[#f8f1e6] text-[#17233c]"
              onChange={onCountryFilterChanged}
            >
              {countryFilterElems.map((country) => (
                <option key={country} value={country}>
                  {country}
                </option>
              ))}
            </select>
          </div>
        )}
        {!showFavourites && (
          <div className="flex w-2/3 flex-row items-center justify-end gap-3 p-3 pb-5 pr-14">
            <AddStationModal saveStation={addStation} />
            <AddStationFromApiModal saveStation={addStation} />
          </div>
        )}
        {!showFavourites && (
          <div className="flex w-2/3 flex-col justify-center pb-10">
            {stations.map((s) => (
              <StationCard
                key={s.stationuuid}
                station={s}
                isPlaying={s.stationuuid === nowPlaying.stationuuid}
                togglePlayPause={togglePlayPause}
                isFavourite={favourites.some(
                  (fav) => fav.stationuuid === s.stationuuid,
                )}
                toggleFavourite={toggleFavourite}
              />
            ))}
          </div>
        )}
        {showFavourites && (
          <div className="flex w-2/3 flex-col p-10">
            {favourites.map((s) => (
              <StationCard
                key={s.stationuuid}
                station={s}
                isPlaying={s.stationuuid === nowPlaying.stationuuid}
                togglePlayPause={togglePlayPause}
                isFavourite={favourites.some(
                  (fav) => fav.stationuuid === s.stationuuid,
                )}
                toggleFavourite={toggleFavourite}
              />
            ))}
          </div>
        )}
        {!showFavourites && (
          <button
            className="m-5 mb-10 flex bg-[#f8f1e6] p-1 text-[#17233c]"
            onClick={onShowMore}
          >
            {" "}
            Show more
          </button>
        )}
      </div>
    );
}