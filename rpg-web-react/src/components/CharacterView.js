import CharacterCard from "./CharacterCard";
import SpellBook from "./SpellBook";
import EncounterList from "./EncounterList";

function CharacterView({character}) {
    return (
        <div className="container">
            <br/>
            <div className="row">
                <div className="col">
                    <CharacterCard key={character.id} character={character}></CharacterCard>
                </div>
                <div className="col">
                    <div className="row">
                        {character.spellbook?.length > 0 && <SpellBook spellbook={character.spellbook}/>}
                    </div>
                </div>
            </div>
            <EncounterList encounters={character.encounters} />
        </div>
    )
}

export default CharacterView;