package receitasOnline.Servicos;
import receitasOnline.Entidades.Usuario;
import java.util.List;

//Interface que define os métodos que um serviço de usuário deve implementar
public interface IUsuarioServico {
	// Método para adicionar um usuário
	 void adicionarUsuario(Usuario usuario);
	 // Método para buscar um usuário pelo ID
	 Usuario buscarUsuario(int id);
	// Método para atualizar as informações de um usuário
	 void atualizarUsuario(Usuario usuario);
	 // Método para remover um usuário pelo ID
	 void removerUsuario(int id);
	 // Método para listar todos os usuários
	 List<Usuario> listarUsuarios();
}
