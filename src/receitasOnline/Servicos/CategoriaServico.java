package receitasOnline.Servicos;

import java.util.List;

import receitasOnline.Entidades.Categoria;
import receitasOnline.Repositorio.ICategoriaRepositorio;

//A classe CategoriaServico implementa a interface ICategoriaServico
public class CategoriaServico implements ICategoriaServico{
	private ICategoriaRepositorio categoriaRepositorio;
	
	// Construtor que recebe um repositório de categorias como parâmetro
	public CategoriaServico(ICategoriaRepositorio categoriaRepositorio) {
		this.categoriaRepositorio = categoriaRepositorio;
	}

	// Método para adicionar uma nova categoria ao repositório
	@Override
	public void adicionarCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		categoriaRepositorio.adicionar(categoria);
	}

	// Sobrecarga do método adicionarCategoria para criar uma categoria com id e nome
    public void adicionarCategoria(int id, String nome) {
        Categoria categoria = new Categoria(id, nome);
        categoriaRepositorio.adicionar(categoria);
    }
	
 // Método para buscar uma categoria pelo ID
	@Override
	public Categoria buscarCategoria(int id) {
		// TODO Auto-generated method stub
		return categoriaRepositorio.buscar(id);
	}

	// Método para atualizar as informações de uma categoria existente
	@Override
	public void atualizarCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		categoriaRepositorio.atualizar(categoria);
	}
	
	// Método para remover uma categoria pelo ID
	@Override
	public void removerCategoria(int id) {
		// TODO Auto-generated method stub
		categoriaRepositorio.remover(id);
	}

	// Método para listar todas as categorias do repositório
	@Override
	public List<Categoria> listarCategorias() {
		// TODO Auto-generated method stub
		return categoriaRepositorio.listarTodas();
	}
}
