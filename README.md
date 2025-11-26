# 🚀 Care Plus - Flow Harmony (Back-end SOA)

**Turma:** Engenharia de Software 3º Ano, Início: 2025/ago
**Parceria:** Care Plus (Part of Bupa)

Este repositório contém o *back-end* da solução **Care Plus - Flow Harmony**, desenvolvida como parte do Challenge para a disciplina de **Arquitetura Orientada a Serviços e Web Services**. O desafio foca na **Expansão de Serviços de Saúde Digital no APP**.

---

## 🎯 Contexto e Objetivo

O desafio da Care Plus é ampliar os serviços de saúde digital oferecidos no nosso aplicativo, alinhados ao propósito da empresa: “Ajudar as pessoas a viverem vidas mais longas, saudáveis e felizes, e criar um mundo melhor.”.

### A Solução: Flow Harmony

O **Flow Harmony** é um assistente digital de **Bem-Estar e Produtividade** focado na **prevenção do *burnout*** e na promoção do equilíbrio através do registro e acompanhamento de hábitos não clínicos e do nível de energia.

### 🚫 Restrições do Desafio

A solução **não** utiliza as seguintes abordagens, conforme as regras estabelecidas:
* Telemedicina
* Análise de sintomas
* Diagnóstico dermatológico por imagem
* Batimentos cardíacos
* Audiometria
* Fisioterapia

---

## 💻 Tecnologias e Dependências

| Categoria | Tecnologia | Detalhes |
| :--- | :--- | :--- |
| **Linguagem** | Java | JDK 17+ |
| **Framework** | Spring Boot 3.x | Desenvolvimento da API RESTful |
| **Banco de Dados** | PostgreSQL | Persistência dos dados de hábitos e usuários |
| **Versionamento DB** | Flyway | Controle de *migrations* e *schema* |
| **APIs/Comunicação** | RESTful (JSON) | Padrão de comunicação (Adoção de padrões como REST e JSON) |
| **ORM** | Spring Data JPA / Hibernate | Mapeamento Objeto-Relacional |

---

## ⚙️ Configuração e Execução

### 1. Requisitos

* JDK 17+
* Maven
* Instância local do **PostgreSQL**

### 2. Configuração do Banco de Dados

1. Crie um banco de dados vazio no PostgreSQL (ex: `careplus_db`).
2. Edite o arquivo `src/main/resources/application.properties` com as credenciais:

    ```properties
    # Configuração do PostgreSQL
    spring.datasource.url=jdbc:postgresql://localhost:5432/careplus_db
    spring.datasource.username=seu_usuario_postgres
    spring.datasource.password=sua_senha_postgres
    spring.datasource.driver-class-name=org.postgresql.Driver

    # Configuração do Flyway
    spring.flyway.enabled=true
    spring.flyway.locations=classpath:db/migration
    ```

### 3. Execução

1.  **Build:** `mvn clean install`
2.  **Run:** `mvn spring-boot:run`

**Nota:** O Flyway executará as *migrations* (`V1` e `V2`) na inicialização, criando as tabelas e inserindo os dados de teste.

---

## 🗺️ Arquitetura Orientada a Serviços (SOA)

O *back-end* segue o modelo SOA, garantindo a **organização modular baseada em serviços independentes e reutilizáveis** e a **separação clara entre camadas de apresentação, serviço e dados**.



1.  **Camada de Apresentação (Controllers):** Expõe as APIs. Garante o **Uso adequado de métodos HTTP**.
2.  **Camada de Serviço (Services):** Implementa a lógica de negócio do Flow Harmony.
3.  **Camada de Dados (Repositories):** Gerencia a **Conexão com banco de dados** e o **Controle de migrações**.
4.  **Segurança e Qualidade:** O projeto implementa **Validação de entrada** (via DTOs e Global Handler) e **Tratamento adequado de erros e exceções**.

---

## 🔌 APIs Implementadas (Endpoints RESTful)

Os serviços são expostos por *endpoints* REST, atendendo ao requisito de **Implementação de APIs RESTful**.

**Base URL:** `http://localhost:8080/api`

### 1. Serviços de Hábito (`/habitos`)

| Funcionalidade | Método | Endpoint | Retorno Esperado |
| :--- | :--- | :--- | :--- |
| **Registrar Hábito** | `POST` | `/habitos/registro` | `201 Created` (`RegistroHabitoResponseDTO`) |
| **Exibir Estatísticas** | `GET` | `/habitos/estatisticas?dias={N}` | `200 OK` (`EstatisticasResponseDTO`) |
| **Consultar Histórico** | `GET` | `/habitos/registros?dataInicial=...` | `200 OK` (Lista de Registros) |

### 2. Serviços de Energia (`/mapa-energia`)

| Funcionalidade | Método | Endpoint | Retorno Esperado |
| :--- | :--- | :--- | :--- |
| **Registrar Mapa** | `POST` | `/mapa-energia` | `201 Created` (`MapaEnergiaResponseDTO`) |

---

## 👥 Integrantes do Grupo

* **[Enzo de Oliveira Rodrigues]** - RM [553377]
* **[Rafael Cristofali]** - RM [553521]
* **[Hugo Santos]** - RM [553266]
* **[Maria Julia] ** - RM [553384]

*(Os grupos devem ser formados de 3 a 5 integrantes, no máximo.)*
