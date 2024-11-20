package receitasOnline.Repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import receitasOnline.Entidades.Usuario;
import receitasOnline.Factory.ConnectionSingleton;
import receitasOnline.IRepositorio.IRepositorioUsuario;

public class RepositorioUsuarioSQL implements IRepositorioUsuario {

    private Connection connection;

    public RepositorioUsuarioSQL() {
        try {
            this.connection = ConnectionSingleton.getInstance().conexao;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void adicionar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, email, senha, rua, numero, cidade, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getRua());
            stmt.setInt(5, usuario.getNumero());
            stmt.setString(6, usuario.getCidade());
            stmt.setString(7, usuario.getEstado());
            stmt.executeUpdate();
        }
    }

    @Override
    public Usuario buscar(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        Usuario usuario = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setRua(rs.getString("rua"));
                usuario.setNumero(rs.getInt("numero"));
                usuario.setCidade(rs.getString("cidade"));
                usuario.setEstado(rs.getString("estado"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao Buscar Usuário: " + e.getMessage());
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public void atualizar(Usuario usuario) throws SQLException {
    	// Verificar se o usuário existe antes de tentar atualizar
        Usuario usuarioExistente = buscar(usuario.getId());
        
        if (usuarioExistente == null) {
            System.out.println("Erro: Usuário com o ID " + usuario.getId() + " não encontrado.");
            return; // Interrompe a execução se o ID não existir
        }
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, rua = ?, numero = ?, cidade = ?, estado = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getRua());
            stmt.setInt(5, usuario.getNumero());
            stmt.setString(6, usuario.getCidade());
            stmt.setString(7, usuario.getEstado());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao Atualizar Usuário: " + e.getMessage());
            throw e; // Re-lança a exceção para que ela seja tratada em outro lugar, se necessário
        }
    }

    @Override
    public void remover(Usuario usuario) {
    	// Verificar se o usuário existe antes de tentar atualizar
        Usuario usuarioExistente = buscar(usuario.getId());
        
        if (usuarioExistente == null) {
            System.out.println("Erro: Usuário com o ID " + usuario.getId() + " não encontrado.");
            return; // Interrompe a execução se o ID não existir
        }
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Usuario> listarTodos() throws SQLException {
        String sql = "SELECT * FROM usuario";
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setRua(rs.getString("rua"));
                usuario.setNumero(rs.getInt("numero"));
                usuario.setCidade(rs.getString("cidade"));
                usuario.setEstado(rs.getString("estado"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }
}
