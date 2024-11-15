package receitasOnline.Repositorio;

import receitasOnline.Entidades.Categoria;
import receitasOnline.Factory.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositorio implements ICategoriaRepositorio {
    private Connection connection; // Conexão com o banco de dados

    public CategoriaRepositorio() {
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
    public void adicionar(Categoria categoria) {
        String sql = "INSERT INTO categoria (nome_categoria) VALUES (?)";
        try {
            abrirConexao();
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, categoria.getNome());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar categoria", e);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public Categoria buscar(int id) {
        String sql = "SELECT * FROM categoria WHERE id_categoria = ?";
        try {
            abrirConexao();
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Categoria(
                            rs.getInt("id_categoria"),
                            rs.getString("nome_categoria")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar categoria", e);
        } finally {
            fecharConexao();
        }
        throw new IllegalArgumentException("Categoria não encontrada.");
    }

    @Override
    public void atualizar(Categoria categoria) {
        String sql = "UPDATE categoria SET nome_categoria = ? WHERE id_categoria = ?";
        try {
            abrirConexao();
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, categoria.getNome());
                stmt.setInt(2, categoria.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar categoria", e);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM categoria WHERE id_categoria = ?";
        try {
            abrirConexao();
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover categoria", e);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Categoria> listarTodos() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria";
        try {
            abrirConexao();
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categorias.add(new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nome_categoria")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar categorias", e);
        } finally {
            fecharConexao();
        }
        return categorias;
    }
}
