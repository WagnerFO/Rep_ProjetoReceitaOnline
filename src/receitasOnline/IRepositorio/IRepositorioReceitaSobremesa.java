package receitasOnline.IRepositorio;

import java.sql.SQLException;
import java.util.ArrayList;

import receitasOnline.Entidades.Receita;
import receitasOnline.Entidades.ReceitaPrincipal;
import receitasOnline.Entidades.ReceitaSobremesa;

public interface IRepositorioReceitaSobremesa {

    void adicionar(ReceitaSobremesa receita) throws SQLException, InterruptedException;
    ReceitaSobremesa buscar(int id);
    ArrayList<ReceitaSobremesa> buscarReceitasPorIngrediente(String nomeIngrediente)throws SQLException, InterruptedException;
    void atualizar(ReceitaSobremesa receita) throws SQLException;
    void remover(ReceitaSobremesa receita);
    ArrayList<ReceitaSobremesa> listarTodos() throws SQLException; 
}