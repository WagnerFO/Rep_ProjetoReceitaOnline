package receitasOnline.Servicos;

import java.util.List;

import receitasOnline.Entidades.Avaliacao;
import receitasOnline.Repositorio.IAvaliacaoRepositorio;

//A classe AvaliacaoServico implementa a interface IAvaliacaoServico
public class AvaliacaoServico implements IAvaliacaoServico{
	private IAvaliacaoRepositorio avaliacaoRepositorio;
	
	// Construtor que recebe um repositório de avaliações como parâmetro
	public AvaliacaoServico(IAvaliacaoRepositorio avaliacaoRepositorio) {
		this.avaliacaoRepositorio = avaliacaoRepositorio;
	}
	
	// Método para adicionar uma nova avaliação ao repositório
	@Override
	public void adicionarAvaliacao(Avaliacao avaliacao) {
		// TODO Auto-generated method stub
		avaliacaoRepositorio.adicionar(avaliacao);
	}

	// Método para buscar uma avaliação pelo ID
	@Override
	public Avaliacao buscarAvaliacao(int id) {
		// TODO Auto-generated method stub
		return avaliacaoRepositorio.buscar(id);
	}

	// Método para atualizar uma avaliação existente
	@Override
	public void atualizarAvaliacao(Avaliacao avaliacao) {
		// TODO Auto-generated method stub
		avaliacaoRepositorio.atualizar(avaliacao);
	}

	// Método para remover uma avaliação pelo ID
	@Override
	public void removerAvaliacao(int id) {
		// TODO Auto-generated method stub
		avaliacaoRepositorio.remover(id);
	}

	// Método para listar todas as avaliações do repositório
	@Override
	public List<Avaliacao> listarAvaliacoes() {
		// TODO Auto-generated method stub
		return avaliacaoRepositorio.listarTodas();
	}

}
