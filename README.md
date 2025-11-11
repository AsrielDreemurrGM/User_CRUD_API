<h1>CRUD de Usuários - Back-End</h1>
<p>
  Este projeto é a API back-end de um sistema de gerenciamento de usuários,
  construída em <strong>Spring Boot</strong> com <strong>Java 21</strong>. Ele
  gerencia autenticação via <strong>JWT</strong>, criptografia de senhas com
  <strong>BCrypt</strong> e operações CRUD de usuários no banco
  <strong>PostgreSQL</strong>. Implementa tratamento de erros estruturado com
  exceções customizadas e validação de dados de entrada.
</p>
<h2>Repositório do Sistema Frontend</h2>
🔗
<a href="https://github.com/AsrielDreemurrGM/User_CRUD"> User_CRUD</a>
<h2>🚀 Funcionalidades</h2>
<ul>
  <li>CRUD completo de usuários com validação de dados</li>
  <li>Registro e edição de usuários com senha criptografada</li>
  <li>Autenticação via JWT para todas as rotas protegidas</li>
  <li>
    Bootstrap inicial do sistema criando usuário admin e gerando token JWT
  </li>
  <li>Tratamento de erros com respostas estruturadas para frontend</li>
  <li>Filtro de requisições para validação de tokens JWT</li>
  <li>Configuração de CORS para integração com front-end</li>
  <li>Persistência de dados no PostgreSQL</li>
</ul>
<h2>🛠️ Stack Tecnológica</h2>
<ul>
  <li><strong>Java:</strong> 21</li>
  <li><strong>Spring Boot:</strong> 3.5.7</li>
  <li>
    <strong>Spring Security:</strong> Autenticação e autorização usando JSON Web
    Token (JWT)
  </li>
  <li><strong>PostgreSQL:</strong> Banco de dados relacional</li>
  <li><strong>Lombok:</strong> Redução de boilerplate code</li>
  <li><strong>Maven:</strong> Gerenciamento de dependências</li>
  <li><strong>BCrypt:</strong> Criptografia de senhas</li>
</ul>
<h2>📂 Estrutura do Projeto</h2>
<ul>
  <li>
    <strong>controller:</strong> Endpoints REST para CRUD de usuários, login e
    bootstrap
  </li>
  <li><strong>service:</strong> Lógica de negócio e validação de dados</li>
  <li>
    <strong>repository:</strong> Interfaces JPA para acesso ao banco de dados
  </li>
  <li>
    <strong>model:</strong> Entidades JPA representando usuários e inicialização
    do app
  </li>
  <li>
    <strong>security:</strong> JWTUtil, filtros de autenticação e configuração
    do Spring Security
  </li>
  <li>
    <strong>exception:</strong> Exceções customizadas e tratamento global de
    erros
  </li>
</ul>
<h2>🧪 Testes e Validações</h2>
<ul>
  <li>Validação de dados de entrada no serviço de usuários</li>
  <li>Tratamento de erros estruturado com mensagens claras para frontend</li>
  <li>Bootstrap inicial para criar admin e gerar token JWT</li>
  <li>Autenticação testada com JWT e filtros de segurança Spring Security</li>
</ul>
<h2>⚙️ Primeiros Passos</h2>
<ol>
  <li>
    Clone o repositório:
    <pre><code>git clone https://github.com/AsrielDreemurrGM/User_CRUD_API.git</code></pre>
  </li>
  <li>Siga o passo a passo instruido no schema.sql:</li>
  <h2>📂 Banco de Dados (Schema)</h2>
  <p>
    O projeto utiliza <strong>PostgreSQL</strong>. Abaixo está o script de
    criação do banco e tabelas principais. Execute-o no terminal SQL (psql) ou
    pgAdmin antes de rodar o backend.
  </p>

```sql
-- Criação do banco
CREATE DATABASE usercruddb;

-- Conexão
\c usercruddb;

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL
);

-- Tabela de controle de inicialização
CREATE TABLE IF NOT EXISTS app_init (
    id SERIAL PRIMARY KEY,
    initialized BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO app_init (initialized)
VALUES (FALSE)
ON CONFLICT DO NOTHING;
```
<p>
  <b>Observações:</b>
  <ul>
    <li>Senhas devem ser criadas via backend (BCrypt).</li>
    <li>Não insira usuários manualmente; use a rota de bootstrap.</li>
    <li>JWT possui validade de 24 horas.</li>
    <li>Se limpar tabelas ou expirar o token, execute no navegador: <code>localStorage.removeItem('token')</code></li>
    <li>Em caso de mais dúvidas sobre a criação do banco consulte o arquivo schema.sql</li>
  </ul>
</p>
<ul>
  <li>
    Após ter o sistema backend rodando, clone e execute o projeto frontend e abra no
    navegador:
    <pre><code>http://localhost:5173</code></pre>
  </li>
</ul>
<h2>📜 Histórico de Commits Relevantes</h2>
<ul>
  <li>Implementação de UserController e DTOs para CRUD</li>
  <li>Adição de AuthController com login JWT</li>
  <li>Configuração de WebSecurityConfig e JWTRequestFilter</li>
  <li>Criação de BootstrapController e serviço de inicialização</li>
  <li>
    Refatoração do JWTUtil para usar senha do banco como chave de assinatura
  </li>
  <li>
    Tratamento de exceções customizadas: UserNotFoundException,
    InvalidUserDataException, AdminNotFoundException
  </li>
  <li>
    Integração completa com PostgreSQL e criptografia de senhas com BCrypt
  </li>
</ul>
