db = db.getSiblingDB('rpg-arena');
db.users.insertOne({name: "Ada Lovelace"});
db.createUser(
    {
        user: "rpg-arena",
        pwd: "change-me",
        roles: [
            {
                role: "readWrite",
                db: "rpg-arena"
            }
        ]
    }
);
