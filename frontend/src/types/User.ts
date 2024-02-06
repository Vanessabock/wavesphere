export type User = {
    id: string;
    name: string;
    // Remove githubId from frontend by using a UserGet object
    githubId: number;
    favouriteStations: string[];

} | null;