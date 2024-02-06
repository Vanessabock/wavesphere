import './App.css'
import {Header} from "./components/header.tsx";
import Home from "./components/home.tsx";
import {Route, Routes, useNavigate} from "react-router-dom";
import axios from "axios";
import {useEffect, useState} from "react";
import {User} from "./types/User.ts";
import {Login} from "./components/login.tsx";
import SignUp from "./components/signup.tsx";
import {StationsList} from "./components/stations-list.tsx";

function App() {
    const navigate = useNavigate();

    const [user, setUser] = useState<User>(null);

    useEffect(() => {
        axios.get("/api/user").then((response) => setUser(response.data));
    }, []);

    const logout = () =>
        axios.get("/api/logout").then(() => {
            setUser(null);
            navigate("/");
        });

  return (
      <div className="flex min-h-screen flex-col">
          <Header isLoggedIn={!!user} logout={logout}/>
          <Routes>
              <Route path="/" element={<Home/>}/>
              <Route path="/login" element={<Login/>}/>
              <Route path="/signup" element={<SignUp/>}/>
              <Route path="/stations" element={<StationsList/>}/>
          </Routes>
      </div>
  )
}

export default App
