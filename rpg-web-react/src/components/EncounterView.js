import CharacterCard from "./CharacterCard";
import {renderEncountersActions, renderStatus} from "../common/encounters.js";
import React from "react";
import axios from "axios";

export default class EncounterView extends React.Component {
    interval = null;

    constructor(props) {
        super(props);
        this.state = {isLoading: true}
    }

    componentDidMount() {
        if (this.state.encounter?.status === 'FINISHED') {
            console.log("clear interval")
            clearInterval(this.interval)
        } else {
            console.log("set interval")
            clearInterval(this.interval);
            this.interval = setInterval(this.getData, 3000);
            this.getData();
        }
    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    launchEncounter = () => {
        console.log("launching encounter");
        axios.post('http://localhost:8082/encounters/' + this.state.encounter.id + ':launch')
            .then((response) => {
                console.log(response);
                console.log("set interval");
                clearInterval(this.interval);
                this.interval = setInterval(this.getData, 500);
                this.getData();
            });
    }

    getData = () => {
        axios.get(
            `http://localhost:8082/encounters/` + this.props.encounterId
        ).then((response) => {
            this.setState({encounter: response.data, isLoading: false});
            console.log(this.state.encounter);

            if(this.state.encounter !== undefined && this.state.encounter.status === 'FINISHED') {
                console.log("remove interval");
                console.log(this.interval);
                clearInterval(this.interval);
            }
        });
    }

    render() {
        return (
            <>
                {this.state.isLoading && <p>loading</p>}
                {!this.state.isLoading && <div className="container">
                    <br/>
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">#{this.state.encounter.id}</h5>
                            {renderStatus(this.state.encounter.status)}
                            <div className="container">
                                <br/>
                                <div className="col">
                                    {this.state.encounter.status === 'CREATED' &&
                                        <button className="btn btn-danger " role="button" aria-disabled="true"
                                                onClick={() => this.launchEncounter()}>Launch</button>}
                                    {this.state.encounter.status === 'FAILED' &&
                                        <button className="btn btn-danger " role="button" aria-disabled="true"
                                                onClick={() => this.launchEncounter()}>Retry</button>}
                                    <br/>
                                    <br/>
                                    <div className="row">
                                        {this.state.encounter.characters.map((character) => (
                                            <div key={character.id} className="col-sm-6 mb-3 mb-sm-0">
                                                <CharacterCard character={character} encounter={this.state.encounter}/>
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>}
            </>
        )
    };
}