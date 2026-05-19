# PetStore API - Testes de Automação 🐱


![API Tests](https://github.com/kamilajaa/PetStoreAPITest.java/actions/workflows/api-tests.yml/badge.svg)

> Projeto de automação de testes de API usando Java + Maven + REST-assured + JUnit 5

## O que esse projeto faz?

Esse projeto testa a API pública **PetStore** (https://petstore.swagger.io). Ele simula um fluxo completo de venda:

1. Cadastra o gato **"Bichento"**
2. Cadastra a usuária **"Hermione Granger"**
3. Cria uma **ordem de venda** do gato para a usuária
4. Consulta o **status do pet** após a venda
5. Consulta a **ordem de venda**
6. Analisa se há algo **estranho ou incompleto** na transação

## Tecnologias usadas

- **Java 21** - linguagem principal
- **Maven** - gerenciador de dependências e build
- **REST-assured** - pra fazer as chamadas HTTP (GET, POST, PUT)
- **JUnit 5** - pra rodar os testes em ordem
- **Gson** - pra converter objetos Java em JSON

## Estrutura de pastas

```
PetStoreAPITest.java/
├── pom.xml                              ← dependências do Maven
├── src/
│   └── test/
│       └── java/
│           └── petstore/
│               ├── Pet.java              ← modelo do gato
│               ├── User.java             ← modelo da usuária
│               ├── StoreOrder.java       ← modelo da ordem de venda
│               └── PetStoreTest.java     ← testes (4.1 a 4.6)
└── .gitignore
```

## Como rodar os testes

### 1. Pré-requisitos

- Java 21 instalado
- Maven instalado
- Conexão com internet (a API é online)

### 2. Instalar dependências

```bash
mvn clean install
```

### 3. Rodar os testes

```bash
mvn test
```

Ou direto pelo VS Code: clica na classe `PetStoreTest.java` e aperta o botão de **Run**.

## O que cada teste faz?

| # | Método | Descrição |
|---|---|---|
| 4.1 | `postPet()` | Cadastra o gato "Bichento" na API |
| 4.2 | `postUser()` | Cadastra a usuária "Hermione Granger" |
| 4.3 | `postOrder()` | Cria ordem de venda do gato pra usuária |
| 4.4 | `getPet()` | Consulta o pet após a venda |
| 4.5 | `getOrder()` | Consulta a ordem de venda |
| 4.6 | `analiseTransacao()` | Aponta o que está estranho/incompleto |

## ⚠️ Aviso importante

A API PetStore (`https://petstore.swagger.io`) é **pública e gratuita**, então às vezes fica fora do ar (erro 502 Bad Gateway). Se isso acontecer, é só esperar alguns minutos e tentar de novo.

## Autor

Feito durante estudos de automação de API com Java no curso Iterasys.
