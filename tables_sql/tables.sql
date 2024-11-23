
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
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nota INT NOT NULL,
    comentario TEXT NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY (usuarioId) REFERENCES usuario(id)
);

CREATE TABLE categoria (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE ingrediente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    quantidade DOUBLE
);


-- Tabela receita_principal (informações específicas para receitas principais)
CREATE TABLE receita_principal (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    modo_preparo TEXT NOT NULL,
    categoriaId INT NOT NULL,
    tempo_preparo INT NOT NULL,
    FOREIGN KEY (categoriaId) REFERENCES categoria(id)
);

-- Tabela receita_sobremesa (informações específicas para receitas sobremesas)
CREATE TABLE receita_sobremesa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    modo_preparo TEXT NOT NULL,
    categoriaId INT NOT NULL,
    contem_acucar BOOLEAN NOT NULL,
    tipo_acucar VARCHAR(255),
    FOREIGN KEY (categoriaId) REFERENCES categoria(id)
);



CREATE TABLE receitaP_ingrediente(
    id_receitaPrincipal INT,
    id_ingrediente INT,
    PRIMARY KEY (id_receitaPrincipal, id_ingrediente),
    FOREIGN KEY (id_receitaPrincipal) REFERENCES receita_principal(id),
    FOREIGN KEY (id_ingrediente) REFERENCES ingrediente(id)
);

CREATE TABLE receitaS_ingrediente (
    id_receitaSobremesa INT,
    id_ingrediente INT,
    PRIMARY KEY (id_receitaSobremesa, id_ingrediente),
    FOREIGN KEY (id_receitaSobremesa) REFERENCES receita_sobremesa(id),
    FOREIGN KEY (id_ingrediente) REFERENCES ingrediente(id)
);

CREATE TABLE receitaP_categoria (
    id_receitaPrincipal INT,
    id_categoria INT,
    PRIMARY KEY (id_receitaPrincipal, id_categoria),  -- Garantir a unicidade da combinação
    FOREIGN KEY (id_receitaPrincipal) REFERENCES receita_principal(id) ON DELETE CASCADE,  -- Excluir as associações quando a receita for excluída
    FOREIGN KEY (id_categoria) REFERENCES categoria(id) ON DELETE CASCADE  -- Excluir as associações quando a categoria for excluída
);


