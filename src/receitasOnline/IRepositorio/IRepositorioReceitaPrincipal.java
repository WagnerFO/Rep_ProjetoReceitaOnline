package receitasOnline.IRepositorio;

import java.sql.SQLException;
import java.util.ArrayList;

import receitasOnline.Entidades.Receita;
import receitasOnline.Entidades.ReceitaPrincipal;

public interface IRepositorioReceitaPrincipal {

    void adicionar(ReceitaPrincipal receita) throws SQLException, InterruptedException;
    ReceitaPrincipal buscar(int id);
    ArrayList<ReceitaPrincipal> buscarReceitasPorIngrediente(String nomeIngrediente)throws SQLException, InterruptedException;
    void atualizar(ReceitaPrincipal receita) throws SQLException;
    void remover(ReceitaPrincipal receita);
    ArrayList<ReceitaPrincipal> listarTodos() throws SQLException; 
}
