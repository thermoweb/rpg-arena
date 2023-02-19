import Layout from "../components/Layout";
import CharacterView from "../components/CharacterView";
import {useEffect, useState} from "react";
import axios from "axios";
import {useParams} from "react-router-dom";
import loadingBar from "../assets/loading-bar.gif";

function CharacterViewPage() {
    const {characterId} = useParams();

    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const getData = async () => {
            try {
                const response = await axios.get(
                    `http://localhost:8084/characters/` + characterId
                );
                setData(response.data);
                setError(null);
                console.log(data);
            } catch (err) {
                setError(err.message);
                setData(null);
            } finally {
                setLoading(false);
            }
        };
        getData();
    }, [loading, characterId]);

    return (
        <Layout>
            {loading && <img src={loadingBar} alt={'loading'}/>}
            {!loading && <CharacterView character={data}></CharacterView>}
        </Layout>
    );
}

export default CharacterViewPage;