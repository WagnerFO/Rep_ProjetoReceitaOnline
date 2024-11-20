package receitasOnline.Repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import receitasOnline.Entidades.Categoria;
import receitasOnline.Entidades.Receita;
import receitasOnline.Entidades.ReceitaPrincipal;
import receitasOnline.Factory.ConnectionSingleton;
import receitasOnline.IRepositorio.IRepositorioReceitaPrincipal;

public class RepositorioReceitaPrincipalSQL implements IRepositorioReceitaPrincipal {

    private Connection connection;
    
    public RepositorioReceitaPrincipalSQL() {
        try {
            this.connection = ConnectionSingleton.getInstance().conexao;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void adicionar(ReceitaPrincipal receita) throws SQLException, InterruptedException {
        String insertReceitaSQL = "INSERT INTO receita (titulo, descricao, modo_preparo, categoriaId) VALUES (?, ?, ?, ?)";
        String insertReceitaPrincipalSQL = "INSERT INTO receita_principal (id, dificuldade, tempo_preparo) VALUES (?, ?, ?)";
        String selectIngredienteSQL = "SELECT id FROM ingrediente WHERE nome = ?";
        String insertIngredienteSQL = "INSERT INTO ingrediente (nome) VALUES (?)";
        String insertReceitaIngredienteSQL = "INSERT INTO receita_ingrediente (id_receita, id_ingrediente) VALUES (?, ?)";
        
        try (Connection connection = ConnectionSingleton.getInstance().conexao) {
            connection.setAutoCommit(false); // Início da transação
            
            try (PreparedStatement stmtReceita = connection.prepareStatement(insertReceitaSQL, Statement.RETURN_GENERATED_KEYS)) {
                // Inserir na tabela receita
                stmtReceita.setString(1, receita.getTitulo());
                stmtReceita.setString(2, receita.getDescricao());
                stmtReceita.setString(3, receita.getModoPreparo());
                stmtReceita.setInt(4, receita.getCategoria().getId());
                stmtReceita.executeUpdate();
                
                // Obter o ID gerado para receita
                ResultSet rsReceita = stmtReceita.getGeneratedKeys();
                int idReceita;
                if (rsReceita.next()) {
                    idReceita = rsReceita.getInt(1);
                } else {
                    throw new SQLException("Erro ao obter o ID da receita inserida.");
                }
                
                // Inserir na tabela receita_principal
                try (PreparedStatement stmtReceitaPrincipal = connection.prepareStatement(insertReceitaPrincipalSQL)) {
                    stmtReceitaPrincipal.setInt(1, idReceita);
                    stmtReceitaPrincipal.setString(2, receita.getDificuldade());
                    stmtReceitaPrincipal.setInt(3, receita.getTempoPreparo());
                    stmtReceitaPrincipal.executeUpdate();
                }
                
                // Gerenciar os ingredientes
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
    public ReceitaPrincipal buscar(int id) {
        String sql = """
            SELECT r.id, r.titulo, r.descricao, r.modo_preparo, r.categoriaId, 
                   rp.dificuldade, rp.tempo_preparo 
            FROM receita r
            INNER JOIN receita_principal rp ON r.id = rp.id
            WHERE r.id = ?
        """;

        ReceitaPrincipal receita = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                receita = new ReceitaPrincipal(); // Instância correta
                receita.setId(rs.getInt("id"));
                receita.setTitulo(rs.getString("titulo"));
                receita.setDescricao(rs.getString("descricao"));
                receita.setModoPreparo(rs.getString("modo_preparo"));

                // Preenche os atributos específicos de ReceitaPrincipal
                receita.setDificuldade(rs.getString("dificuldade"));
                receita.setTempoPreparo(rs.getInt("tempo_preparo"));

                // Busca a categoria associada
                int categoriaId = rs.getInt("categoriaId");
                Categoria categoria = new Categoria();
                categoria.setId(categoriaId);
                receita.setCategoria(categoria); // Associa a categoria à receita
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ReceitaPrincipal: " + e.getMessage());
            e.printStackTrace();
        }
        return receita;
    }

    @Override
    public ArrayList<ReceitaPrincipal> buscarReceitasPorIngrediente(String nomeIngrediente) throws SQLException, InterruptedException {
        String sql = """
            SELECT r.id, r.titulo, r.descricao, r.modo_preparo, rp.dificuldade, rp.tempo_preparo
            FROM receita r
            INNER JOIN receita_principal rp ON r.id = rp.id
            INNER JOIN receita_ingrediente ri ON r.id = ri.id_receita
            INNER JOIN ingrediente i ON ri.id_ingrediente = i.id
            WHERE i.nome = ?
        """;

        ArrayList<ReceitaPrincipal> receitas = new ArrayList<>();

        try (Connection connection = ConnectionSingleton.getInstance().conexao;
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomeIngrediente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ReceitaPrincipal receita = new ReceitaPrincipal();
                receita.setId(rs.getInt("id"));
                receita.setTitulo(rs.getString("titulo"));
                receita.setDescricao(rs.getString("descricao"));
                receita.setModoPreparo(rs.getString("modo_preparo"));
                receita.setDificuldade(rs.getString("dificuldade"));
                receita.setTempoPreparo(rs.getInt("tempo_preparo"));
                receitas.add(receita);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar receitas por ingrediente: " + e.getMessage());
            e.printStackTrace();
        }
        return receitas;
    }

    @Override
    public void atualizar(ReceitaPrincipal receita) throws SQLException {
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
    public void remover(ReceitaPrincipal receita) {
        String sql = "DELETE FROM receita WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, receita.getId());
            stmt.executeUpdate();   
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<ReceitaPrincipal> listarTodos() throws SQLException {
        String sql = """
            SELECT r.id, r.titulo, r.descricao, r.modo_preparo, rp.dificuldade, rp.tempo_preparo, r.categoriaId
            FROM receita r
            INNER JOIN receita_principal rp ON r.id = rp.id
        """;

        ArrayList<ReceitaPrincipal> receitas = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Obter os dados da receita principal
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");
                String modoPreparo = rs.getString("modo_preparo");
                String dificuldade = rs.getString("dificuldade");
                int tempoPreparo = rs.getInt("tempo_preparo");

                // Obter o ID da categoria associada à receita
                int categoriaId = rs.getInt("categoriaId");
                Categoria categoria = new Categoria(); // Criar a categoria
                categoria.setId(categoriaId); // Definir o ID da categoria

                // Criar a ReceitaPrincipal com a categoria associada
                ReceitaPrincipal receita = new ReceitaPrincipal(titulo, descricao, modoPreparo, dificuldade, new ArrayList<>(), categoria, tempoPreparo);
                receita.setId(id);
                receitas.add(receita);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar todas as receitas principais: " + e.getMessage());
            e.printStackTrace();
        }
        return receitas;
    }

    
}
