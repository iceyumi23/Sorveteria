# 🍦 Sorveteria

Aplicação web para montar sundaes de casquinha. O cliente escolhe os sabores na tela, o sistema calcula o total e registra o pedido no banco de dados.

Projeto de estudo com **Design Pattern Decorator**, **MVC**, **DAO/Repository** e princípios **SOLID**.

## Funcionalidades

- Lista o cardápio (base e sabores) vindo do banco de dados
- Calcula o preço do sundae conforme os sabores escolhidos
- Finaliza e grava o pedido no MySQL
- Interface simples, com fundo branco e cores rosa e azul

## Tecnologias

| Camada | Tecnologia |
|---|---|
| Front-end | HTML, CSS e JavaScript puro |
| Back-end | Java 24, Spring Boot 4, Maven |
| Banco de dados | MySQL (acesso via JDBC) |
| Testes | JUnit (Spring Boot Test) |

## Como o Decorator funciona

A casquinha é o componente base e cada sabor é um *decorator* que soma seu preço ao anterior.

```
Casquinha (R$ 5,00)
  └─ Chocolate (+ R$ 2,00)
       └─ Morango (+ R$ 2,00)   →  Total: R$ 9,00
```

Novos sabores podem ser adicionados sem alterar as classes existentes (princípio aberto/fechado).

## Estrutura do projeto

```
SorveteriaJAVA/
├── front-end/
│   ├── index.html
│   ├── css/style.css
│   └── javascript/        # app.js (tela) e api.js (chamadas à API)
├── back-end/
│   └── src/main/java/com/example/backend/
│       ├── controller/    # recebe as requisições (C do MVC)
│       ├── service/       # regras de negócio e montagem do sundae
│       ├── model/         # Casquinha, CasquinhaBase e decorators (Chocolate, Morango, Baunilha)
│       ├── repository/    # acesso ao banco (DAO/JDBC)
│       ├── dto/           # objetos de entrada e saída da API
│       ├── exception/     # erros de negócio
│       └── config/        # configurações
└── database/
    └── schema.sql         # criação das tabelas e dados iniciais
```

## Como rodar

### Pré-requisitos

- Java 24
- MySQL 8+

### 1. Banco de dados

Execute o script `database/schema.sql` no MySQL. Ele cria o banco `sorveteria`, as tabelas e o cardápio inicial.

### 2. Configuração

Em `back-end/src/main/resources/application.properties`, ajuste a conexão:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sorveteria
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

### 3. Executar

```bash
cd back-end
./mvnw spring-boot:run
```

No Windows: `mvnw.cmd spring-boot:run`

### 4. Acessar

Abra no navegador: **http://localhost:8080/sorveteria**

## API

Base URL local: `http://localhost:8080` (página inicial em `http://localhost:8080/sorveteria`).

| Método | Rota | Descrição | Efeito colateral |
| :--- | :--- | :--- | :--- |
| `GET` | `/sorveteria/sabores` | Retorna a base e a lista de sabores | Nenhum (somente leitura) |
| `POST` | `/sorveteria/sundae/calcular` | Calcula o total sem salvar | Não grava pedido |
| `POST` | `/sorveteria/pedidos` | Finaliza e grava o pedido | Persiste pedido e gera número |

### Como usar no Front-end
1. Busque a base e os sabores em `GET /sorveteria/sabores` ao abrir a tela.
2. Envie a montagem atual para `POST /sorveteria/sundae/calcular` para exibir o valor atualizado antes de confirmar.
3. Envie para `POST /sorveteria/pedidos` apenas ao finalizar o pedido.

### O que NÃO esperar da API
- `POST /sorveteria/sundae/calcular` **não** persiste pedidos.
- Sabores inválidos são rejeitados (`SaborInvalidoException` via `ApiExceptionHandler`) — trate respostas de erro no Front.
- Não recalcule regras de negócio no Front como fonte final; use o retorno do back-end.
- Verifique a configuração de CORS (`WebConfig`) e o banco/JDBC antes de testar fluxos de gravação.

Exemplo de corpo para as rotas POST:

```json
{ "sabores": ["chocolate", "morango"] }
```

## Testes

```bash
cd back-end
./mvnw test
```

Cobrem a casquinha base, cada sabor e cenários de combinação.

Imagens do Diagrama de Classe:
<img width="1312" height="711" alt="Diagrama de Classe" src="https://github.com/user-attachments/assets/010ecc3a-4ef0-4671-bae9-e6ee36506265" />

Imagens do Diagrama de Sequência:
<img width="910" height="722" alt="Diagrama de Sequencia" src="https://github.com/user-attachments/assets/92710687-417b-4b4a-bb9b-593473cb09f8" />



