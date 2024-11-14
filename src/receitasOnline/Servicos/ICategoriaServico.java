package receitasOnline.Servicos;
import java.util.List;
import receitasOnline.Entidades.Categoria;

//Interface que define os métodos que um serviço de categoria deve implementar
public interface ICategoriaServico {
    // Método para adicionar uma categoria
	void adicionarCategoria(Categoria categoria);
    // Método para buscar uma categoria pelo ID
    Categoria buscarCategoria(int id);
    // Método para atualizar as informações de uma categoria
    void atualizarCategoria(Categoria categoria);
    // Método para remover uma categoria pelo ID
    void removerCategoria(int id);
    // Método para listar todas as categorias
    List<Categoria> listarCategorias();
}
