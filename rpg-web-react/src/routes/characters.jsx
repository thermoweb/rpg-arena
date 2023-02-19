import Layout from "../components/Layout";
import CharactersList from "../components/CharactersList";
import axios from "axios";
import {useEffect, useState} from "react";

function CharactersPage() {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const getData = async () => {
            try {
                const response = await axios.get(
                    `http://localhost:8084/characters`
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
    }, [loading]);

    return (
        <Layout>
            <CharactersList data={data} loading={loading} error={error}></CharactersList>
        </Layout>
    );
}

export default CharactersPage