# 🌱 ReciclaVille - API de Compensação de Carbono

O **ReciclaVille** é uma plataforma desenvolvida para facilitar o gerenciamento de **compensação de carbono** para empresas parceiras. A aplicação permite que os usuários cadastrem declarações de embalagens utilizadas, calculem metas de reciclagem para compensar emissões de carbono e acompanhem os materiais declarados.

Este repositório contém o código do **MVP (Minimum Viable Product)** da aplicação **Back-End**, desenvolvido em **Java** com **Spring Boot** e **PostgreSQL**, seguindo os padrões REST e as boas práticas de desenvolvimento.

---

## 🚀 Funcionalidades

A API disponibiliza os seguintes recursos:

### 📦 Materiais
- Cadastro de novos materiais
- Consulta de materiais
- Atualização de materiais
- Exclusão de materiais

### 🧑 Clientes
- Cadastro de novos clientes
- Consulta de clientes
- Atualização de clientes
- Exclusão de clientes

### 📄 Declarações
- Inclusão de novas declarações
- Cálculo da meta de compensação de carbono 
- Consulta de declarações
- Exclusão de declarações

---

## ⚙️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**

---

## 📐 Padrões e Boas Práticas

- API RESTful com uso adequado de métodos HTTP (`GET`, `POST`, `PUT`, `DELETE`)
- Estrutura de URLs seguindo convenções REST (`/clients`, `/materials`, `/statements`)
- Utilização de DTOs para requisições e respostas
- Separação de camadas (Controller, Service, Repository)
- Convenções de código Java e boas práticas de clean code

