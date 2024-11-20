
CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    rua VARCHAR(255),
    numero INT,
    cidade VARCHAR(255),
    estado VARCHAR(255)
);

CREATE TABLE avaliacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nota INT NOT NULL,
    comentario TEXT,
    usuarioId INT,
    FOREIGN KEY (usuarioId) REFERENCES usuario(id)
);

CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE ingrediente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    quantidade DOUBLE
);

CREATE TABLE receita (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    modo_preparo TEXT NOT NULL,
    categoriaId INT NOT NULL,
    FOREIGN KEY (categoriaId) REFERENCES categoria(id)
);

CREATE TABLE receita_principal (
    id INT PRIMARY KEY,
    dificuldade VARCHAR(50) NOT NULL,
    tempo_preparo INT NOT NULL,
    FOREIGN KEY (id) REFERENCES receita(id) ON DELETE CASCADE
);

CREATE TABLE receita_sobremesa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    modo_preparo TEXT,
    categoriaId INT,
    FOREIGN KEY (categoriaId) REFERENCES categoria(id)
);

CREATE TABLE receita_ingrediente (
    id_receita INT,
    id_ingrediente INT,
    PRIMARY KEY (id_receita, id_ingrediente),
    FOREIGN KEY (id_receita) REFERENCES receita(id),
    FOREIGN KEY (id_ingrediente) REFERENCES ingrediente(id)
);
