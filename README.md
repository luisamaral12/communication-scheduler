# Communication Scheduler API

Desafio técnico Luizalabs para construção de API para consultar, agendar ou cancelar o agendamento do envio de mensagens.

#### Informações Gerais
##### Tecnologias utilizadas:

- **Java 8**
- **Spring Boot 2.5.4**
- **Lombok** 
- **Swagger 3.0.0**
- **MySQL 8.0.26** [ambiente de produção]
- **H2** [ambiente de teste]
- **Docker**
- **JUnit5**


#### Instalação
* Baixe e instale o [Docker](https://www.docker.com/);


* Após instalação e configuração do docker, execute o comando abaixo para subir o banco de dados:

> `docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=Luizalabs -e MYSQL_USER=magalu -e MYSQL_PASSWORD=magalu mysql:8.0.26`

* Clone esse repositório para seu diretório local:

> `gh repo clone luisamaral12/communication-scheduler`

* No diretório onde o projeto foi clonado, gere o artefato através do Maven:

> `mvn clean package`

* Navegar até a pasta do projeto e executar o comando abaixo para subí-lo em ambiente local:

> `java -jar target/communication-0.0.1-SNAPSHOT.jar`

#### Execução
Após instalado, a API poderá ser acessada através do link:

> `http://localhost:8080/luizalabs/swagger-ui/index.html#/`

#### Validações
Ao buscar um agendamento por seu identificador, a API verificará se:
1. O agendamento referente ao identificador existe na base de dados;

Ao solicitar um novo agendamento, a API verificará se:
1. Todos os campos foram preenchidos;
2. Um e-mail ou celular válido foram informados;
3. Um tipo de comunicação válido foi informado (EMAIL, SMS, WHATSAPP, PUSH);
4. A data de agendamento não é anterior à data atual;

Ao solicitar o cancelamento de um agendamento, a API verificará se:
1. O agendamento referente ao identificador existe na base de dados;
2. O agendamento já fora cancelado;

#### License
[GNU GENERAL PUBLIC LICENSE Version 3](https://www.gnu.org/licenses/gpl-3.0)