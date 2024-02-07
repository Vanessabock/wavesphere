import {Station} from "../types/Station.ts";
import React from "react";
import placeholderRadio from "./../assets/radio-placeholder.png"

type StationCardProps = {
    station: Station
}

export const StationCard: React.FC<StationCardProps> = ({station}) => {
    return (
        <div className="flex flex-row justify-start content-center text-[#f8f1e6] p-3">
            <img src={station.favicon || placeholderRadio} alt="Radio"
                 className="flex h-7 mr-5 select-none object-contain"/>
            {station.name}
        </div>
    )
}