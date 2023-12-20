# Desafio desenvolvedor full-stack 3wings [API](https://3wings-full-stack-challenge-production.up.railway.app/swagger-ui/index.html#/)

Uma API feita em Spring boot que realiza o gerenciamento de posts de blog, em forma de CRUD(Create, Read, Update, Delete)

[![](https://mermaid.ink/img/pako:eNptkE1rwzAMhv-K0XUmxPloEt8GPW5Q6G34YmwvM0vsYivQLuS_z0kLC6y6SHpe8SJpBuW1AQ5qkDEereyDHIUjKTZCTj4ime9kjRfy5l1PrN6jMwabIFoczBMeh6l_gpV3aBzulaNEQ1QwKenX_8p00XtlAQqjCaO0Ol2wLSkAv8xoBPBUahm-BQi3zskJ_fnmFHAMk6Fwt3ocDPxTDjFRoy368P54yZooXKT78P5vJvXAZ7gCZ2WTtV3XsrzpWM7qQ0HhlnCXMcbaujhUddtUeVEuFH42C5aVdZE3rKnKMl_VYvkFub1wcg?type=png)](https://mermaid.live/edit#pako:eNptkE1rwzAMhv-K0XUmxPloEt8GPW5Q6G34YmwvM0vsYivQLuS_z0kLC6y6SHpe8SJpBuW1AQ5qkDEereyDHIUjKTZCTj4ime9kjRfy5l1PrN6jMwabIFoczBMeh6l_gpV3aBzulaNEQ1QwKenX_8p00XtlAQqjCaO0Ol2wLSkAv8xoBPBUahm-BQi3zskJ_fnmFHAMk6Fwt3ocDPxTDjFRoy368P54yZooXKT78P5vJvXAZ7gCZ2WTtV3XsrzpWM7qQ0HhlnCXMcbaujhUddtUeVEuFH42C5aVdZE3rKnKMl_VYvkFub1wcg)


### Tecnologias Utilizadas

Exemplo:
* [Java](https://docs.oracle.com/en/java/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Mockito](https://site.mockito.org/)
* [Docker](https://www.docker.com/)
* [MySQL](https://www.mysql.com/)

## Dependências e Versões Necessárias

* Docker - Versão: 24.0.7
* Java - Versão: 17
* Spring - Versão: 3.2.0
* MySQL - Versão: 8.0.35
  
### Como rodar o projeto

**OBS:** Para rodar você deve mudar as configurações do ```application.yml``` que fica em ```main/resource``` para suas configurações de banco ou passar as variáveis de ambiente com as configurações do seu **MySQL**. Tenha certeza de que está tudo ligado e não esqueça de informar um banco existente.

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/<seu_banco_de_dados_criado>?useSSL=false&serverTimezone=UTC
    username: <seu_usuário>
    password: <sua_senha>
  jpa:
    hibernate:
      ddl-auto: update
```

*Usando mvn*

```
mvn spring-boot:run
```

*Usando jar*

```
mvn clean package
```

Depois rode

```
java -jar target/wings-blog-0.0.1-SNAPSHOT.jar
```

*Usando docker-compose*

```
docker compose up -d
```

> Depois disso seu projeto estará rodando em [localhost:8080](http://localhost:8080).
> Você pode chegar a documentação em Swagger em [localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

## Como rodar os testes

**OBS:** Se quiser rodar os testes de integração tenha certeza que seu servidor MySQL está rodando e todos os parametros de conexão foram configurados, Pois ele levanta o contexto da aplicação

*Para rodar sem os testes de integração*

```
mvn clean install -DskipITs
```

```
mvn test
```

*Para rodar com testes de integração

```
mvn test
```

## Rotas Http

#### Listar todos os posts
> GET

```/posts```

Retorno:

```json
[
  {
    "id": 1,
    "slug": "this-is-the-way",
    "title": "This is The Way",
    "content": "I can bring your in warm, or I can bring in cold",
    "createdAt": "2023-12-15T22:56:23.397+00:00",
    "updatedAt": "2023-12-15T22:56:23.397+00:00"
  },
  {
    "id": 2,
    "slug": "luke-im-your-father",
    "title": "Luke I'm your father",
    "content": "NOOOOOOOOO",
    "createdAt": "2023-12-15T22:52:40.949+00:00",
    "updatedAt": "2023-12-15T22:52:40.949+00:00"
  }
]
```

#### Listar um post pelo ID
> GET

```/posts/{id}```

Retorno:

```json

{
  "id": 1,
  "slug": "this-is-the-way",
  "title": "This is The Way",
  "content": "I can bring your in warm, or I can bring in cold",
  "createdAt": "2023-12-15T22:56:23.397+00:00",
  "updatedAt": "2023-12-15T22:56:23.397+00:00"
},
```

#### Criar um post
> POST

```/posts```
* Body:

```json
{
  "title": "I'm Iron man",
  "content": "Senhor Stark?"
}
```

Retorno:
Status ```201 Created```

#### Atualizar um post
> PUT

```/posts/{id}```

* Body:

```json
{
  "title": "I'm Iron man",
  "content": "Senhor Stark?"
}
```

Retorno:
Status ```204 No Content```

#### Deletar um post
> DELETE

```/posts/{id}```

Retorno:
Status ```204 No Content```

## Problemas enfrentados

### Problema 1:
Rodar os testes de integração
* Como solucionar: Verifique se seu servidor de banco de dados está em execução e verifique se os parametros da aplicação no application.yml ou nas variaveis de ambiente estão corretas.

### Problema 2:
Problemas ao rodar o docker
* Como solucionar: Se você está usando windows verifique se o docker possui todas as permissões necessárias na sua máquina.

## Próximos passos

1- Realizar testes de carga e deixar a aplicação ainda mais poderosa


