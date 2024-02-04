import React, {useEffect, useRef, useState} from "react";

type StickyHeaderProps = {
    isFixed: boolean;
    children: React.ReactNode;
};

export const StickyHeader: React.FC<StickyHeaderProps> = ({isFixed, children}) => {
    const [top, setTop] = useState<number>(0);
    const header = useRef<HTMLDivElement>(null);

    useEffect(() => {
        const handleScroll = () => {
            if (header.current) {
                setTop(header.current.offsetTop);
            }
        };

        window.addEventListener("scroll", handleScroll);

        return () => {
            window.removeEventListener("scroll", handleScroll);
        };
    }, []);

    return (
        <div ref={header} className={`sticky-header ${top > 0 ? "scrolled" : ""} ${isFixed ? "fixed" : "sticky"}`}>
            {children}
        </div>
    );
};
