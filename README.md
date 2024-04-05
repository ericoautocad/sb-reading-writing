# Rodando o start do projeto sem docker
Após ter instalado em sua máquina local o gerenciar de mongodb server de sua preferencia, crie na conexão local a base de dados: 
db-source-db.

Crie na base criada a collection "db-source-db.trips" e importe nesta collection o conteúdo presente no arquivo "trips.json" que consta no diretório: 
src/main/resources. 

Agora é só rodar a aplicação para verificar os registros que são filtrados de "db-source-db.trips" e inseridos na colection "db-target-ms.trip_chosen". 

# Rodando o start do projeto com docker
Após rodar os containers do docker localmente com o comando: docker compose -f **"docker-compose.yml" up -d --build**, verifique se os containers estão de pé. 

Realizer a conexão com sua ferramenta de gerencimento de mongodb no container **db-source-ms** com a seguinte connection string: **mongodb://admin:p1c4d1nh0@localhost:27018/**. 
Faça o import do arquivo que irá prover dados para a collection "source", ou seja conect usando a conexão citada acima, crie o banco de dado **db-source-ms** e neste solicite a 
criação da collection: **db-source-ms.trips**. Importe nesta collection o arquivo **trips.json** que consta no diretório: src/main/resources.

Como estamos utilizando as mesmas credencias para os containers: **db-source-ms** e **db-target-ms**, apenas ajuste no arquivo **application.properties** as portas das das conexões do mongo, 
conforme o exemplo abaixo: 
    spring.data.mongodb.primary.port=27018
    spring.data.mongodb.secondary.port=27019

Agora é só rodar a aplicação para verificar os registros que são filtrados de **db-source-db.trips** e inseridos na colection **db-target-ms.trip_chosen**. Para verificar se de fato 
a inserção de registro foi executada no banco de destino, realizer a conexão com sua ferramenta de gerencimento de mongodb no container **db-target-ms** com a seguinte connection string: **mongodb://admin:p1c4d1nh0@localhost:27019/**. Agora basta verificar se foram inseridos os regitros em: **db-target-ms.trip_chosen**