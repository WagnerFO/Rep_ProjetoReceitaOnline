package receitasOnline.Repositorio;

import receitasOnline.Entidades.Avaliacao;
import receitasOnline.Factory.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoRepositorio implements IAvaliacaoRepositorio {
    private Connection connection; // Conexão com o banco de dados

    public AvaliacaoRepositorio() {
        // A conexão será inicializada quando necessário
    }

    private void abrirConexao() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            try {
				this.connection = ConnectionFactory.createConnectionToMySql();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Obtém a conexão pela fábrica
        }
    }

    @Override
    public void adicionar(Avaliacao avaliacao) {
        String sql = "INSERT INTO avaliacao (nota, comentario) VALUES (?, ?)";
        try {
            abrirConexao();
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, avaliacao.getNota());
                stmt.setString(2, avaliacao.getComentario());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar avaliação", e);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public Avaliacao buscar(int id) {
        String sql = "SELECT * FROM avaliacao WHERE id_avaliacao = ?";
        try {
            abrirConexao();
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Avaliacao(
                            rs.getInt("id_avaliacao"),
                            rs.getInt("nota"),
                            rs.getString("comentario")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar avaliação", e);
        } finally {
            fecharConexao();
        }
        throw new IllegalArgumentException("Avaliação não encontrada.");
    }

    @Override
    public void atualizar(Avaliacao avaliacao) {
        String sql = "UPDATE avaliacao SET nota = ?, comentario = ? WHERE id_avaliacao = ?";
        try {
            abrirConexao();
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, avaliacao.getNota());
                stmt.setString(2, avaliacao.getComentario());
                stmt.setInt(3, avaliacao.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar avaliação", e);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM avaliacao WHERE id_avaliacao = ?";
        try {
            abrirConexao();
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover avaliação", e);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Avaliacao> listarTodos() {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        String sql = "SELECT * FROM avaliacao";
        try {
            abrirConexao();
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    avaliacoes.add(new Avaliacao(
                        rs.getInt("id_avaliacao"),
                        rs.getInt("nota"),
                        rs.getString("comentario")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar avaliações", e);
        } finally {
            fecharConexao();
        }
        return avaliacoes;
    }

    private void fecharConexao() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

	@Override
	public List<Avaliacao> listarTodas() {
		// TODO Auto-generated method stub
		return null;
	}
}
