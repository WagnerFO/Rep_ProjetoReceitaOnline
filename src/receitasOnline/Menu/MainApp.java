package receitasOnline.Menu;

import java.sql.SQLException;

import receitasOnline.Entidades.Avaliacao;
import receitasOnline.IRepositorio.IRepositorioAvaliacao;
import receitasOnline.Repositorio.RepositorioAvaliacaoSQL;

public class MainApp {

    private static IRepositorioAvaliacao avaliacaoRepSql = new RepositorioAvaliacaoSQL();
    public static void main(String[] args) throws SQLException {  
        Avaliacao av1 = new Avaliacao(9,"Muito Bom");

        avaliacaoRepSql.adicinar(av1);
    }
}

