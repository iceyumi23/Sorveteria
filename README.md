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

| Método | Rota | Descrição |
|---|---|---|
| GET | `/sorveteria/sabores` | Retorna a base e a lista de sabores |
| POST | `/sorveteria/sundae/calcular` | Calcula o total sem salvar |
| POST | `/sorveteria/pedidos` | Finaliza e grava o pedido |

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

## Próximos passos

- [ ] Controle de estoque dos sabores
- [ ] Cadastro de clientes e formas de pagamento
- [ ] Novos sabores e tamanhos

## Autor

Seu Nome — [github.com/seu-usuario](https://github.com/seu-usuario)
