


  

curl http://localhost:8080/teamsAvailable

curl http://localhost:8080/savearff/matches


curl -XPOST http://localhost:8080/ingest -d '{"from":"1980-81","to":"1987-88","league":2}' -H "content-type:application/json"



curl http://localhost:8080/guess -d '{"homePosition":10,"homeName":"Sporting de Gijón","awayPosition":19,"awayName":"Deportivo de La Coruña","leagueDay":5,"league":2,"numberOfColumns":1}' -H "content-type:application/json"

curl http://localhost:8080/guess/learning -d '{"homePosition":10,"homeName":"Sporting de Gijón","awayPosition":19,"awayName":"Deportivo de La Coruña","leagueDay":5,"league":2,"numberOfColumns":1}' -H "content-type:application/json"


curl -XPOST http://localhost:8080/ingestElastic -d '{"from":"1980-81","to":"1987-88","league":2}' -H "content-type:application/json"


http://localhost:8080/h2-console/login.do?jsessionid=1e3d4c6bdddc3683282d5b01cc6ba37f