import CharacterCard from "./CharacterCard";
import {renderStatus} from "../common/encounters.js";
import React from "react";
import axios from "axios";

export default class EncounterView extends React.Component {
    interval = null;

    constructor(props) {
        super(props);
        this.state = {isLoading: true, isReferentialLoading: true}
    }

    componentDidMount() {
        axios.get('http://localhost:8084/referential')
            .then((response) => {
                this.setState({referential: response.data, isReferentialLoading: false});
            });
        if (this.state.encounter?.status === 'FINISHED') {
            clearInterval(this.interval)
        } else {
            clearInterval(this.interval);
            this.interval = setInterval(this.getData, 3000);
            this.getData();
        }
    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    launchEncounter = () => {
        axios.post('http://localhost:8082/encounters/' + this.state.encounter.id + ':launch')
            .then((response) => {
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

            if (this.state.encounter !== undefined && this.state.encounter.status === 'FINISHED') {
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
                        <br/>
                        <div className="container">
                            <br/>
                            <div className="row">
                                <div className="col">
                                    {!this.state.isReferentialLoading &&
                                        <>
                                        {this.state.referential.grid[this.state.encounter.grid].x}
                                        </>}
                                </div>
                                <div className="col">
                                    {this.state.encounter.combatLog?.logs?.map((round, index) => (
                                        <div key={index}>
                                            {round.round}
                                            {round.characters.map((character, index) => (
                                                <div key={index}>
                                                    {round.logs[character.id].map((action, index) => (
                                                        <div key={index}>
                                                            {action.description} <br/>
                                                        </div>
                                                    ))}
                                                </div>
                                            ))}
                                        </div>
                                    ))}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                }
            </>
        )
    };
}