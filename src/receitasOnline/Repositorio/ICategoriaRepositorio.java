package receitasOnline.Repositorio;

import receitasOnline.Entidades.Categoria;
import java.sql.SQLException;
import java.util.List;

// Interface para o repositório de categorias
public interface ICategoriaRepositorio {
    // Método para adicionar uma categoria
    void adicionar(Categoria categoria) throws SQLException;

    // Método para buscar uma categoria pelo ID
    Categoria buscar(int id) throws SQLException;

    // Método para atualizar as informações de uma categoria
    void atualizar(Categoria categoria) throws SQLException;

    // Método para remover uma categoria pelo ID
    void remover(int id) throws SQLException;

    // Método para listar todas as categorias
    List<Categoria> listarTodos() throws SQLException;
}
