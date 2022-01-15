import React, { useContext } from "react";
import { AppDataContext } from "../App";

export default function HomePage() {
    const appData = useContext(AppDataContext);

    return(
        <div>
            <p>You aren't authorized by the system.</p>
        </div>
    );
}
