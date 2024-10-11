<div align="center">

# Mediotec+

</div>

<div align="center">
<img src="https://i.ibb.co/nRV9jcj/Design-sem-nome.png">
</div>

## 📜 Descrição

Mediotech+ é um sistema web desenvolvido para coordenadores, professores e estudantes da escola Mediotec, que visa facilitar a organização e monitoramento de aulas, matérias, estudantes e notas. Esse repositório corresponde ao **Backend** do projeto.

O projeto é composto por vários repositórios, onde cada um é responsável por diferentes aspectos do sistema. Abaixo, está uma lista dos repositórios e suas funções:

- [**Repositório Frontend**](https://github.com/miguelamaral254/mediotec-frontend): Esse é repositório que contém o Frontend do projeto.

## 🛠️ Construído com

<div style="display: flex; align-items: center; gap: 16px;">
<a href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html" target="_blank">
<img src="https://www.svgrepo.com/show/184143/java.svg" alt="Java 17" style="width: 48px; height: 48px;">
</a>

<a href="https://maven.apache.org/" target="_blank">
<img src="https://www.svgrepo.com/show/373829/maven.svg" alt="Maven" style="width: 48px; height: 48px;">
</a>

<a href="https://spring.io/projects/spring-boot" target="_blank">
<img src="https://www.svgrepo.com/show/376350/spring.svg" alt="Spring Boot" style="width: 48px; height: 48px;">
</a>

<a href="https://www.postgresql.org/" target="_blank">
<img src="https://www.svgrepo.com/show/354200/postgresql.svg" alt="PostgreSQL" style="width: 48px; height: 48px;">
</a>

<a href="https://jakarta.ee/specifications/persistence/" target="_blank">
<img src="https://www.svgrepo.com/show/500908/jpa.svg" alt="JPA" style="width: 48px; height: 48px;">
</a>

<a href="https://projectlombok.org/" target="_blank">
<img src="https://kodejava.org/wp-content/uploads/2018/12/lombok.png" alt="Lombok" style="width: 48px; height: 48px;">
</a>

<a href="https://junit.org/junit5/" target="_blank">
<img src="https://www.svgrepo.com/show/330758/junit5.svg" alt="JUnit 5" style="width: 48px; height: 48px;">
</a>

<a href="https://site.mockito.org/" target="_blank">
<img src="https://miro.medium.com/v2/resize:fit:640/format:webp/1*4ezoav544ciIcSAa67ci1w.png" alt="Mockito" style="width: 80px; height: 48px;">
</a>

<a href="https://jwt.io/" target="_blank">
<img src="https://cdn.worldvectorlogo.com/logos/jwt-3.svg" style="width: 48px; height: 48px;">
</a>

</div>

- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) - Linguagem de programação.
- [Maven 3.9.9](https://maven.apache.org/) - Gerenciador de dependências
- [PostgreSQL](https://www.postgresql.org/) - Sistema gerenciador de banco de dados.
- [Spring Boot 3.3.3](https://spring.io/projects/spring-boot) - Framework para construção das aplicações
- [JPA](https://jakarta.ee/specifications/persistence/) - API para mapeamento objeto-relacional (ORM).
- [Spring Security](https://spring.io/projects/spring-security) - Framework de autenticação e autorização.
- [Spring Web](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html) - Framework para construção das aplicações web e APIs REST.
- [SpringDoc OpenAPI](https://springdoc.org/) - Ferramenta para geração de documentação OpenAPI para APIs Spring Boot.
- [Lombok](https://projectlombok.org/) - Biblioteca para simplificação do código Java com anotações.
- [DevTools](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using.devtools) - Ferramenta de desenvolvimento para facilitar o processo de desenvolvimento e depuração.
- [JUnit 5](https://junit.org/junit5/) - Framework de testes para Java.
- [Mockito](https://site.mockito.org/) - Biblioteca de simulação para testes unitários.
- [JWT (JSON Web Token)](https://jwt.io/) - Padrão para autenticação

## 🖇️ Colaborando

Por favor, leia o [COLABORACAO.md](https://github.com/miguelamaral254/mt-auth-service/blob/main/COLABORACAO.md) para obter detalhes sobre o nosso código de conduta e o processo para nos enviar pedidos de solicitação.

## ⌨️ Como rodar o projeto

### Pré-requisitos

Antes de rodar o projeto, você precisará garantir que os seguintes itens estejam instalados:

- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/download/)

### Configuração do PostgreSQL

Certifique-se de que o PostgreSQL está instalado e rodando. Crie um banco de dados e configure as credenciais no arquivo `application.properties` ou `application.yml` (os detalhes da configuração de conexão com o banco de dados estão mais abaixo).

## Configuração do Projeto

Siga os passos abaixo para rodar o projeto localmente:

### 1. Clonar o repositório

Clone este repositório para o seu ambiente local usando o comando:

### 2. Navegar até o diretório do projeto

Entre no diretório do projeto:

### 3. Configurar o banco de dados

Atualize as informações de conexão com o PostgreSQL no arquivo `src/main/resources/application.properties` ou `application.yml` conforme necessário:

properties
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

### 4. Compilar e rodar o projeto

Compile e rode o projeto com o Maven:

O servidor estará rodando em `http://localhost:8080`.

## Endpoints

A aplicação expõe APIs REST que podem ser acessadas via HTTP. Use ferramentas como [Postman](https://www.postman.com/) para testar os endpoints.

---

Agora seu projeto Spring Boot estará rodando localmente, conectado ao PostgreSQL.

## 📌 Versão

Nós usamos [SemVer](http://semver.org/) para controle de versão.

## ✒️ Autores

Esses são os membros que ajudaram a desenvolver o projeto desde o seu início

- **Miguel Amaral** - *Desenvolvimento Backend*: [Github](https://github.com/miguelamaral254) - [Linkedin](https://linkedin.com/in/miguelamaral254/)
- **Weslley Santana** - *Desenvolvimento Backend e repositório*: [Github](https://github.com/wsllyz) - [Linkedin](https://linkedin.com/in/wessantana)
- **Júlio Cesar** - *Desenvolvimento Frontend*: [Github](https://github.com/JulioCesarAguiar) - [Linkedin](https://www.linkedin.com/in/j%C3%BAlio-cesar-aguiar-25a0b6277/)
- **Walter Cabral** - *Desenvolvimento Frontend*: [Github](https://github.com/linkParaPerfil) - [Linkedin](https://www.linkedin.com/in/walter-cabral-251341237/)
- **Tairone Albuquerque** - *Design*: [Github](https://github.com/TaironeAlbuquerque) - [Linkedin](https://linkedin.com/in/taironealb/)
- **João Pedro Evangelista** - *Documentação*: [Github](https://github.com/jotapedevs) - [Linkedin](https://www.linkedin.com/in/joaoevangelistadev/)

## 📄 Licença

Este projeto está sob a licença MIT - veja o arquivo [LICENSE.md](https://github.com/miguelamaral254/mt-auth-service/blob/main/LICENSE) para detalhes.

## 🎁 Agradecimentos

- Agradecemos imensamente ao professor [Geraldo Gomes](https://github.com/geraldo7junior) por todo apoio e ensinamentos até aqui!
