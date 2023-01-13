This repository is still in `work in progress`.

There is 2 main modules to check and one demo module : 
- `rpg-web` is the place where you'll find most of the rest api (creating characters and encounters)
- `rpg-encounter` is the place where encounters are handled (in microservice just because I can)
- `rpg-brain-demo` is an exemple of how a remote brain could be coded in Java. But feel free to use whatever you want.

I decided to use mongoDB for fun and I think it's perfect for what I need.
You just have to create an user on your database like this : 
```sql
db.createUser({user:"rpg-arena",pwd:"password",roles:[{role:"readWrite",db:"rpg-arena"}]})
```

Also I use a personnal lib for some utils classes, you can found them here : https://github.com/thermoweb/thermoweb-framework
( it's also a wip project. don't judge me too much :prayer: )