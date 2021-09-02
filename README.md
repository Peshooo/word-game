A game inspired by typing tests. You will see 10 words on a canvas, each moving in its own direction with its own speed. The moment a word hits the canvas' border, it disappears and another word replaces it on a random place moving in a random direction with random speed. The moment you type a word currently on the canvas (and hit space or enter), the same thing happens but the number of letters in that word is added to your score. There are two modes - Standard and Survival. In Standard mode you simply have 60 seconds to get the maximum score possible. In Survival mode you start with 10 seconds and for each correct word you receive a bonus second but you will never have more than 10 seconds.

Running it locally is simple:

First, launch a mysql instance, on which the record-storage depends:

`docker run -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=game_records mysql`

Then, from the kafka-local storage, spin up the kafka real quick, on which both the record-storage and the game-server depend:

`docker-compose up --remove-orphans`

Then start the records-storage from the records-storage directory:

`mvn spring-boot:run -Dspring-boot.run.profiles=local`

Then launch a redis, on which the game-server depends:

`docker run -p 6379:6379 redis`

Then start the game-server from the game-server directory:

`mvn spring-boot:run -Dspring-boot.run.profiles=local`

Finally, from the web-ui directory:

`mvn spring-boot:run -Dspring-boot.run.profiles=local`

Now open `http://localhost:8080/` and enjoy.