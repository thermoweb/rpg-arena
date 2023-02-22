import loading from '../assets/ajax-loader.gif';
import axios from "axios";

export const renderStatus = (status) => {
    switch (status) {
        case 'FINISHED':
            return <span className="badge text-bg-success">FINISHED</span>
        case 'FAILED':
            return <span className="badge text-bg-danger">FAILED</span>
        case 'IN_PROGRESS':
            return <span className="badge text-bg-primary">IN PROGRESS <img src={loading} alt={'loading gif'}/></span>
        case 'QUEUED':
            return <span className="badge text-bg-primary">QUEUED <img src={loading} alt={'loading gif'}/></span>
        default:
            return <span className="badge text-bg-primary">{status}</span>
    }
}

const launchEncounter = (encounterId) => {
    axios.post('http://localhost:8082/encounters/' + encounterId + ':launch')
        .then((response) => {
    });
}

export const renderEncountersActions = (encounter) => {
    return <>
        {encounter.status === 'CREATED' &&
            <button className="btn btn-danger " role="button" aria-disabled="true"
                  onClick={() => launchEncounter(encounter.id)}>Launch</button>}
        {encounter.status === 'FAILED' &&
            <button className="btn btn-danger " role="button" aria-disabled="true"
                  onClick={() => launchEncounter(encounter.id)}>Retry</button>}
    </>
}