export default function SpellBook({spellbook}) {
    return (
        <div className="card">
            <div className="card-body">
                <h5 className="card-title">Spells</h5>
                <table className="table">
                    <thead>
                    <tr>
                        <th>Level</th>
                        <th>Name</th>
                        <th>Damages</th>
                        <th>HP cost</th>
                    </tr>
                    </thead>
                    <tbody>
                    {spellbook.map((spell, index) => (
                        <tr key={index}>
                            <td>level</td>
                            <td>{spell}</td>
                            <td>damages</td>
                            <td>costs</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    )
}