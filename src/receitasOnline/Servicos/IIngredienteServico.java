package receitasOnline.Servicos;
import java.util.List;
import receitasOnline.Entidades.Ingrediente;

//Interface que define os métodos que um serviço de ingrediente deve implementar
public interface IIngredienteServico {
    // Método para adicionar um ingrediente
    void adicionarIngrediente(Ingrediente ingrediente);
    // Método para buscar um ingrediente pelo ID
    Ingrediente buscarIngrediente(int id);
    // Método para atualizar as informações de um ingrediente
    void atualizarIngrediente(Ingrediente ingrediente);
    // Método para remover um ingrediente pelo ID
    void removerIngrediente(int id);
    // Método para listar todos os ingredientes
    List<Ingrediente> listarIngredientes();
}
