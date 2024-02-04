import React from "react";

type HamburgerTwoProps = {
  onHamburgerClick: () => void;
  isActive: boolean;
};

export const HamburgerTwo: React.FC<HamburgerTwoProps> = ({ onHamburgerClick, isActive }) => {
  return (
    <div>
      <button onClick={onHamburgerClick} className="hamburger-two">
        <div className="bars">
          <div className={`bar top ${isActive ? "active" : ""}`}></div>
          <div className={`bar bottom ${isActive ? "active" : ""}`}></div>
        </div>
      </button>
    </div>
  );
};
