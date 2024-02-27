import {Station} from "./Station.ts";
import { ListeningStatistic } from "./ListeningStatistic.ts";


export type User = {
    id: string;
    name: string;
    githubId: number;
    favouriteStations: Station[];
    listeningStatistics: ListeningStatistic[];
} | null;