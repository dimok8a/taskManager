import {Route, Routes} from "react-router-dom";
import React from "react";
import {useAppSelector} from "../store/hooks";
import {anonymousRoutes, authRoutes, publicRoutes} from "../routes";
import {WithNavbarPageWrapper} from "../pages/WithNavbarPageWrapper";

const AppRouter = () => {
    const user = useAppSelector((state) => state.user);
    return (
        <Routes>
            {
                // Routes for authorized users only
                user.id !== null && authRoutes.map(({path, component, withNavbar}) =>
                    <Route
                        key={path}
                        path={path}
                        element={<WithNavbarPageWrapper withNavbar={withNavbar} component={component} />}/>)
            }
            {
                // Routes for unauthorized users only
                user.id == null && anonymousRoutes.map(({path, component, withNavbar}) =>
                    <Route
                        key={path}
                        path={path}
                        // render={() => <WithNavbarPageWrapper withNavbar={withNavbar} component={component} />}
                        element={<WithNavbarPageWrapper withNavbar={withNavbar} component={component} />} />)
            }
            {
                // Routes for everyone
                publicRoutes.map(({path, component, withNavbar}) =>
                <Route
                    key={path}
                    path={path}
                    element={<WithNavbarPageWrapper withNavbar={withNavbar} component={component} />}/>)
            }
        </Routes>
    );
};

export default AppRouter;
