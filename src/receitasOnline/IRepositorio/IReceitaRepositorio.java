package receitasOnline.Repositorio;
import receitasOnline.Entidades.Receita;
import java.util.List;

//Interface para o repositório de receitas
public interface IReceitaRepositorio {
	// Método para adicionar uma receita
	void adicionar(Receita receita);
	// Método para buscar uma receita pelo ID
	Receita buscar(int id);
	 // Método para atualizar as informações de uma receita
	void atualizar(Receita receita);
	// Método para remover uma receita pelo ID
	void remover(int id);
	 // Método para listar todas as receitas
	List<Receita> listarTodas();

}
