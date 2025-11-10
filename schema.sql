-- ============================================================
--  Projeto: User CRUD API
--  Autor: Eduardo Augusto
--  Descrição: Script de criação do banco de dados e tabela
--             de usuários para o projeto de CRUD.
--  Banco de Dados: PostgreSQL
--
-- OBS: Leia TODO O ARQUIVO antes de executar
-- ============================================================

-- ============================================================
-- 1 - CRIAÇÃO DO BANCO DE DADOS
-- ------------------------------------------------------------
-- Este comando cria o banco de dados "usercruddb".
-- Execute este comando no terminal SQL do PostgreSQL (psql)
-- ou em uma ferramenta como pgAdmin, antes de rodar o projeto.
-- ============================================================
CREATE DATABASE usercruddb;

-- ============================================================
-- 2 - CONEXÃO COM O BANCO DE DADOS
-- ------------------------------------------------------------
-- Após criar o banco, conecte-se a ele via o terminal SQL do
-- PostgreSQL (psql) com:
-- \c usercruddb;
-- ============================================================

-- ============================================================
-- 3 - CRIAÇÃO DA TABELA DE USUÁRIOS
-- ------------------------------------------------------------
-- A tabela "usuarios" contém os campos:
--   - id: chave primária (gerada automaticamente)
--   - nome: nome completo do usuário
--   - email: deve ser único (não pode haver repetição)
--   - senha: senha criptografada com BCrypt (armazenada no back-end)
-- ============================================================
CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL
);

-- ============================================================
-- 4 - OBSERVAÇÕES IMPORTANTES
-- ------------------------------------------------------------
-- - O back-end (Spring Boot) utiliza BCrypt para criptografar as senhas.
--
-- - Caso deseje inserir usuários de teste manualmente, utilize:
--   INSERT INTO usuarios (nome, email, senha)
--   VALUES ('Usuário Teste', 'teste@example.com', '$2a$10$hashDaSenhaAqui');
--
--   OBS: A senha acima deve estar criptografada com BCrypt.
--
-- - Não defina senhas reais neste arquivo — use apenas dados fictícios.
--
-- - O Spring Boot fará o mapeamento desta tabela automaticamente
--   através do JPA (Hibernate).
-- ============================================================

-- ============================================================
-- 5 - COMO EXECUTAR
-- ------------------------------------------------------------
-- 1. Abra o terminal do PostgreSQL (psql) ou o pgAdmin.
-- 2. Execute este arquivo SQL (Use barras normais "/"):
--      \i 'CAMINHO/ATE/schema.sql';
-- 3. Confirme que o banco e a tabela foram criados:
--      \c usercruddb;
--      \dt;
-- 4. Após isso, faça uma cópia do application-template.properties,
--    renomeie-a para application.properties e configure o projeto:
--
--      spring.datasource.url=jdbc:postgresql://localhost:5432/usercruddb
--      spring.datasource.username=postgres
--      spring.datasource.password=SUA_SENHA_AQUI
--
-- 5. Rode o projeto no STS ou IntelliJ com o comando:
--      mvn spring-boot:run
--
-- 6. O back-end iniciará em:
--      http://localhost:8080
-- ============================================================
