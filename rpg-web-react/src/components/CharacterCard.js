import heart from '../assets/heart-pulse.svg'
import force from '../assets/fist-force.svg'
import dexterity from '../assets/gym-dexterity.svg'
import intelligence from '../assets/brain-intelligence.svg'
import brain from '../assets/brain.svg'
import {Link} from "react-router-dom";

export default function CharacterCard({character, encounter}) {
    return (
        <div className="card">
            <div className="card-body">
                <h5 className="card-title">
                    {encounter?.combatLog?.winner?.id === character.id &&
                        <>
                            <button type="button" className="btn btn-warning" disabled>
                                <img src="/images/trophy-fill.svg" alt="trophy"/> Winner
                            </button>
                            &nbsp;
                        </>
                    }

                    <span>{character.name}</span>
                    {
                        encounter !== null && <Link to={'/characters/' + character.id}>
                            <img src="/images/go-to.svg" width="30" height="24" alt="go to icon"/>
                        </Link>
                    }

                </h5>
                <h6 className="card-subtitle mb-2 text-muted">
                    {character.species} {character.profiles}
                </h6>
                <div className="row">
                    <div className="col">
                        <img src={heart} title="Hit Points" alt="hit point icon" width="30"
                             height="24"/>
                        &nbsp;
                        <span>{character.hitPoints} / {character.maxHitPoints}</span>
                        <br/>
                        <br/>
                        <img src={force} title="Force" alt="fist for force icon" width="30"
                             height="24"/>
                        &nbsp;
                        <span>{character.statistics['FORCE']}</span>
                        <br/>
                        <br/>
                        <img src={dexterity} title="Dexterity"
                             alt="gym for dexterity icon" width="30"
                             height="24"/>
                        &nbsp;
                        <span>{character.statistics['DEXTERITY']}</span>
                        <br/>
                        <br/>
                        <img src={intelligence} title="Intelligence"
                             alt="brain for intelligence icon"
                             width="30" height="24"/>
                        &nbsp;
                        <span>{character.statistics['INTELLIGENCE']}</span>
                        <br/>
                        <br/>
                    </div>
                    <div className="col">
                        {character.skills.map((skill) => (
                            <div key={skill}>
                                <img src={"/images/skills/" + skill.toLowerCase() + ".svg"}
                                     title={skill.description}
                                     alt="skill" width="30" height="24"/>
                                <span>{skill}</span>
                                <br/>
                            </div>
                        ))}
                        {character.brain !== undefined && character.brain !== null &&
                            <div>
                                <img src={brain} title="brain" alt="brain" width="30" height="24"/>
                                <span>{character.brain.type}</span>
                                <br/>
                            </div>
                        }
                    </div>
                </div>
                <div className="row">
                    &nbsp;
                </div>
                <div className="row">
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">Equipment</h5>
                            <table className="table">
                                <thead>
                                <tr>
                                    <th>Slot</th>
                                    <th>Name</th>
                                    <th>attrition</th>
                                </tr>
                                </thead>
                                <tbody>
                                {Object.keys(character.equipment).map((key, index) => (
                                    <tr key={index}>
                                        <td>{key}</td>
                                        <td>{character.equipment[key].name}</td>
                                        <td>{character.equipment[key].attrition} %</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}