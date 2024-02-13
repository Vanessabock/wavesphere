import {Station} from "./Station.ts";

export type User = {
    id: string;
    name: string;
    githubId: number;
    favouriteStations: Station[];
} | null;