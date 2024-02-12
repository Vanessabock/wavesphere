import React from "react";

type PauseIconProps = {
    size: string
}

export const PauseIcon: React.FC<PauseIconProps> = ({size}) => {
    return (
        <svg width={size}
             height={size}
             viewBox="0 0 24 24"
             fill="none"
             xmlns="http://www.w3.org/2000/svg">

            <g id="SVGRepo_bgCarrier" strokeWidth="0"></g>
            <g id="SVGRepo_tracerCarrier" strokeLinecap="round" strokeLinejoin="round"></g>
            <g id="SVGRepo_iconCarrier">
                <path
                    d="M10 6.42004C10 4.76319 8.65685 3.42004 7 3.42004C5.34315 3.42004 4 4.76319 4 6.42004V18.42C4 20.0769 5.34315 21.42 7 21.42C8.65685 21.42 10 20.0769 10 18.42V6.42004Z"
                    stroke="#f8f1e6" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"></path>
                <path
                    d="M20 6.42004C20 4.76319 18.6569 3.42004 17 3.42004C15.3431 3.42004 14 4.76319 14 6.42004V18.42C14 20.0769 15.3431 21.42 17 21.42C18.6569 21.42 20 20.0769 20 18.42V6.42004Z"
                    stroke="#f8f1e6" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"></path>
            </g>
        </svg>

    )
}