package receitasOnline.Repositorio;

import receitasOnline.Entidades.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio implements IUsuarioRepositorio {
    private Connection connection;

    public UsuarioRepositorio(Connection connection) {
        this.connection = connection; // Recebe a conexão pela fábrica
    }

    @Override
    public void adicionar(Usuario usuario) {
        String sql = "INSERT INTO Usuario (nome, email, senha, data_criacao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setDate(4, new java.sql.Date(usuario.getDataCriacao().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar usuário", e);
        }
    }

    @Override
    public Usuario buscar(int id) {
        String sql = "SELECT * FROM Usuario WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getDate("data_criacao")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
        throw new IllegalArgumentException("Usuário não encontrado.");
    }

    @Override
    public void atualizar(Usuario usuario) {
        String sql = "UPDATE Usuario SET nome = ?, email = ?, senha = ?, data_criacao = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setDate(4, new java.sql.Date(usuario.getDataCriacao().getTime()));
            stmt.setInt(5, usuario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM Usuario WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover usuário", e);
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha"),
                    rs.getDate("data_criacao")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar usuários", e);
        }
        return usuarios;
    }
}
