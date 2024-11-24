package receitasOnline.IRepositorio;

import java.sql.SQLException;
import java.util.ArrayList;

import receitasOnline.Entidades.ReceitaSobremesa;

public interface IRepositorioReceitaSobremesa {

    void adicionar(ReceitaSobremesa receita) throws SQLException, InterruptedException;
    void buscar(ReceitaSobremesa receita);;
    ArrayList<ReceitaSobremesa> buscarReceitasPorIngrediente(String nomeIngrediente)throws SQLException, InterruptedException;
    void atualizar(ReceitaSobremesa receita) throws SQLException, InterruptedException;
    void remover(ReceitaSobremesa receita);
    ArrayList<ReceitaSobremesa> listarTodos() throws SQLException; 
}
