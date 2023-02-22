import Layout from "../components/Layout";
import EncounterList from "../components/EncounterList";
import {useEffect, useState} from "react";
import axios from "axios";
import loadingBar from "../assets/ajax-loader.gif";

export default function EncountersPage({encounterId}) {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const getData = async () => {
            try {
                const response = await axios.get(
                    `http://localhost:8082/encounters`
                );
                setData(response.data);
                setError(null);
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
            {loading && <p><img src={loadingBar} alt={'loading'}/> loading</p>}
            {!loading && encounterId !== undefined && <div className="container"><EncounterList encounters={data.content}/></div>}
            {!loading && encounterId === undefined && <div className="container"><EncounterList encounters={data.content}/></div>}
        </Layout>
    )
}