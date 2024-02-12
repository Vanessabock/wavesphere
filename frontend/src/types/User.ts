import {Station} from "./Station.ts";

export type User = {
    id: string;
    name: string;
    githubId: string;
    favouriteStations: Station[];
} | null;