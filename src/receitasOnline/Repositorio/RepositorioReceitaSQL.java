package receitasOnline.Repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import receitasOnline.Entidades.Receita;
import receitasOnline.Factory.ConnectionSingleton;
import receitasOnline.IRepositorio.IRepositorioReceita;

public class RepositorioReceitaSQL implements IRepositorioReceita {

    private Connection connection;
    
    public RepositorioReceitaSQL() {
        try {
            this.connection = ConnectionSingleton.getInstance().conexao;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void adicionar(Receita receita) throws SQLException, InterruptedException {
        String insertReceitaSQL = "INSERT INTO receita (titulo, descricao, modo_preparo) VALUES (?, ?, ?)";
        String selectIngredienteSQL = "SELECT id FROM ingrediente WHERE nome = ?";
        String insertIngredienteSQL = "INSERT INTO ingrediente (nome) VALUES (?)";
        String insertReceitaIngredienteSQL = "INSERT INTO receita_ingrediente (id_receita, id_ingrediente) VALUES (?, ?)";
        
        try (Connection connection = ConnectionSingleton.getInstance().conexao) {
            connection.setAutoCommit(false); // Início da transação
            
            // Inserir a receita
            int idReceita;
            try (PreparedStatement stmt = connection.prepareStatement(insertReceitaSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, receita.getTitulo());
                stmt.setString(2, receita.getDescricao());
                stmt.setString(3, receita.getModoPreparo());
                stmt.executeUpdate();
                
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    idReceita = rs.getInt(1); // Obter o ID gerado da receita
                } else {
                    throw new SQLException("Erro ao obter o ID da receita inserida.");
                }
            }
    
            // Associar ingredientes à receita
            for (String nomeIngrediente : receita.getIngredientes()) {
                int idIngrediente;
    
                // Verificar se o ingrediente já existe
                try (PreparedStatement stmt = connection.prepareStatement(selectIngredienteSQL)) {
                    stmt.setString(1, nomeIngrediente);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        idIngrediente = rs.getInt("id");
                    } else {
                        // Inserir ingrediente, caso não exista
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertIngredienteSQL, Statement.RETURN_GENERATED_KEYS)) {
                            insertStmt.setString(1, nomeIngrediente);
                            insertStmt.executeUpdate();
                            ResultSet rsInsert = insertStmt.getGeneratedKeys();
                            if (rsInsert.next()) {
                                idIngrediente = rsInsert.getInt(1);
                            } else {
                                throw new SQLException("Erro ao obter o ID do ingrediente inserido.");
                            }
                        }
                    }
                }
    
                // Inserir na tabela de relacionamento
                try (PreparedStatement stmt = connection.prepareStatement(insertReceitaIngredienteSQL)) {
                    stmt.setInt(1, idReceita);
                    stmt.setInt(2, idIngrediente);
                    stmt.executeUpdate();
                }
            }
    
            connection.commit(); // Confirmar a transação
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao adicionar receita com ingredientes.", e);
        }
    }
    
    

    @Override
    public Receita buscar(int id) {
        String sql = "SELECT * FROM receita WHERE id = ?";
        Receita receita = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                receita = new Receita();
                receita.setId(rs.getInt("id"));
                receita.setTitulo(rs.getString("titulo"));
                receita.setDescricao(rs.getString("descricao"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Receita " + e.getMessage());
            e.printStackTrace();
        }
        return receita;
    }

    @Override
    public ArrayList<Receita> buscarReceitasPorIngrediente(String nomeIngrediente) throws SQLException, InterruptedException {
    String sql = """
        SELECT r.id, r.titulo, r.descricao, r.modo_preparo
        FROM receita r
        INNER JOIN receita_ingrediente ri ON r.id = ri.id_receita
        INNER JOIN ingrediente i ON ri.id_ingrediente = i.id
        WHERE i.nome = ?
    """;

    ArrayList<Receita> receitas = new ArrayList<>();
    try (Connection connection = ConnectionSingleton.getInstance().conexao;
         PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, nomeIngrediente);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Receita receita = new Receita();
            receita.setId(rs.getInt("id"));
            receita.setTitulo(rs.getString("titulo"));
            receita.setDescricao(rs.getString("descricao"));
            receita.setModoPreparo(rs.getString("modo_preparo"));
            receitas.add(receita);
        }
    }
    return receitas;
}

    @Override
    public void atualizar(Receita receita) throws SQLException {
        String sql = "UPDATE receita SET titulo = ?, descricao = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, receita.getTitulo());
            stmt.setString(2, receita.getDescricao());
            stmt.setInt(3, receita.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao alterar Receita " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void remover(Receita receita) {
        String sql = "DELETE FROM receita WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, receita.getId());
            stmt.executeUpdate();   
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Receita> listarTodos() throws SQLException {
        String sql = "SELECT * FROM receita";
        ArrayList<Receita> receitas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Receita receita = new Receita(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descricao")
                );
                receitas.add(receita);
            }
        }
        return receitas;
    }
}
