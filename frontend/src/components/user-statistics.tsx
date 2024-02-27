import { useEffect, useState } from "react";
import { User } from "../types/User.ts";
import axios from "axios";

export const UserStatistics = () => {
  const [user, setUser] = useState<User>(null);

  useEffect(() => {
    axios.get("/api/user").then((response) => {
      setUser(response.data);
    });
  }, []);

  return (
    <div className="flex min-h-screen w-full flex-col items-center gap-3 bg-gradient-to-br from-[#1c4462] to-[#509cb7] p-14 text-[#f8f1e6]">
      <div className="flex p-3 text-xl font-bold">
        {user?.name}, you spend some time listening to radio stations!{" "}
      </div>
      <div className="flex w-1/2 flex-col gap-4">
        <div className="... h-0.5 w-full bg-gradient-to-r from-transparent from-0% via-[#f8f1e6] via-50% to-transparent to-100%"></div>
        {user?.listeningStatistics
          .slice(0)
          .reverse()
          .map((st) => (
            <div className="flex flex-row justify-between" key={st.stationName}>
              <p> {st.stationName} </p>
              <div>
                {("00" + Math.floor(st.listenedTime / 3600)).slice(-2)} :{" "}
                {("00" + Math.floor((st.listenedTime % 3600) / 60)).slice(-2)} :{" "}
                {("00" + Math.floor((st.listenedTime % 3600) % 60)).slice(-2)}
              </div>
            </div>
          ))}
        <div className="... h-0.5 w-full bg-gradient-to-r from-transparent from-0% via-[#f8f1e6] via-50% to-transparent to-100%"></div>
      </div>
    </div>
  );
};
