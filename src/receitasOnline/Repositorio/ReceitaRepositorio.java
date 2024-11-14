package receitasOnline.Repositorio;

import receitasOnline.Entidades.Receita;
import receitasOnline.Factory.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceitaRepositorio implements IReceitaRepositorio {
    private Connection connection;

    // Construtor que recebe a conexão
    public ReceitaRepositorio() throws SQLException {
        try {
			this.connection = ConnectionFactory.createConnectionToMySql();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Obtém a conexão pela fábrica
    }

    @Override
    public void adicionar(Receita receita) {
        String sql = "INSERT INTO receita (titulo, descricao, modo_preparo) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, receita.getTitulo());
            stmt.setString(2, receita.getDescricao());
            stmt.setString(3, receita.getModoPreparo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar receita", e);
        }
    }

    @Override
    public Receita buscar(int id) {
        String sql = "SELECT * FROM receita WHERE id_receita = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Criando o objeto Receita com os dados do banco
                    return new Receita(
                        rs.getInt("id_receita"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        rs.getString("modo_preparo")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar receita", e);
        }
        throw new IllegalArgumentException("Receita não encontrada.");
    }

    @Override
    public void atualizar(Receita receita) {
        String sql = "UPDATE receita SET titulo = ?, descricao = ?, modo_preparo = ? WHERE id_receita = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, receita.getTitulo());
            stmt.setString(2, receita.getDescricao());
            stmt.setString(3, receita.getModoPreparo());
            stmt.setInt(4, receita.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar receita", e);
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM receita WHERE id_receita = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover receita", e);
        }
    }

	@Override
	public List<Receita> listarTodas() {
		// TODO Auto-generated method stub
		 List<Receita> receitas = new ArrayList<>();
	        String sql = "SELECT * FROM receita";
	        try (PreparedStatement stmt = connection.prepareStatement(sql);
	             ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                // Adicionando as receitas encontradas no banco de dados à lista
	                receitas.add(new Receita(
	                    rs.getInt("id_receita"),
	                    rs.getString("titulo"),
	                    rs.getString("descricao"),
	                    rs.getString("modo_preparo")
	                ));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Erro ao listar receitas", e);
	        }
	        return receitas;
	}
}
