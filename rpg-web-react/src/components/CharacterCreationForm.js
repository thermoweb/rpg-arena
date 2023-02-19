import React from 'react';
import axios from 'axios';
import { Navigate } from "react-router-dom";

import Layout from "./Layout";

export default class CharacterCreationForm extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            isReferentialLoading: true,
            id: '',
            name: '',
            species: 'GNOME',
            profile: 'WIZARD',
            strength: 'INTELLIGENCE',
            intermediate: 'DEXTERITY',
            weakness: 'FORCE',
            created: false
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        axios.get('http://localhost:8084/referential')
            .then((response) => {
                this.setState({referential: response.data, isReferentialLoading: false});
            })
    }

    handleChange(event) {
        this.setState({[event.target.name]: event.target.value});
    }

    handleSubmit(event) {
        axios.post('http://localhost:8084/characters', {
            name: this.state.name,
            statistics: {
                weakness: this.state.weakness,
                intermediate: this.state.intermediate,
                strength: this.state.strength
            },
            species: this.state.species,
            profile: this.state.profile
        })
            .then((response) => {
                this.setState({created: true, id: response.data.id}); // id: response.data.id,
            })
            .catch(function (error) {
                console.log(error);
            });
        event.preventDefault();
    }

    renderAbilities() {
        return (
            <>
                {!this.state.isReferentialLoading && this.state.referential.abilities.map((ability) => (
                    <option key={ability} value={ability}>{ability}</option>
                ))}
            </>
        )
    }

    render() {
        if (this.state.created) {
            return <Navigate to={"/characters/" + this.state.id} replace={true} />
        }
        return (
            <Layout>
                <div className="container">
                    <br/><br/>
                    <div className="card">
                        <div className="card-header">Create new Character</div>
                        <div className="card-body">
                            <form onSubmit={this.handleSubmit}>
                                <div className="row g-3 align-items-center">
                                    <div className="col">
                                        <div className="input-group mb-3">
                                            <span className="input-group-text" id="basic-addon1">Character's name</span>
                                            <input type="text" className="form-control" placeholder="Name"
                                                   aria-label="Name" value={this.state.name}
                                                   onChange={this.handleChange}
                                                   aria-describedby="basic-addon1" name="name"/>
                                        </div>
                                    </div>
                                    <div className="col">
                                        <div className="input-group mb-3">
                                            <label className="input-group-text" htmlFor="Profile">Profile</label>
                                            <select className="form-select" id="Profile" name="profile"
                                                    value={this.state.profile} onChange={this.handleChange}>
                                                {!this.state.isReferentialLoading && this.state.referential.profiles.map((profile) => (
                                                    <option key={profile} value={profile}>{profile}</option>
                                                ))}
                                            </select>
                                        </div>
                                    </div>
                                    <div className="col">
                                        <div className="input-group mb-3">
                                            <label className="input-group-text" htmlFor="Species">Species</label>
                                            <select className="form-select" id="Species" name="species"
                                                    value={this.state.species} onChange={this.handleChange}>
                                                {!this.state.isReferentialLoading && this.state.referential.species.map((species) => (
                                                    <option key={species} value={species}>{species}</option>
                                                ))}
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col">
                                        <div className="input-group mb-3">
                                            <label className="input-group-text" htmlFor="Strength">Strength</label>
                                            <select className="form-select" id="Strength" name="strength"
                                                    value={this.state.strength} onChange={this.handleChange}>
                                                {this.renderAbilities()}
                                            </select>
                                        </div>
                                    </div>
                                    <div className="col">
                                        <div className="input-group mb-3">
                                            <label className="input-group-text"
                                                   htmlFor="Intermediate">Intermediate</label>
                                            <select className="form-select" id="Intermediate" name="intermediate"
                                                    value={this.state.intermediate} onChange={this.handleChange}>
                                                {this.renderAbilities()}
                                            </select>
                                        </div>
                                    </div>
                                    <div className="col">
                                        <div className="input-group mb-3">
                                            <label className="input-group-text" htmlFor="Weakness">Weakness</label>
                                            <select className="form-select" id="Weakness" name="weakness"
                                                    value={this.state.weakness} onChange={this.handleChange}>
                                                {this.renderAbilities()}
                                            </select>
                                        </div>
                                    </div>
                                    <button type="submit" className="btn btn-primary">Create</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </Layout>
        );
    }
}