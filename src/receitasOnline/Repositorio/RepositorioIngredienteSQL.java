package receitasOnline.Repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import receitasOnline.Entidades.Ingrediente;
import receitasOnline.Factory.ConnectionSingleton;
import receitasOnline.IRepositorio.IRepositorioIngrediente;

public class RepositorioIngredienteSQL implements IRepositorioIngrediente {

    private Connection connection;
    
    public RepositorioIngredienteSQL() {
        try {
            this.connection = ConnectionSingleton.getInstance().conexao;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void adicionar(Ingrediente ingrediente) throws SQLException {
        String sql = "INSERT INTO ingrediente (nome, quantidade) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ingrediente.getNome());
            stmt.setDouble(2, ingrediente.getQuantidade());
            stmt.executeUpdate();
        }
    }

    @Override
    public Ingrediente buscar(int id) {
        String sql = "SELECT * FROM ingrediente WHERE id = ?";
        Ingrediente ingrediente = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ingrediente = new Ingrediente();
                ingrediente.setId(rs.getInt("id"));
                ingrediente.setNome(rs.getString("nome"));
                ingrediente.setQuantidade(rs.getDouble("quantidade"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Ingrediente " + e.getMessage());
            e.printStackTrace();
        }
        return ingrediente;
    }

    @Override
    public void atualizar(Ingrediente ingrediente) throws SQLException {
        String sql = "UPDATE ingrediente SET nome = ?, quantidade = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ingrediente.getNome());
            stmt.setDouble(2, ingrediente.getQuantidade());
            stmt.setInt(3, ingrediente.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao alterar Ingrediente " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void remover(Ingrediente ingrediente) {
        String sql = "DELETE FROM ingrediente WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ingrediente.getId());
            stmt.executeUpdate();   
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Ingrediente> listarTodos() throws SQLException {
        String sql = "SELECT * FROM ingrediente";
        ArrayList<Ingrediente> ingredientes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Ingrediente ingrediente = new Ingrediente(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("quantidade")
                );
                ingredientes.add(ingrediente);
            }
        }
        return ingredientes;
    }
}
