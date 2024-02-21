import {Station} from "../types/Station.ts";
import React from "react";
import placeholderRadio from "./../assets/radio-placeholder.png"
import {PauseIcon} from "../icons/pause-icon.tsx";
import {PlayIcon} from "../icons/play-icon.tsx";
import {WebsiteLinkIcon} from "../icons/website-link-icon.tsx";
import {FavouriteIcon} from "../icons/favourite-icon.tsx";

type StationCardProps = {
    station: Station
    isPlaying: boolean
    togglePlayPause: (station: Station) => void
    isFavourite: boolean;
    toggleFavourite: (station: Station) => void;
}

export const StationCard: React.FC<StationCardProps> = ({
                                                            station,
                                                            isPlaying,
                                                            togglePlayPause,
                                                            isFavourite,
                                                            toggleFavourite
                                                        }) => {

    const toggleRadio = () => {
        togglePlayPause(station)
    }

    const toggleHeart = () => {
        toggleFavourite(station)
    };

    return (
        <div className="flex flex-col justify-center">
            <div className="flex flex-row justify-between text-[#f8f1e6] p-3 pt-3.5">
                <div className="flex flex-row">
                    <img src={station.favicon || placeholderRadio} alt="RadioImage"
                         className="flex h-7 mr-5 select-none object-contain"/>
                    <p className="pt-1"> {station.name} </p>
                    <a className="border-transparent cursor-pointer pl-3 pt-2" href={station.homepage}
                       target="_blank"><WebsiteLinkIcon/></a>
                </div>
                <div className="flex flex-row ">
                    <button className="border-transparent mr-2" onClick={toggleHeart}><FavouriteIcon
                        isActive={isFavourite}/></button>
                    <button className="border-transparent" onClick={toggleRadio}>
                        {isPlaying ? <PauseIcon size={"30px"}/> : <PlayIcon size={"30px"}/>}
                    </button>
                </div>
            </div>
            <div
                className="h-0.5 w-full bg-gradient-to-r from-transparent from-0% via-[#f8f1e6] via-50% to-transparent to-100% ..."></div>
        </div>

    )
}