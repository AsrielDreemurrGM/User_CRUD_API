-- ============================================================
--  Projeto: User CRUD API
--  Autor: Eduardo Augusto Motta da Rosa
--  Descrição: Script de criação do banco de dados e tabela
--             de usuários para o projeto de CRUD.
--  Banco de Dados: PostgreSQL
--
-- OBS: Leia TODO O ARQUIVO antes de executar
-- ============================================================

-- ============================================================
-- 1 - CRIAÇÃO DO BANCO DE DADOS
-- ============================================================
-- Este comando cria o banco de dados "usercruddb".
-- Execute este comando no terminal SQL do PostgreSQL (psql)
-- ou em uma ferramenta como pgAdmin, antes de rodar o projeto.
-- ============================================================
CREATE DATABASE usercruddb;

-- ============================================================
-- 2 - CONEXÃO COM O BANCO DE DADOS
-- ============================================================
-- Após criar o banco, conecte-se a ele via terminal SQL ou pgAdmin:
-- \c usercruddb;
-- ============================================================

-- ============================================================
-- 3 - CRIAÇÃO DA TABELA DE USUÁRIOS
-- ============================================================
CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL
);

-- ============================================================
-- 3.1 - TABELA DE CONTROLE DE INICIALIZAÇÃO
-- ============================================================
CREATE TABLE IF NOT EXISTS app_init (
    id SERIAL PRIMARY KEY,
    initialized BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO app_init (initialized)
VALUES (FALSE)
ON CONFLICT DO NOTHING;

-- ============================================================
-- 4 - OBSERVAÇÕES IMPORTANTES
-- ============================================================
-- - O back-end (Spring Boot) utiliza BCrypt para criptografar as senhas.
-- - Não insira manualmente usuários na tabela, pois a senha precisa ser
--   criptografada corretamente pelo backend.
-- - O backend irá criar um usuário inicial via rota de bootstrap.
-- - O Spring Boot fará o mapeamento desta tabela automaticamente
--   através do JPA (Hibernate).
-- - O token JWT tem validade de 24 horas.
-- - Caso você limpe as tabelas, ou passe de 24 horas, precisará rodar este
--   comando no console do seu navegador para limpar o token de conexão:
--   localStorage.removeItem('token')
-- - Após, reinicie o front-end e um novo token será gerado automaticamente.
-- ============================================================

-- ============================================================
-- 5 - COMO EXECUTAR
-- ============================================================
-- 1. Abra o terminal do PostgreSQL (psql) ou o pgAdmin.
-- 2. Execute este arquivo SQL (Use barras normais "/"):
--      \i 'CAMINHO/ATE/schema.sql';
-- 3. Confirme que o banco e a tabela foram criados:
--      \c usercruddb;
--      \dt;
-- 4. Faça uma cópia do application-template.properties,
--    renomeie para application.properties e configure:
--      spring.datasource.url=jdbc:postgresql://localhost:5432/usercruddb
--      spring.datasource.username=postgres
--      spring.datasource.password=SUA_SENHA_AQUI
-- 5. Rode o projeto no STS ou IntelliJ:
--      mvn spring-boot:run
-- 6. O back-end iniciará em:
--      http://localhost:8080
-- ============================================================
