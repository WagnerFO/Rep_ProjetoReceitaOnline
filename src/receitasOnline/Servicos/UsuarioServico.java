package receitasOnline.Servicos;

import java.util.List;
import receitasOnline.Entidades.Usuario;
import receitasOnline.Repositorio.IUsuarioRepositorio;

// A classe UsuarioServico implementa a interface IUsuarioServico
public class UsuarioServico implements IUsuarioServico {
    private IUsuarioRepositorio usuarioRepositorio;

    // Construtor que recebe um repositório de usuários como parâmetro
    public UsuarioServico(IUsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    // Método para adicionar um usuário ao repositório
    @Override
    public void adicionarUsuario(Usuario usuario) {
        usuarioRepositorio.adicionar(usuario);
    }

    // Método para buscar um usuário pelo ID
    @Override
    public Usuario buscarUsuario(int id) {
        return usuarioRepositorio.buscar(id);
    }

    // Método para atualizar as informações de um usuário
    @Override
    public void atualizarUsuario(Usuario usuario) {
        usuarioRepositorio.atualizar(usuario);
    }

    // Método para remover um usuário pelo ID
    @Override
    public void removerUsuario(int id) {
        usuarioRepositorio.remover(id);
    }

    // Método para listar todos os usuários do repositório
    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.listarTodos();
    }
}
