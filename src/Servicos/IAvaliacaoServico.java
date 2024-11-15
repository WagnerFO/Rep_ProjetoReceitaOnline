package receitasOnline.Servicos;
import java.util.List;
import receitasOnline.Entidades.Avaliacao;

//Interface que define os métodos que um serviço de avaliação deve implementar
public interface IAvaliacaoServico {
	// Método para adicionar uma avaliação
    void adicionarAvaliacao(Avaliacao avaliacao);
    // Método para buscar uma avaliação pelo ID
    Avaliacao buscarAvaliacao(int id);
    // Método para atualizar as informações de uma avaliação
    void atualizarAvaliacao(Avaliacao avaliacao);
    // Método para remover uma avaliação pelo ID
    void removerAvaliacao(int id);
    // Método para listar todas as avaliações
    List<Avaliacao> listarAvaliacoes();

}
