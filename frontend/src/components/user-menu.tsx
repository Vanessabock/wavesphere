import React from "react";
import {Link} from "react-router-dom";
import {cn} from "../lib/utils.ts";

type UserMenuProps = {
    isLoggedIn: boolean;
    className: string;
    logout: () => void;
    toggleMenu?: () => void;
};

export const UserMenu: React.FC<UserMenuProps> = ({isLoggedIn, className, logout, toggleMenu}) => {
    return isLoggedIn ? (
        <div className={className}>
            <button onClick={logout} className={`h-8 rounded-lg text-[#17233c] px-3 text-sm`}>
                Log Out
            </button>
        </div>
    ) : (
        <div className={cn("gap-2 text-sm", className)}>
            <Link onClick={toggleMenu} to="/login">
                <button className="h-8 w-full rounded-lg text-[#17233c] px-3">Log In</button>
            </Link>
        </div>
    );
};
