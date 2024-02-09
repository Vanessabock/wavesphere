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
                <div className="flex bottom-0 sticky justify-center h-40 w-full bg-[#17233c] text-[#f8f1e6]">
                    {nowPlayingStation.name}
                    <button className="border-transparent" onClick={toggleRadio}>
                        {!!nowPlayingStation.stationuuid ? <PauseIcon size={"30px"}/> : <PlayIcon size={"30px"}/>}
                    </button>
                </div>)}
        </>
    )
}