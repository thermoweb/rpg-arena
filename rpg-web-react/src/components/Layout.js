import React from 'react';
import Banner from "./Banner";

const Layout =({children}) =>{
    return(
        <>
            <div>
                <Banner />
            </div>
            <main>{children}</main>
        </>
    )
}

export default Layout;
