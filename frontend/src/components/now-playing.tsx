import {Station} from "../types/Station.ts";
import React, {useEffect, useRef, useState} from "react";
import {PauseIcon} from "./pause-icon.tsx";
import {PlayIcon} from "./play-icon.tsx";
import {VolumeIcon} from "./volume-icon.tsx";
import {MuteIcon} from "./mute-icon.tsx";
import Slider from '@mui/joy/Slider';

type NowPlayingProps = {
    nowPlayingStation: Station;
    stationOnNowPlayingScreen: Station;
    togglePlayPause: (station: Station) => void;
};

export const NowPlaying: React.FC<NowPlayingProps> = ({
                                                          nowPlayingStation,
                                                          stationOnNowPlayingScreen,
                                                          togglePlayPause,
                                                      }) => {

    const audioPlayer = useRef<HTMLAudioElement>(null);
    const [volume, setVolume] = useState<number>(0.5)

    useEffect(() => {
        if (audioPlayer.current){
            audioPlayer.current.volume = volume;
        }
    }, [volume]);

    const toggleRadio = () => {
        if (nowPlayingStation.stationuuid){
            togglePlayPause(nowPlayingStation)
        } else {
            togglePlayPause(stationOnNowPlayingScreen)
        }
    }

    const toggleSlider = (_event: Event, newValue: number | number[]) => {
        setVolume(newValue as number);
    }

    return (
        <>
            {stationOnNowPlayingScreen.stationuuid && (
                <div className="flex flex-col bottom-0 sticky h-40 w-full bg-[#17233c] text-[#f8f1e6] text-2xl">
                    <div className="flex justify-center m-4">{stationOnNowPlayingScreen.name}</div>
                    <div className="flex flex-row justify-between ml-7 pl-5 w-1/2">
                        <div className="flex flex-row w-1/2 items-center px-5 gap-4">
                            {volume === 0 ? <MuteIcon/> : <VolumeIcon/>}
                            <Slider min={0} max={1} step={0.01} defaultValue={0.5} onChange={toggleSlider}/>
                        </div>
                        <button className="flex justify-center border-transparent" onClick={toggleRadio}>
                            {nowPlayingStation.stationuuid ? <PauseIcon size={"50px"}/> : <PlayIcon size={"50px"}/>}
                        </button>
                    </div>
                    {nowPlayingStation.stationuuid && (<audio className="hidden" ref={audioPlayer} controls autoPlay src={nowPlayingStation.url}></audio>)}
                </div>)}
        </>
    )
}