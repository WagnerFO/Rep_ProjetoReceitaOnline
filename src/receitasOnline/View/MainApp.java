package receitasOnline.View;

import java.sql.SQLException;

import receitasOnline.Entidades.Avaliacao;
import receitasOnline.Entidades.Usuario;
import receitasOnline.IRepositorio.IRepositorioAvaliacao;
import receitasOnline.IRepositorio.IRepositorioUsuario;
import receitasOnline.Repositorio.RepositorioAvaliacaoSQL;
import receitasOnline.Repositorio.RepositorioUsuarioSQL;

public class MainApp {

    private static IRepositorioAvaliacao avaliacaoRepSql = new RepositorioAvaliacaoSQL();
    private static IRepositorioUsuario usuarioRepSql = new RepositorioUsuarioSQL();
    public static void main(String[] args) throws SQLException { 
    	
    	Avaliacao av1 = new Avaliacao(9,"Muito Bom", 1);
        
        avaliacaoRepSql.adicionar(av1);
    }
}

