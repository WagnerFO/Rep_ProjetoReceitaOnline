package receitasOnline.Repositorio;

import receitasOnline.Entidades.Ingrediente;

import java.sql.SQLException;
import java.util.List;

// Interface para o repositório de ingredientes
public interface IIngredienteRepositorio {
    // Método para adicionar um ingrediente
    void adicionar(Ingrediente ingrediente) throws SQLException, IllegalArgumentException;

    // Método para buscar um ingrediente pelo ID
    Ingrediente buscar(int id) throws SQLException;

    // Método para atualizar as informações de um ingrediente
    void atualizar(Ingrediente ingrediente) throws SQLException, IllegalArgumentException;

    // Método para remover um ingrediente pelo ID
    void remover(int id) throws SQLException;

    // Método para listar todos os ingredientes
    List<Ingrediente> listarTodos() throws SQLException;
}
