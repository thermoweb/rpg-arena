import loadingBar from "../assets/loading-bar.gif";
import Layout from "../components/Layout";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import EncounterView from "../components/EncounterView";

export default function EncounterViewPage() {
    const {encounterId} = useParams();

    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const getData = async () => {
            try {
                const response = await axios.get(
                    `http://localhost:8082/encounters/` + encounterId
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
    }, [loading, encounterId]);

    return (
        <Layout>
            {loading && <img src={loadingBar} alt={'loading'}/>}
            {!loading && <EncounterView key={encounterId} encounterId={encounterId} encounter={data}></EncounterView>}
        </Layout>
    );
}