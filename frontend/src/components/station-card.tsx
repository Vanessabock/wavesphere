import {Station} from "../types/Station.ts";
import React, {useState} from "react";
import placeholderRadio from "./../assets/radio-placeholder.png"
import {PauseIcon} from "./pause-icon.tsx";
import {PlayIcon} from "./play-icon.tsx";

type StationCardProps = {
    station: Station
}

export const StationCard: React.FC<StationCardProps> = ({station}) => {
    const [isPlaying, setIsPlaying] = useState<boolean>(false)

    const toggleRadio = () => {
        setIsPlaying(!isPlaying)
    }

    return (
        <div className="flex flex-col justify-center">
            <div className="flex flex-row justify-between text-[#f8f1e6] p-3 pt-3.5">
                <div className="flex flex-row">
                    <img src={station.favicon || placeholderRadio} alt="RadioImage"
                         className="flex h-7 mr-5 select-none object-contain"/>
                    <p className="pt-1"> {station.name} </p>
                </div>
                <div>
                    <button className="flex border-opacity-0" onClick={toggleRadio}>
                        {isPlaying ? <PauseIcon/> : <PlayIcon/>
                        }
                    </button>
                    {isPlaying && (<audio className="hidden" controls autoPlay>
                        <source src={station.url} type="audio/mpeg"/>
                    </audio>)}
                </div>
            </div>
            <div
                className="h-0.5 w-full bg-gradient-to-r from-transparent from-0% via-[#f8f1e6] via-50% to-transparent to-100% ..."></div>
        </div>

    )
}