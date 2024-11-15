package receitasOnline.Repositorio;
import receitasOnline.Entidades.Avaliacao;
import java.util.List;


//Interface para o repositório de avaliações
public interface IAvaliacaoRepositorio {
	// Método para adicionar uma avaliação
	void adicionar(Avaliacao avaliacao);
	// Método para buscar uma avaliação pelo ID
	Avaliacao buscar(int id);
	// Método para atualizar as informações de uma avaliação
	void atualizar(Avaliacao avaliacao);
	// Método para remover uma avaliação pelo ID
	void remover(int id);
	// Método para listar todas as avaliações
	List<Avaliacao> listarTodas();
	List<Avaliacao> listarTodos();
}
