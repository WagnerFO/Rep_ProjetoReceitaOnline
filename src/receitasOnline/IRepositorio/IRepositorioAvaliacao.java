package receitasOnline.IRepositorio;

import java.sql.SQLException;
import java.util.ArrayList;
import receitasOnline.Entidades.Avaliacao;

public interface IRepositorioAvaliacao {
    void adicionar(Avaliacao avaliacao) throws SQLException;
    Avaliacao buscar(int id);
    void atualizar(Avaliacao avaliacao) throws SQLException;
    void remover(Avaliacao avaliacao);
    ArrayList<Avaliacao> listarTodos() throws SQLException;
}
