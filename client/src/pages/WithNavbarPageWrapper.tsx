import {Navbar} from "../components/Navbar";
import React, {FunctionComponent} from "react";

interface WithNavbarProps {
    component: React.ComponentType;
    withNavbar: boolean;
}

export const WithNavbarPageWrapper: FunctionComponent<WithNavbarProps> = ({component: Component, withNavbar}) => {
    return (
        <>
            {withNavbar && <Navbar />}
            <Component/>
        </>
    );
};
