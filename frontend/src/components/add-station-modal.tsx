import * as React from "react";
import {FormEvent, useState} from "react";
import Modal from "@mui/joy/Modal";
import ModalClose from "@mui/joy/ModalClose";
import Sheet from "@mui/joy/Sheet";
import {Station} from "../types/Station.ts";

type AddStationModalProps = {
    saveStation: (saveStation: Station) => void;
};

export default function AddStationModal(props: Readonly<AddStationModalProps>) {
    const [isModalOpen, setIsModalOpen] = React.useState<boolean>(false);
    const [name, setName] = useState<string>("");
    const [url, setUrl] = useState<string>("");
    const [homepage, setHomepage] = useState<string>("");
    const [favicon, setFavicon] = useState<string>("");

    const onStationSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const stationToSave: Station = {
            stationuuid: "",
            name: name,
            url: url,
            homepage: homepage,
            favicon: favicon
        };

        props.saveStation(stationToSave);
        setName("");
        setUrl("");
        setHomepage("");
        setFavicon("");

        setIsModalOpen(false);
    };


    return (
        <React.Fragment>
            <button
                className="flex bg-[#f8f1e6] p-1 text-[#17233c]"
                onClick={() => {
                    setName("");
                    setUrl("");
                    setHomepage("");
                    setFavicon("");
                    setIsModalOpen(true);
                }}
            >
                Add new Station
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
                    <h2 className="mb-5 flex justify-center text-lg font-bold">Insert station information</h2>
                    <form className="flex flex-col gap-3" onSubmit={onStationSubmit}>
                        <div className="flex justify-between">
                            Station name
                            <input
                                value={name}
                                onChange={(event) => setName(event.target.value)}
                                placeholder=""
                            />
                        </div>
                        <div className="flex justify-between">
                            Stream Url <input value={url} onChange={(event) => setUrl(event.target.value)} placeholder=""/>
                        </div>
                        <div className="flex justify-between">
                            Homepage <input value={homepage} onChange={(event) => setHomepage(event.target.value)}
                                          placeholder=""/>
                        </div>
                        <div className="flex justify-between">
                            Icon <input value={favicon} onChange={(event) => setFavicon(event.target.value)}
                                         placeholder=""/>
                        </div>
                        <button type="submit">Save</button>
                    </form>
                </Sheet>
            </Modal>
        </React.Fragment>
    );
}
