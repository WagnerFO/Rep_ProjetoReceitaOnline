package receitasOnline.Repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    	try(PreparedStatement stmt = connection.prepareStatement(sql)){
    		stmt.setString(1, ingrediente.getNome());
    		stmt.setDouble(2,  ingrediente.getQuantidade());
    		stmt.executeUpdate();
    	}
    }
    
    
    public int obterOuCriarIngrediente(String nomeIngrediente, double quantidadeIngrediente, Connection connection) throws SQLException {
        String selectIngredienteSQL = "SELECT id FROM ingrediente WHERE nome = ?";
        String insertIngredienteSQL = "INSERT INTO ingrediente (nome, quantidade) VALUES (?, ?)";

        // Verificar se o ingrediente já existe
        try (PreparedStatement selectStmt = connection.prepareStatement(selectIngredienteSQL)) {
            selectStmt.setString(1, nomeIngrediente);
            ResultSet rsIngrediente = selectStmt.executeQuery();
            if (rsIngrediente.next()) {
                return rsIngrediente.getInt("id");  // Se existir, retorna o ID
            }
        }

        // Caso o ingrediente não exista, cria um novo
        try (PreparedStatement insertStmt = connection.prepareStatement(insertIngredienteSQL, Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setString(1, nomeIngrediente);
            insertStmt.setDouble(2, quantidadeIngrediente);  // Inserir a quantidade
            insertStmt.executeUpdate();  // Executa o comando de inserção

            ResultSet rsInsert = insertStmt.getGeneratedKeys();
            if (rsInsert.next()) {
                return rsInsert.getInt(1);  // Retorna o ID do ingrediente inserido
            } else {
                throw new SQLException("Erro ao obter o ID do ingrediente inserido.");
            }
        }
    }
    
    @Override
    public Ingrediente buscar(int id) throws SQLException {
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
            System.out.println("Erro ao buscar Ingrediente: " + e.getMessage());
            e.printStackTrace();
        }
        return ingrediente;
    }
    
    @Override
    public void atualizar(Ingrediente ingrediente) throws SQLException {
        Ingrediente ingredienteExistente = buscar(ingrediente.getId());

        if (ingredienteExistente == null) {
            System.out.println("Erro: Ingrediente com o ID " + ingrediente.getId() + " não encontrado.");
            return; // Interrompe a execução se o ID não existir
        }

        String sql = "UPDATE ingrediente SET nome = ?, quantidade = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ingrediente.getNome());
            stmt.setDouble(2, ingrediente.getQuantidade());
            stmt.setInt(3, ingrediente.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao alterar Ingrediente: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void remover(Ingrediente ingrediente) throws SQLException {
    	Ingrediente ingredienteExistente = buscar(ingrediente.getId());

    	if (ingredienteExistente == null) {
    	    System.out.println("Erro: Ingrediente com o ID " + ingrediente.getId() + " não encontrada.");
    	    return; // Interrompe a execução se o ID não existir
    	}
    	
        String sql = "DELETE FROM ingrediente WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ingrediente.getId());
            int rowsAffected = stmt.executeUpdate();  
            
            if(rowsAffected >0 ) {
            	System.out.println("Ingrediente " + ingredienteExistente.getNome() + " removido com sucesso.");
            }
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
