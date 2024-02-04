import './App.css'
import {Header} from "./components/header.tsx";
import Home from "./components/home.tsx";
import {Route, Routes} from "react-router-dom";

function App() {

    const logout = () => {

    }

  return (
      <div className="flex min-h-screen flex-col">
          <Header isLoggedIn={false} logout={logout}/>
          <Routes>
              <Route path="/" element={<Home/>}
              />
          </Routes>
      </div>
  )
}

export default App
