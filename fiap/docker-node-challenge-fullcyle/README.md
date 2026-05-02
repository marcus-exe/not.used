# Full Cycle - Nginx with Node.js

## In this docker compose project, 3 containers are used:

1) MySQL (db)
   - database (people and id)
2) Node (node)
   - displays the front page
   - access database
   - register information
3) Nginx (nginx)
   - acts as an in-between from port 8080 to port 3000

## To run in your machine: 
```
docker compose up -d 
```
_disclaimer: I still haven't fixed the sql log messages from healthcheck_
