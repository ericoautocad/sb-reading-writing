
Após ter instalado em sua máquina local o gerenciar de mongodb server de sua preferencia, crie na conexão local a base de dados: 
db-source-db.

Crie na base criada a collection "db-source-db.trips" e importe nesta collection o conteúdo presente no arquivo "trips.json" que consta no diretório: 
src/main/resources. 

Agora é só rodar a aplicação para verificar os registros que são filtrados de "db-source-db.trips" e inseridos na colection "db-target-ms.trip_chosen". 