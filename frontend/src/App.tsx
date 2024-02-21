import './App.css'
import {Header} from "./components/header.tsx";
import Home from "./components/home.tsx";
import {Route, Routes, useNavigate} from "react-router-dom";
import axios from "axios";
import {useEffect, useState} from "react";
import {User} from "./types/User.ts";
import {Login} from "./components/login.tsx";
import {StationsList} from "./components/stations-list.tsx";
import {Station} from "./types/Station.ts";
import {NowPlaying} from "./components/now-playing.tsx";

function App() {
    const navigate = useNavigate();

    const [user, setUser] = useState<User>(null);
    const [nowPlayingStation, setNowPlayingStation] = useState<Station>({
        stationuuid: "",
        name: "",
        url_resolved: "",
        homepage: "",
        favicon: "",
        tags: "",
        country: ""
    })
    const [stationOnNowPlayingScreen, setStationOnNowPlayingScreen] = useState<Station>(nowPlayingStation)

    useEffect(() => {
        axios.get("/api/user").then((response) => {
            setUser(response.data)
        });
    }, []);

    const logout = () => {
        axios.get("/api/logout").then(() => {
            setUser(null);
            navigate("/");
        });
    }

    const updateUser = (updatedUser: User) => {
        axios.put("/api/user", updatedUser).then((response) => response.data);
        setUser(updatedUser);
    };

    const togglePlayPause = (station: Station) => {
        if (!stationOnNowPlayingScreen.stationuuid){
            setStationOnNowPlayingScreen(station)
        }
        if (nowPlayingStation.stationuuid === station.stationuuid) {
            setNowPlayingStation({
                stationuuid: "",
                name: "",
                url_resolved: "",
                homepage: "",
                favicon: "",
                tags: "",
                country: ""
            })
        } else {
            setNowPlayingStation(station)
            setStationOnNowPlayingScreen(station)
        }
    }

    return (
        <div className="flex min-h-screen flex-col">
            <Header isLoggedIn={!!user} logout={logout}/>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/stations" element={<StationsList user={user} nowPlaying={nowPlayingStation}
                                                               showFavourites={false} updateUser={updateUser}
                                                               togglePlayPause={togglePlayPause}/>}/>
                <Route path="/favourites" element={<StationsList user={user} nowPlaying={nowPlayingStation}
                                                                 showFavourites={true} updateUser={updateUser}
                                                                 togglePlayPause={togglePlayPause}/>}/>
            </Routes>
            <NowPlaying nowPlayingStation={nowPlayingStation} stationOnNowPlayingScreen={stationOnNowPlayingScreen} togglePlayPause={togglePlayPause}/>
        </div>
    )
}

export default App
