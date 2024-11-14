package receitasOnline.Servicos;
import java.util.List;

import receitasOnline.Entidades.Receita;
import receitasOnline.Repositorio.IReceitaRepositorio;

public class ReceitaServico implements IReceitaServico{
	private IReceitaRepositorio receitaRepositorio;
	
	public ReceitaServico(IReceitaRepositorio receitaRepositorio) {
		this.receitaRepositorio = receitaRepositorio;
	}
	@Override
	public void adicionarReceita(Receita receita) {
		// TODO Auto-generated method stub
		receitaRepositorio.adicionar(receita);
	}
	
	//sobrecarga de método adicionarReceita
	public void adicionarReceita(int id, String nome) {
		Receita receita = new Receita(id, nome);
		receitaRepositorio.adicionar(receita);
	}

	public void adicionarReceita(int id, String nome, String descricao) {
		Receita receita = new Receita(id, nome, descricao);
		receitaRepositorio.adicionar(receita);
	}
	
	@Override
	public Receita buscarReceita(int id) {
		// TODO Auto-generated method stub
		return receitaRepositorio.buscar(id);
	}

	@Override
	public void atualizarReceita(Receita receita) {
		// TODO Auto-generated method stub
		receitaRepositorio.atualizar(receita);
		
	}

	@Override
	public void removerReceita(int id) {
		// TODO Auto-generated method stub
		receitaRepositorio.remover(id);
	}

	@Override
	public List<Receita> listarReceitas() {
		// TODO Auto-generated method stub
		return receitaRepositorio.listarTodas();
	}
}
