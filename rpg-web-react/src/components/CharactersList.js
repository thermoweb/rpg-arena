import loadingBar from '../assets/loading-bar.gif'
import {Link} from "react-router-dom";
function CharactersList({data, error, loading}) {
    return (
        <div className="container">
            <br/>
            <a href="/characters/create" className="btn btn-danger " role="button"
               aria-disabled="true">New Character</a>
            <table className="table">
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Profile</th>
                    <th scope="col">Species</th>
                    <th scope="col">Hit Points</th>
                </tr>
                </thead>
                <tbody>
                    {loading && <tr><td colSpan={4}><img src={loadingBar} alt={'loading'}/></td></tr>}
                    {!loading && data.map((character) => (
                        <tr key={character.id}>
                            <td><Link to={'/characters/' + character.id}>{character.name}</Link></td>
                            <td>{character.profiles}</td>
                            <td>{character.species}</td>
                            <td>{character.maxHitPoints}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}

export default CharactersList

