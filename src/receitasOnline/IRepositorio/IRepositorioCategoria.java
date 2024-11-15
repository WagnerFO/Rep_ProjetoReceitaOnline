package receitasOnline.IRepositorio;

import java.sql.SQLException;
import java.util.ArrayList;

import receitasOnline.Entidades.Categoria;

public interface IRepositorioCategoria {

    void adicionar(Categoria categoria) throws SQLException;
    Categoria buscar(int id);
    void atualizar(Categoria categoria) throws SQLException;
    void remover(Categoria categoria);
    ArrayList<Categoria> listarTodos() throws SQLException; 
}
