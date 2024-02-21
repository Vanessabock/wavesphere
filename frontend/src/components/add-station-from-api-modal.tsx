import * as React from "react";
import {FormEvent, useState} from "react";
import Modal from "@mui/joy/Modal";
import ModalClose from "@mui/joy/ModalClose";
import Sheet from "@mui/joy/Sheet";
import {Station} from "../types/Station.ts";
import axios from "axios";

type AddStationModalProps = {
    saveStation: (saveStation: Station) => void;
};

export default function AddStationFromApiModal(props: Readonly<AddStationModalProps>) {
    const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
    const [name, setName] = useState<string>("");
    const [apiStations, setApiStations] = useState<Station[]>([])

    const onStationSave = (stationToSave: Station) => {
        props.saveStation(stationToSave)
        setApiStations([])
        setIsModalOpen(false);
    };

    const onStationSearch = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        axios.get(`/api/radioStations/${name}?limit=10`).then((response) => {
            setApiStations(response.data)
        })
            .catch(() => console.log("No Result for Radio Station Api Request with search name"));
    }


    return (
        <React.Fragment>
            <button
                className="flex bg-[#f8f1e6] p-1 text-[#17233c]"
                onClick={() => {
                    setName("");
                    setApiStations([]);
                    setIsModalOpen(true);
                }}
            >
                Add Station from api
            </button>
            <Modal
                className="flex flex-col items-center justify-center"
                aria-labelledby="modal-title"
                aria-describedby="modal-desc"
                open={isModalOpen}
                onClose={() => setIsModalOpen(false)}
            >
                <Sheet className="rounded-md p-7">
                    <ModalClose variant="plain"/>
                    <h2 className="mb-5 flex justify-center text-lg font-bold">Search for station to add</h2>
                    <form className="flex flex-col gap-3" onSubmit={onStationSearch}>
                        <div className="flex gap-3 justify-between">
                            Station name <input
                            value={name}
                            onChange={(event) => setName(event.target.value)}
                            placeholder=""
                        />
                        </div>
                        <button type="submit">Search</button>
                    </form>
                    {apiStations.length > 0 && <div className="flex flex-col">
                        {apiStations.map((s) =>
                            <div className="flex flex-row justify-between p-1" key={s.stationuuid}>
                                {s.name}
                                <button onClick={() => onStationSave(s)}> Save</button>
                            </div>)}
                    </div>}
                </Sheet>
            </Modal>
        </React.Fragment>
    );
}
