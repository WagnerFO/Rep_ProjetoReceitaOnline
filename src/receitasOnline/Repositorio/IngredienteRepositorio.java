package receitasOnline.Repositorio;

import receitasOnline.Entidades.Ingrediente;
import receitasOnline.Factory.ConnectionFactory;
import receitasOnline.IRepositorio.IIngredienteRepositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredienteRepositorio implements IIngredienteRepositorio {
    private Connection connection; // Conexão com o banco de dados

    // Construtor que recebe a conexão
    public IngredienteRepositorio(Connection connection) {
        this.connection = connection;
    }

    // Método para adicionar um ingrediente ao banco de dados
    @Override
    public void adicionar(Ingrediente ingrediente) {
        String sql = "INSERT INTO ingrediente (nome_ingrediente, quantidade) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ingrediente.getNome());
            stmt.setBigDecimal(2, ingrediente.getQuantidade());
            stmt.executeUpdate(); // Executa a inserção
            System.out.println("Ingrediente adicionado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar ingrediente", e);
        }
    }

    // Método para buscar um ingrediente pelo ID
    @Override
    public Ingrediente buscar(int id) {
        String sql = "SELECT * FROM ingrediente WHERE id_ingrediente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Ingrediente(
                        rs.getInt("id_ingrediente"),
                        rs.getString("nome_ingrediente"),
                        rs.getBigDecimal("quantidade")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar ingrediente", e);
        }
        throw new IllegalArgumentException("Ingrediente não encontrado.");
    }

    // Método para atualizar um ingrediente no banco de dados
    @Override
    public void atualizar(Ingrediente ingrediente) {
        String sql = "UPDATE ingrediente SET nome_ingrediente = ?, quantidade = ? WHERE id_ingrediente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ingrediente.getNome());
            stmt.setBigDecimal(2, ingrediente.getQuantidade());
            stmt.setInt(3, ingrediente.getId());
            stmt.executeUpdate(); // Executa a atualização
            System.out.println("Ingrediente atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar ingrediente", e);
        }
    }

    // Método para remover um ingrediente do banco de dados
    @Override
    public void remover(int id) {
        String sql = "DELETE FROM ingrediente WHERE id_ingrediente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate(); // Executa a remoção
            System.out.println("Ingrediente removido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover ingrediente", e);
        }
    }

    // Método para listar todos os ingredientes no banco de dados
    @Override
    public List<Ingrediente> listarTodos() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        String sql = "SELECT * FROM ingrediente";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ingredientes.add(new Ingrediente(
                    rs.getInt("id_ingrediente"),
                    rs.getString("nome_ingrediente"),
                    rs.getBigDecimal("quantidade")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar ingredientes", e);
        }
        return ingredientes;
    }
}
