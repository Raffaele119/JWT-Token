# JWT-Token
richiedo un token: curl -X POST http://localhost:8080/api/login -H "Content-Type: application/json" -d "{\"username\":\"user\",\"password\":\"password\"}"

alla pagina con il json ci posso accedere tutti, al contrario servirà avere un token per accedere agli endpoint DELETE e PUT

DELETE
curl -X DELETE http://localhost:8080/api/exercises/5 -H "Authorization: Bearer TUO_TOKEN

PUT
curl -X POST http://localhost:8080/api/exercises -H "Content-Type: application/json" -H "Authorization: Bearer TUO_TOKEN" -d "{\"name\":\"Nuovo esercizio\",\"description\":\"Descrizione\",\"muscleGroup\":\"braccia\"}"

metodi PUT e DELETE sono protetti da token, GET e GET by id sono invece accessibili a tutti
