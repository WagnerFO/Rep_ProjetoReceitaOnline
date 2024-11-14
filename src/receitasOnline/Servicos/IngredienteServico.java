package receitasOnline.Servicos;

import java.util.List;

import receitasOnline.Entidades.Ingrediente;
import receitasOnline.Repositorio.IIngredienteRepositorio;

//A classe IngredienteServico implementa a interface IIngredienteServico
public class IngredienteServico implements IIngredienteServico{
	private IIngredienteRepositorio ingredienteRepositorio;
	
	// Construtor que recebe um repositório de ingredientes como parâmetro
	public IngredienteServico(IIngredienteRepositorio ingredienteRepositorio) {
		this.ingredienteRepositorio = ingredienteRepositorio;
	}

	// Método para adicionar um novo ingrediente ao repositório
	@Override
	public void adicionarIngrediente(Ingrediente ingrediente) {
		// TODO Auto-generated method stub
		ingredienteRepositorio.adicionar(ingrediente);
	}

	// Sobrecarga do método adicionarIngrediente
    public void adicionarIngrediente(int id, String nome) {
        Ingrediente ingrediente = new Ingrediente(id, nome);
        ingredienteRepositorio.adicionar(ingrediente);
    }	
	
 // Método para buscar um ingrediente pelo ID
	@Override
	public Ingrediente buscarIngrediente(int id) {
		// TODO Auto-generated method stub
		return ingredienteRepositorio.buscar(id);
	}

	// Método para atualizar as informações de um ingrediente existente
	@Override
	public void atualizarIngrediente(Ingrediente ingrediente) {
		// TODO Auto-generated method stub
		ingredienteRepositorio.atualizar(ingrediente);
	}

	// Método para remover um ingrediente pelo ID
	@Override
	public void removerIngrediente(int id) {
		// TODO Auto-generated method stub
		ingredienteRepositorio.remover(id);
	}

	// Método para listar todos os ingredientes do repositório
	@Override
	public List<Ingrediente> listarIngredientes() {
		// TODO Auto-generated method stub
		return ingredienteRepositorio.listarTodos();
	}
}
