import {Station} from "../types/Station.ts";
import React from "react";
import {PauseIcon} from "./pause-icon.tsx";
import {PlayIcon} from "./play-icon.tsx";

type NowPlayingProps = {
    nowPlayingStation: Station;
    togglePlayPause: (station: Station) => void
};

export const NowPlaying: React.FC<NowPlayingProps> = ({nowPlayingStation, togglePlayPause}) => {

    const toggleRadio = () => {
        togglePlayPause(nowPlayingStation)
    }

    return (
        <>
            {nowPlayingStation.stationuuid && (
                <div className="flex flex-col bottom-0 sticky h-40 w-full bg-[#17233c] text-[#f8f1e6] text-2xl">
                    <div className="flex justify-center m-4">{nowPlayingStation.name}</div>
                    <button className="flex justify-center border-transparent" onClick={toggleRadio}>
                        {!!nowPlayingStation.stationuuid ? <PauseIcon size={"50px"}/> : <PlayIcon size={"50px"}/>}
                    </button>
                </div>)}
        </>
    )
}