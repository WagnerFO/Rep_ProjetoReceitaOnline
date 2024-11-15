package receitasOnline.IRepositorio;

import java.sql.SQLException;
import java.util.ArrayList;
import receitasOnline.Entidades.Usuario;

public interface IRepositorioUsuario {
    void adicionar(Usuario usuario) throws SQLException;
    Usuario buscar(int id);
    void atualizar(Usuario usuario) throws SQLException;
    void remover(Usuario usuario);
    ArrayList<Usuario> listarTodos() throws SQLException;
}
