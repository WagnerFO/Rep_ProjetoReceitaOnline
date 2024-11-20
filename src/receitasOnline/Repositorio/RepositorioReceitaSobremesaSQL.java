package receitasOnline.Repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import receitasOnline.Entidades.Categoria;
import receitasOnline.Entidades.Receita;
import receitasOnline.Factory.ConnectionSingleton;
import receitasOnline.IRepositorio.IRepositorioReceita;

public class RepositorioReceitaSobremesaSQL implements IRepositorioReceita {

    private Connection connection;
    
    public RepositorioReceitaSobremesaSQL() {
        try {
            this.connection = ConnectionSingleton.getInstance().conexao;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void adicionar(Receita receita) throws SQLException, InterruptedException {
        String insertReceitaSQL = "INSERT INTO receita (titulo, descricao, modo_preparo, categoriaId) VALUES (?, ?, ?, ?)";
        String selectIngredienteSQL = "SELECT id FROM ingrediente WHERE nome = ?";
        String insertIngredienteSQL = "INSERT INTO ingrediente (nome) VALUES (?)";
        String insertReceitaIngredienteSQL = "INSERT INTO receita_ingrediente (id_receita, id_ingrediente) VALUES (?, ?)";
        
        // Utilizando a conexão existente, sem criar uma nova
        try (Connection connection = ConnectionSingleton.getInstance().conexao) {
            connection.setAutoCommit(false); // Início da transação
            
            try (PreparedStatement stmt = connection.prepareStatement(insertReceitaSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, receita.getTitulo());
                stmt.setString(2, receita.getDescricao());
                stmt.setString(3, receita.getModoPreparo());
                stmt.setInt(4, receita.getCategoria().getId());
                stmt.executeUpdate();
                
                ResultSet rs = stmt.getGeneratedKeys();
                int idReceita = 0;
                if (rs.next()) {
                    idReceita = rs.getInt(1); // Obter o ID gerado da receita
                } else {
                    throw new SQLException("Erro ao obter o ID da receita inserida.");
                }
                
                for (String nomeIngrediente : receita.getIngredientes()) {
                    int idIngrediente;
                    
                    // Verificar se o ingrediente já existe
                    try (PreparedStatement selectStmt = connection.prepareStatement(selectIngredienteSQL)) {
                        selectStmt.setString(1, nomeIngrediente);
                        ResultSet rsIngrediente = selectStmt.executeQuery();
                        if (rsIngrediente.next()) {
                            idIngrediente = rsIngrediente.getInt("id");
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
                        
                        // Inserir na tabela de relacionamento
                        try (PreparedStatement insertReceitaIngredienteStmt = connection.prepareStatement(insertReceitaIngredienteSQL)) {
                            insertReceitaIngredienteStmt.setInt(1, idReceita);
                            insertReceitaIngredienteStmt.setInt(2, idIngrediente);
                            insertReceitaIngredienteStmt.executeUpdate();
                        }
                    }
                }
                
                connection.commit(); // Confirmar a transação
            } catch (SQLException e) {
                connection.rollback(); // Rollback se houver erro
                throw new SQLException("Erro ao adicionar receita com ingredientes.", e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro de conexão ou na execução de comandos SQL.", e);
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
                receita.setModoPreparo(rs.getString("modoPreparo"));

                // Agora buscamos a categoria associada
                int categoriaId = rs.getInt("categoriaId");
                Categoria categoria = new Categoria();
                categoria.setId(categoriaId);
                receita.setCategoria(categoria);  // Associa a categoria à receita
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
                // Obter os dados da receita
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");
    
                // Obter o ID da categoria associada à receita
                int categoriaId = rs.getInt("categoriaId");
                Categoria categoria = new Categoria();  // Criar a categoria
                categoria.setId(categoriaId);  // Definir o ID da categoria
    
                // Criar a receita com a categoria associada
                Receita receita = new Receita (id, titulo, descricao, categoria);
                receitas.add(receita);
            }
        }
        return receitas;
    }
    
}
