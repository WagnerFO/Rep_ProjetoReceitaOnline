
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

-- Tabela receita (tabela base para todos os tipos de receita)
CREATE TABLE receita (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    modo_preparo TEXT NOT NULL,
    categoriaId INT NOT NULL,
    tipo VARCHAR(50),  -- Tipo para distinguir entre principal ou sobremesa (opcional)
    FOREIGN KEY (categoriaId) REFERENCES categoria(id)
);

-- Tabela receita_principal (informações específicas para receitas principais)
CREATE TABLE receita_principal (
    id INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    dificuldade VARCHAR(50) NOT NULL,
    tempo_preparo INT NOT NULL,
    FOREIGN KEY (id) REFERENCES receita(id) ON DELETE CASCADE
);

-- Tabela receita_sobremesa (informações específicas para receitas sobremesas)
CREATE TABLE receita_sobremesa (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    contem_acucar BOOLEAN NOT NULL,
    tipo_acucar VARCHAR(255),
    FOREIGN KEY (id) REFERENCES receita(id) ON DELETE CASCADE
);



CREATE TABLE receita_ingrediente (
    id_receita INT,
    id_ingrediente INT,
    PRIMARY KEY (id_receita, id_ingrediente),
    FOREIGN KEY (id_receita) REFERENCES receita(id),
    FOREIGN KEY (id_ingrediente) REFERENCES ingrediente(id)
);
