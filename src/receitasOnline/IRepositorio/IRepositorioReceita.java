package receitasOnline.IRepositorio;

import java.sql.SQLException;
import java.util.ArrayList;

import receitasOnline.Entidades.Receita;

public interface IRepositorioReceita {

    void adicionar(Receita receita) throws SQLException, InterruptedException;
    Receita buscar(int id);
    ArrayList<Receita> buscarReceitasPorIngrediente(String nomeIngrediente)throws SQLException, InterruptedException;
    void atualizar(Receita receita) throws SQLException;
    void remover(Receita receita);
    ArrayList<Receita> listarTodos() throws SQLException; 
}
