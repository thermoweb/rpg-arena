import React from 'react';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import Root from "./routes/root";
import CharactersPage from "./routes/characters";
import CharacterViewPage from "./routes/character_view";
import EncountersPage from "./routes/encounters";
import CharacterCreationForm from './components/CharacterCreationForm'
import EncounterViewPage from "./routes/encounter_view";
import EncounterCreationForm from "./components/EncounterCreationForm";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Root/>,
    },
    {
        name: "characterList",
        path: "/characters",
        element: <CharactersPage/>,
    },
    {
        name: "character",
        path: "/characters/:characterId",
        element: <CharacterViewPage/>,
    },
    {
        name: "characterCreation",
        path: "/characters/create",
        element: <CharacterCreationForm/>,
    },
    {
        path: "/encounters",
        element: <EncountersPage/>,
    },
    {
        path: "/encounters/:encounterId",
        element: <EncounterViewPage/>,
    },
    {
        path: "/encounters/create",
        element: <EncounterCreationForm/>,
    }
]);

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <RouterProvider router={router}/>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
