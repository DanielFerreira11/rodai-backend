# Projeto RodAI - Backend

## 🐘 Banco de Dados com Docker (PostgreSQL 17)

### 🔧 Subindo o PostgreSQL com Docker

O projeto utiliza Docker para subir um banco PostgreSQL local com os seguintes dados de acesso:

- **Banco:** acidentes  
- **Usuário:** postgres  
- **Senha:** 1234  
- **Porta:** 5432

O arquivo `docker-compose.yml` já está configurado na raiz do projeto:

```
services:
  postgres:
    image: postgres:17
    container_name: acidentes-db
    restart: always
    ports:
      - "5432:5432"
    environment:
      POSTGRES_DB: acidentes
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: 1234
    volumes:
      - acidentes_pgdata:/var/lib/postgresql/data
    networks:
      - acidentes-net

volumes:
  acidentes_pgdata:

networks:
  acidentes-net:
```

Para subir o banco:

```bash
docker-compose up -d
```

---

### 🔄 Restaurar o backup via pgAdmin

1. Abra o pgAdmin
2. Crie uma nova conexão com:
   - Host: `localhost`
   - Porta: `5432`
   - Usuário: `postgres`
   - Senha: `1234`
3. Vá até o banco `acidentes` > botão direito > **Restore...**
4. Escolha o arquivo `.backup` no caminho:
   `C:\Users\danie\OneDrive\Documentos\Backup Acidentes`
5. Formato: `Custom or tar`
6. Clique em **Restore**

---

### ✅ Verificando dados manualmente

No terminal:

```bash
docker exec -it acidentes-db psql -U postgres -d acidentes
```

Dentro do terminal do PostgreSQL:

```sql
\dt
SELECT COUNT(*) FROM acidente;
```

---

### ⚙️ Configuração da aplicação (`application.properties`)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/acidentes
spring.datasource.username=postgres
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=none
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---
