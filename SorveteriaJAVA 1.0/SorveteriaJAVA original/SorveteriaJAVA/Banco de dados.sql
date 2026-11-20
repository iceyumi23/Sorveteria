CREATE DATABASE sorveteria
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE sorveteria;

CREATE TABLE Clientes(
    id INT PRIMARY KEY NOT NULL,
    nome VARCHAR(80) NOT NULL,
    CPF VARCHAR(30) NOT NULL,
    endereco VARCHAR(60) NOT NULL,
    telefone VARCHAR(15) NOT NULL
);

CREATE TABLE ItemCardapio(
    id VARCHAR(30) PRIMARY KEY,
    nome VARCHAR(90) NOT NULL,
    emoji VARCHAR(10),
    preco_centavos INT NOT NULL,
    tipo VARCHAR(10) NOT NULL
);

CREATE TABLE Pedido(
    id INT PRIMARY KEY AUTO_INCREMENT,
    numero INT,
    total_centavos INT NOT NULL,
    data_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_cliente INT NULL,
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id)
);

CREATE TABLE Pagamento(
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_pedido INT NOT NULL,
    forma VARCHAR(30),
    valor_centavos INT,
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id)
);

CREATE TABLE ItemPedido(
    id_pedido INT NOT NULL,
    id_item VARCHAR(30) NOT NULL,
    PRIMARY KEY (id_pedido, id_item),
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id),
    FOREIGN KEY (id_item) REFERENCES ItemCardapio(id)
);

CREATE TABLE Estoque(
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_item VARCHAR(30) NOT NULL,
    data_entrada DATE,
    quantidade_sabor DOUBLE,
    FOREIGN KEY (id_item) REFERENCES ItemCardapio(id)
);

INSERT INTO ItemCardapio(id, nome, emoji, preco_centavos, tipo) VALUES
('casquinha', 'Casquinha', '🍦' , 500, 'Base'),
('chocolate', 'Chocolate', '🍫', 200, 'Sabor'),
('morango',   'Morango',   '🍓', 200, 'Sabor'),
('baunilha',  'Baunilha',  '🍨', 200, 'Sabor');

INSERT INTO Estoque(id_item, data_entrada, quantidade_sabor) VALUES
('chocolate', '2026-07-15', 20),
('morango',   '2026-10-18', 15),
('baunilha',  '2026-10-23', 20);

SELECT * FROM ItemCardapio;
SELECT * FROM Estoque;