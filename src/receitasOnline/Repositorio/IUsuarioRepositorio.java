package receitasOnline.Repositorio;
import receitasOnline.Entidades.Usuario;
import java.util.List;

//Interface para o repositório de usuários
public interface IUsuarioRepositorio {
	// Método para adicionar um usuário
	void adicionar(Usuario usuario);
	// Método para buscar um usuário pelo ID
	Usuario buscar(int id);
	 // Método para atualizar as informações de um usuário
	void atualizar(Usuario usuario);
	// Método para remover um usuário pelo ID
	void remover(int id);
	// Método para listar todos os usuários
	List<Usuario> listarTodos();
}
