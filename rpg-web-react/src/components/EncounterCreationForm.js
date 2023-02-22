import React from "react";
import axios from 'axios';
import {Navigate} from "react-router-dom";
import Layout from "./Layout";

export default class EncounterCreationForm extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            created: false,
            isChallengersLoading: true,
            isReferentialLoading: true,
            brainUri: 'http://localhost:8083/brain',
            characterId: '',
            brainType: 'REMOTE',
            opponentBrainUri: 'http://localhost:8083/brain',
            opponentId: '',
            opponentBrainType: 'REMOTE',
            grid: 'SQUARE_16'
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        axios.get('http://localhost:8084/characters')
            .then((response) => {
                this.setState({challengers: response.data, isChallengersLoading: false});
            })
        axios.get('http://localhost:8084/referential')
            .then((response) => {
                this.setState({referential: response.data, isReferentialLoading: false});
            })
    }

    handleChange(event) {
        this.setState({[event.target.name]: event.target.value});
    }

    handleSubmit(event) {
        console.log(event);
        console.log(this.state);
        axios.post('http://localhost:8084/encounters', {
            characterId: this.state.characterId,
            opponentId: this.state.opponentId,
            grid: this.state.grid,
            brain: {
                type: this.state.brainType,
                uri: this.state.brainUri
            },
            opponentBrain: {
                type: this.state.opponentBrainType,
                uri: this.state.opponentBrainUri
            },
        })
            .then((response) => {
                this.setState({created: true, id: response.data.id}); // id: response.data.id,
            })
            .catch(function (error) {
                console.log(error);
            });
        event.preventDefault();
    }

    renderBrainTypeOptions() {
        return (
            <>
                <option value='REMOTE'>REMOTE</option>
                <option value='LOCAL'>LOCAL</option>
            </>
        )
    }

    getChallengersOptions() {
        return <>
            {!this.state.isChallengersLoading && this.state.challengers.map((character) => (
                <option key={character.id}
                        value={character.id}>{character.name}</option>
            ))}
        </>;
    }

    render() {
        if (this.state.created) {
            return <Navigate to={"/encounters/" + this.state.id} replace={true}/>
        }
        return (
            <Layout>
                <br/>
                <div className="container">
                    <form onSubmit={this.handleSubmit}>
                        <div className="row">
                            <div className="col">
                                <div className="card">
                                    <div className="card-body">
                                        <div className="row">
                                            <div className="col">
                                                <div className="input-group mb-3">
                                                    <span className="input-group-text"
                                                          id="basic-addon1">Brain URI</span>
                                                    <input type="text" className="form-control"
                                                           placeholder="http://host:port/brain"
                                                           aria-label="brainUri"
                                                           value={this.state.brainUri} onChange={this.handleChange}
                                                           aria-describedby="basic-addon1" name="brainUri"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="row">
                                            <div className="col">
                                                <div className="input-group mb-3">
                                                    <label className="input-group-text"
                                                           htmlFor="Character">Character</label>
                                                    <select className="form-select" id="Character"
                                                            onChange={this.handleChange} name="characterId">
                                                        <option value="">RANDOM</option>
                                                        {this.getChallengersOptions()}
                                                    </select>
                                                </div>
                                            </div>
                                            <div className="col">
                                                <div className="input-group mb-3">
                                                    <label className="input-group-text" htmlFor="Profile">Brain</label>
                                                    <select className="form-select" id="Profile"
                                                            value={this.state.brainType} onChange={this.handleChange}
                                                            name="brainType">
                                                        {this.renderBrainTypeOptions()}
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="col">
                                <div className="card">
                                    <div className="card-body">
                                        <div className="row">
                                            <div className="col">
                                                <div className="input-group mb-3">
                                                    <span className="input-group-text"
                                                          id="opponentBrainUri">Brain URI</span>
                                                    <input type="text" className="form-control"
                                                           placeholder="http://host:port/brain"
                                                           aria-label="brainUri"
                                                           value={this.state.opponentBrainUri}
                                                           onChange={this.handleChange}
                                                           aria-describedby="opponentBrainUri"
                                                           name="opponentBrainUri"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="row">
                                            <div className="col">
                                                <div className="input-group mb-3">
                                                    <label className="input-group-text"
                                                           htmlFor="Opponent">Opponent</label>
                                                    <select className="form-select" id="Opponent"
                                                            onChange={this.handleChange} name="opponentId">
                                                        <option value="">RANDOM</option>
                                                        {this.getChallengersOptions()}
                                                    </select>
                                                </div>
                                            </div>
                                            <div className="col">
                                                <div className="input-group mb-3">
                                                    <label className="input-group-text"
                                                           htmlFor="OpponentBrainType">Brain</label>
                                                    <select className="form-select" id="OpponentBrainType"
                                                            value={this.state.opponentBrainType} onChange={this.handleChange} name="opponentBrainType">
                                                        {this.renderBrainTypeOptions()}
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="row">&nbsp;</div>
                        <div className="row">
                            <div className="col">
                                <div className="input-group mb-3">
                                    <label className="input-group-text" htmlFor="Grid">Grid</label>
                                    <select className="form-select" id="Grid" name="grid"
                                            value={this.state.grid} onChange={this.handleChange}>
                                        {!this.state.isReferentialLoading && Object.keys(this.state.referential.grid).map((grid) => (
                                            <option key={grid} value={grid}>{grid}</option>
                                        ))}
                                    </select>
                                </div>
                            </div>
                            <div className="col">
                                <button type="submit" className="btn btn-danger">Create Encounter</button>
                            </div>
                        </div>
                    </form>
                </div>
            </Layout>
        );
    }
}