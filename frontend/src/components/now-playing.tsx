import {Station} from "../types/Station.ts";
import React from "react";

type NowPlayingProps = {
    nowPlayingStation: Station;
};

export const NowPlaying: React.FC<NowPlayingProps> = ({nowPlayingStation}) => {

    return (
        <>
            {nowPlayingStation.stationuuid && (
                <div className="flex bottom-0 sticky justify-center h-40 w-full bg-[#17233c] text-[#f8f1e6]">
                    {nowPlayingStation.name}
                </div>)}
        </>
    )
}