import {Link} from "react-router-dom";
import {renderEncountersActions, renderStatus} from "../common/encounters.js";

export default function EncounterList({encounters}) {

    return (
        <>
            <br/>
            <Link to='/encounters/create' className="btn btn-danger " role="button" aria-disabled="true">
                Create Encounter
            </Link>
            <table className="table">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Status</th>
                    <th scope="col">Characters</th>
                    <th scope="col">Grid</th>
                    <th scope="col">Last Modified</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>
                {encounters.map((encounter) => (
                    <tr key={encounter.id}>
                        <td>
                            <Link key={encounter.id} to={'/encounters/' + encounter.id}>{encounter.id}</Link>
                        </td>
                        <td>
                            {renderStatus(encounter.status)}
                        </td>
                        <td>
                            {encounter.characters.map((character) => (
                                <span key={character.id}>
                                <Link key={character.id} className="btn btn-outline-secondary"
                                      to={'/characters/' + character.id}>
                                    {encounter.combatLog?.winner?.id === character.id &&
                                        <span><img src="/images/trophy.svg" alt="trophy"/>&nbsp;</span>}
                                    {character.name}
                                </Link>
                                    &nbsp;
                            </span>
                            ))}
                        </td>
                        <td>{encounter.grid}</td>
                        {/*<td th:unless="${#dates.format(#dates.createNow(), 'ddMMyyyy') == #temporals.format(encounter.lastModified(), 'ddMMyyyy')}"
                        th:text="${#temporals.format(encounter.lastModified(), 'dd/MM/yyyy HH:mm')}"></td>
                    <td th:if="${#dates.format(#dates.createNow(), 'ddMMyyyy') == #temporals.format(encounter.lastModified(), 'ddMMyyyy')}"
                        th:text="${#temporals.format(encounter.lastModified(), 'HH:mm')}"></td>*/}
                        <td>{encounter.lastModified}</td>
                        <td>
                            {/*{renderEncountersActions(encounter)}*/}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </>
    )
}