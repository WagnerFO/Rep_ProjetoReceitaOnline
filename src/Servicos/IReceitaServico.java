package receitasOnline.Servicos;
import receitasOnline.Entidades.Receita;
import java.util.List;

//Interface que define os métodos que um serviço de receita deve implementar
public interface IReceitaServico {
	 // Método para adicionar uma receita
	void adicionarReceita(Receita receita);
    // Método para buscar uma receita pelo ID
    Receita buscarReceita(int id);
    // Método para atualizar as informações de uma receita
    void atualizarReceita(Receita receita);
    // Método para remover uma receita pelo ID
    void removerReceita(int id);
    // Método para listar todas as receitas
    List<Receita> listarReceitas();
}
