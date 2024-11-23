package receitasOnline.IRepositorio;

import java.sql.SQLException;
import java.util.ArrayList;

import receitasOnline.Entidades.Ingrediente;

public interface IRepositorioIngrediente {

	void adicionar(Ingrediente ingrediente) throws SQLException;
    Ingrediente buscar(int id) throws SQLException;
    void atualizar(Ingrediente ingrediente) throws SQLException;
    void remover(Ingrediente ingrediente) throws SQLException;
    ArrayList<Ingrediente> listarTodos() throws SQLException;
}
