import { useEffect, useState } from "react";
import { User } from "../types/User.ts";
import axios from "axios";

export const UserStatistics = () => {
  const [user, setUser] = useState<User>(null)

  useEffect(() => {
    axios.get("/api/user").then((response) => {
      setUser(response.data);
    });
  }, []);

  return (
    <>
      <h2> {user?.name}</h2>
      {user?.listeningStatistics.map((st) => <div> {st.stationName} listened {st.listenedTime} seconds </div>)}
    </>
  );
}