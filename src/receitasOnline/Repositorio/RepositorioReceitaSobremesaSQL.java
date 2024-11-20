package receitasOnline.Repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import receitasOnline.Entidades.Categoria;
import receitasOnline.Entidades.Receita;

import receitasOnline.Entidades.ReceitaSobremesa;
import receitasOnline.Factory.ConnectionSingleton;
import receitasOnline.IRepositorio.IRepositorioReceitaSobremesa;

public class RepositorioReceitaSobremesaSQL implements IRepositorioReceitaSobremesa {

    private Connection connection;
    
    public RepositorioReceitaSobremesaSQL() {
        try {
            this.connection = ConnectionSingleton.getInstance().conexao;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void adicionar(ReceitaSobremesa receita) throws SQLException, InterruptedException {
        String insertReceitaSQL = "INSERT INTO receita (titulo, descricao, modo_preparo, categoriaId) VALUES (?, ?, ?, ?)";
        String insertReceitaSobremesaSQL = "INSERT INTO receita_sobremesa (id, contem_acucar, tipo_acucar) VALUES (?, ?, ?)";
        String selectIngredienteSQL = "SELECT id FROM ingrediente WHERE nome = ?";
        String insertIngredienteSQL = "INSERT INTO ingrediente (nome) VALUES (?)";
        String insertReceitaIngredienteSQL = "INSERT INTO receita_ingrediente (id_receita, id_ingrediente) VALUES (?, ?)";
        
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
                
                try (PreparedStatement stmtSobremesa = connection.prepareStatement(insertReceitaSobremesaSQL)) {
                    stmtSobremesa.setInt(1, idReceita);
                    stmtSobremesa.setBoolean(2, receita.isContemAcucar());
                    stmtSobremesa.setString(3, receita.isContemAcucar() ? receita.getTipoAcucar() : null);
                    stmtSobremesa.executeUpdate();
                }

                for (String nomeIngrediente : receita.getIngredientes()) {
                    int idIngrediente;
                    
                    try (PreparedStatement selectStmt = connection.prepareStatement(selectIngredienteSQL)) {
                        selectStmt.setString(1, nomeIngrediente);
                        ResultSet rsIngrediente = selectStmt.executeQuery();
                        if (rsIngrediente.next()) {
                            idIngrediente = rsIngrediente.getInt("id");
                        } else {
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
    public ReceitaSobremesa buscar(int id) {
        String sql = "SELECT * FROM receita WHERE id = ?";
        ReceitaSobremesa receita = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                receita = new ReceitaSobremesa();  // Instanciando ReceitaSobremesa
                receita.setId(rs.getInt("id"));
                receita.setTitulo(rs.getString("titulo"));
                receita.setDescricao(rs.getString("descricao"));
                receita.setModoPreparo(rs.getString("modo_preparo"));

                int categoriaId = rs.getInt("categoriaId");
                Categoria categoria = new Categoria();
                categoria.setId(categoriaId);
                receita.setCategoria(categoria);

                // Busca informações específicas de ReceitaSobremesa
                String sqlSobremesa = "SELECT contem_acucar, tipo_acucar FROM receita_sobremesa WHERE receita_id = ?"; // Corrigido para chave estrangeira
                try (PreparedStatement stmtSobremesa = connection.prepareStatement(sqlSobremesa)) {
                    stmtSobremesa.setInt(1, id);  // Usando o id de receita como chave para a sobremesa
                    ResultSet rsSobremesa = stmtSobremesa.executeQuery();
                    if (rsSobremesa.next()) {
                        receita.setContemAcucar(rsSobremesa.getBoolean("contem_acucar"));
                        receita.setTipoAcucar(rsSobremesa.getString("tipo_acucar"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Receita: " + e.getMessage());
            e.printStackTrace();
        }
        return receita;  // Retorna a receita (pode ser null caso não encontrada)
    }
    
    @Override
    public ArrayList<ReceitaSobremesa> buscarReceitasPorIngrediente(String nomeIngrediente) throws SQLException, InterruptedException {
        String sql = """
            SELECT r.id, r.titulo, r.descricao, r.modo_preparo
            FROM receita r
            INNER JOIN receita_ingrediente ri ON r.id = ri.id_receita
            INNER JOIN ingrediente i ON ri.id_ingrediente = i.id
            WHERE i.nome = ?
        """;

        ArrayList<ReceitaSobremesa> receitasSobremesas = new ArrayList<>();  // Lista de ReceitaSobremesa
        try (Connection connection = ConnectionSingleton.getInstance().conexao;
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomeIngrediente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ReceitaSobremesa receita = new ReceitaSobremesa();  // Instanciando ReceitaSobremesa
                receita.setId(rs.getInt("id"));
                receita.setTitulo(rs.getString("titulo"));
                receita.setDescricao(rs.getString("descricao"));
                receita.setModoPreparo(rs.getString("modo_preparo"));
                receitasSobremesas.add(receita);  // Adicionando à lista de ReceitaSobremesa
            }
        }
        return receitasSobremesas;  // Retorna a lista de receitas
    }


    @Override
    public void atualizar(ReceitaSobremesa receita) throws SQLException {
        // SQL para atualizar os campos da receita
        String sql = """
            UPDATE receita 
            SET titulo = ?, descricao = ?, modo_preparo = ?, contem_acucar = ?, tipo_acucar = ?
            WHERE id = ?
        """;

        // Usando PreparedStatement para atualizar os dados
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Definindo os valores dos parâmetros do SQL
            stmt.setString(1, receita.getTitulo());         // Atualiza título
            stmt.setString(2, receita.getDescricao());      // Atualiza descrição
            stmt.setString(3, receita.getModoPreparo());    // Atualiza modo de preparo
            stmt.setBoolean(4, receita.isContemAcucar());   // Atualiza se contém açúcar
            stmt.setString(5, receita.getTipoAcucar());     // Atualiza o tipo de açúcar
            stmt.setInt(6, receita.getId());                // Filtra pela id da receita

            // Executa o update na base de dados
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Caso ocorra erro, printa e repassa a exceção
            System.out.println("Erro ao alterar Receita: " + e.getMessage());
            throw e;  // Repassa a exceção para o método que chamou
        }
    }


    @Override
    public void remover(ReceitaSobremesa receita) {
        String sql = "DELETE FROM receita WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, receita.getId());
            stmt.executeUpdate();  // Executa a remoção da receita
        } catch (SQLException e) {
            System.out.println("Erro ao remover receita: " + e.getMessage());
        }
    }


    @Override
    public ArrayList<ReceitaSobremesa> listarTodos() throws SQLException {
        String sql = "SELECT * FROM receita";
        ArrayList<ReceitaSobremesa> receitas = new ArrayList<>();  // Alterado para ArrayList<ReceitaSobremesa>
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Obter os dados da receita
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");

                // Obter o ID da categoria associada à receita
                int categoriaId = rs.getInt("categoriaId");
                Categoria categoria = new Categoria();
                categoria.setId(categoriaId);  // Definir o ID da categoria

                // Criar a receita com a categoria associada
                ReceitaSobremesa receitaSobremesa = new ReceitaSobremesa(id, titulo, descricao, "", null, categoria, false, null);
                // Adicionar à lista
                receitas.add(receitaSobremesa);
            }
        }
        return receitas;
    }

    
}
