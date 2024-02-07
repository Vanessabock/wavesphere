import {useEffect, useState} from "react";
import {Station} from "../types/Station.ts";
import axios from "axios";
import {StationCard} from "./station-card.tsx";

export const StationsList = () => {
    const [stations, setStations] = useState<Station[]>([])
    //const [count, setCount] = useState<number>(5)
    const count: number = 20

    useEffect(() => {
        axios.get(`/api/radioStations?count=${count}`).then((response) => {
            setStations(response.data)
        })
    }, []);

    return (
        <div className="flex justify-center bg-gradient-to-br min-h-screen bg-auto from-[#1c4462] to-[#509cb7]">
            <div className="flex flex-col  p-10 w-2/3">
                {stations.map((s) => <StationCard key={s.stationuuid} station={s}/>)}
            </div>
        </div>
    )
}