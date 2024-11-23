package receitasOnline.IRepositorio;

import java.sql.SQLException;
import java.util.ArrayList;

import receitasOnline.Entidades.ReceitaPrincipal;

public interface IRepositorioReceitaPrincipal {

    void adicionar(ReceitaPrincipal receita) throws SQLException, InterruptedException;
    void buscar(ReceitaPrincipal receita) throws SQLException ;
    ArrayList<ReceitaPrincipal> buscarReceitasPorIngrediente(String nomeIngrediente)throws SQLException, InterruptedException;
    void atualizar(ReceitaPrincipal receita) throws SQLException, InterruptedException;
    void remover(ReceitaPrincipal receita) throws SQLException;
    ArrayList<ReceitaPrincipal> listarTodos() throws SQLException; 
}
