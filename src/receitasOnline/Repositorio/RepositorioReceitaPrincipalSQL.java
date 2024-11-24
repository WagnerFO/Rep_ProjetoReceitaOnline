package receitasOnline.Repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import receitasOnline.Entidades.Categoria;
import receitasOnline.Entidades.Ingrediente;
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

   
    @Override
    public void adicionar(ReceitaPrincipal receita) throws SQLException, InterruptedException {
        String insertReceitaSQL = "INSERT INTO receita_principal (titulo, descricao, modo_preparo, categoriaId, tempo_preparo) VALUES (?, ?, ?, ?, ?)";
        String insertReceitaIngredienteSQL = "INSERT INTO ReceitaP_ingrediente (id_receitaPrincipal, id_ingrediente) VALUES (?, ?)"; // Alterado o nome da tabela
        String insertReceitaCategoriaSQL = "INSERT INTO receitaP_categoria (id_receitaPrincipal, id_categoria) VALUES (?, ?)"; // Tabela de associação

        try (Connection connection = ConnectionSingleton.getInstance().conexao) {
            connection.setAutoCommit(false); // Início da transação

            // Inserir a receita principal
            try (PreparedStatement receitaStmt = connection.prepareStatement(insertReceitaSQL, Statement.RETURN_GENERATED_KEYS)) {
                receitaStmt.setString(1, receita.getTitulo());
                receitaStmt.setString(2, receita.getDescricao());
                receitaStmt.setString(3, receita.getModoPreparo());
                
                // Verificar ou criar a categoria
                int idCategoria = obterOuCriarCategoria(receita.getCategoria(), connection);
                receitaStmt.setInt(4, idCategoria); // Usar ID da categoria
                receitaStmt.setInt(5, receita.getTempoPreparo());
                receitaStmt.executeUpdate();

                // Obter ID da receita
                ResultSet rsReceita = receitaStmt.getGeneratedKeys();
                if (!rsReceita.next()) {
                    throw new SQLException("Erro ao obter o ID da receita inserida.");
                }
                int idReceita = rsReceita.getInt(1);

                // Adicionar ingredientes e associar à receita
                for (Ingrediente ingrediente : receita.getIngredientes()) {
                    // Verificar ou criar ingrediente
                    int idIngrediente = obterOuCriarIngrediente(ingrediente.getNome(), ingrediente.getQuantidade(), connection);

                    // Inserir na tabela de associação ReceitaP_ingrediente (relacionamento entre receita e ingrediente)
                    try (PreparedStatement receitaIngredienteStmt = connection.prepareStatement(insertReceitaIngredienteSQL)) {
                        receitaIngredienteStmt.setInt(1, idReceita); // Associar com a receita
                        receitaIngredienteStmt.setInt(2, idIngrediente); // Associar com o ingrediente
                        receitaIngredienteStmt.executeUpdate();
                    }
                }

                // Associar receita à categoria (Tabela de Associação 'receitaP_categoria')
                try (PreparedStatement receitaCategoriaStmt = connection.prepareStatement(insertReceitaCategoriaSQL)) {
                    receitaCategoriaStmt.setInt(1, idReceita); // Passando o ID da receita para 'id_receitaPrincipal'
                    receitaCategoriaStmt.setInt(2, idCategoria); // Passando o ID da categoria para 'id_categoria'
                    receitaCategoriaStmt.executeUpdate();
                }

                connection.commit(); // Confirmar a transação
            } catch (SQLException e) {
                connection.rollback(); // Reverter em caso de erro
                throw e;
            }
        }
    }
    
    // Método para verificar ou criar a categoria
    private int obterOuCriarCategoria(Categoria categoria, Connection connection) throws SQLException {
        String selectCategoriaSQL = "SELECT id FROM categoria WHERE nome = ?";
        String insertCategoriaSQL = "INSERT INTO categoria (nome) VALUES (?)";
        
        // Verificar se a categoria já existe
        try (PreparedStatement selectStmt = connection.prepareStatement(selectCategoriaSQL)) {
            selectStmt.setString(1, categoria.getNome());
            ResultSet rsCategoria = selectStmt.executeQuery();
            if (rsCategoria.next()) {
                return rsCategoria.getInt("id"); // Se existir, retorna o ID
            }
        }

        // Se não existir, cria uma nova categoria
        try (PreparedStatement insertStmt = connection.prepareStatement(insertCategoriaSQL, Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setString(1, categoria.getNome());
            insertStmt.executeUpdate();
            ResultSet rsInsert = insertStmt.getGeneratedKeys();
            if (rsInsert.next()) {
                return rsInsert.getInt(1); // Retorna o ID da nova categoria inserida
            } else {
                throw new SQLException("Erro ao obter o ID da categoria inserida.");
            }
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
    public void buscar(ReceitaPrincipal receita) throws SQLException {
        String sql = "SELECT * FROM receita_principal WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, receita.getId()); // Definir o ID da receita
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                receita.setId(rs.getInt("id"));
                receita.setTitulo(rs.getString("titulo"));
                receita.setDescricao(rs.getString("descricao"));
                receita.setModoPreparo(rs.getString("modo_preparo"));
                receita.setTempoPreparo(rs.getInt("tempo_preparo"));
                
                int categoriaId = rs.getInt("categoriaId");
                Categoria categoria = new Categoria();
                categoria.setId(categoriaId); // Preencher a categoria
                receita.setCategoria(categoria);
                
                // Buscar categoria associada
                String sqlCategoria = "SELECT nome FROM categoria WHERE id = ?";
                try (PreparedStatement stmtCategoria = connection.prepareStatement(sqlCategoria)) {
                    stmtCategoria.setInt(1, categoriaId);
                    try (ResultSet rsCategoria = stmtCategoria.executeQuery()) {
                        if (rsCategoria.next()) {
                            categoria.setNome(rsCategoria.getString("nome"));
                        }
                    }
                }

                // Buscar ingredientes associados à receita
                String sqlIngredientes = "SELECT i.nome, ri.id_ingrediente, i.quantidade FROM ingrediente i " +
                                          "JOIN ReceitaP_ingrediente ri ON ri.id_ingrediente = i.id " +
                                          "WHERE ri.id_receitaPrincipal = ?";
                try (PreparedStatement stmtIngredientes = connection.prepareStatement(sqlIngredientes)) {
                    stmtIngredientes.setInt(1, receita.getId()); // Definir o ID da receita
                    try (ResultSet rsIngredientes = stmtIngredientes.executeQuery()) {
                        List<Ingrediente> ingredientes = new ArrayList<>();
                        while (rsIngredientes.next()) {
                            String nomeIngrediente = rsIngredientes.getString("nome");
                            double quantidadeIngrediente = rsIngredientes.getDouble("quantidade");
                            Ingrediente ingrediente = new Ingrediente(nomeIngrediente, quantidadeIngrediente);
                            ingredientes.add(ingrediente);
                        }
                        receita.setIngredientes(ingredientes); // Associar os ingredientes à receita
                    }
                }
            } else {
                receita.setId(0);  // Se não encontrar a receita, o ID será 0
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar a receita: " + e.getMessage());
        }
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
    public void atualizar(ReceitaPrincipal receita) throws SQLException, InterruptedException {
        // Validação para evitar valores nulos
        if (receita.getTitulo() == null || receita.getTitulo().isEmpty()) {
            throw new IllegalArgumentException("O título da receita não pode ser nulo ou vazio.");
        }

        if (receita.getDescricao() == null || receita.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("A descrição da receita não pode ser nula ou vazia.");
        }

        if (receita.getModoPreparo() == null || receita.getModoPreparo().isEmpty()) {
            throw new IllegalArgumentException("O modo de preparo não pode ser nulo ou vazio.");
        }

        // Verificar se a categoria tem um nome válido
        if (receita.getCategoria() == null || receita.getCategoria().getNome() == null || receita.getCategoria().getNome().isEmpty()) {
            throw new IllegalArgumentException("A categoria da receita não pode ser nula ou sem nome.");
        }

        // Continuar com o código normal de atualização
        String updateReceitaSQL = "UPDATE receita_principal SET titulo = ?, descricao = ?, modo_preparo = ?, categoriaId = ?, tempo_preparo = ? WHERE id = ?";
        String deleteReceitaIngredienteSQL = "DELETE FROM ReceitaP_ingrediente WHERE id_receitaPrincipal = ?"; 
        String insertReceitaIngredienteSQL = "INSERT INTO ReceitaP_ingrediente (id_receitaPrincipal, id_ingrediente) VALUES (?, ?)";
        String deleteReceitaCategoriaSQL = "DELETE FROM receitaP_categoria WHERE id_receitaPrincipal = ?";
        String insertReceitaCategoriaSQL = "INSERT INTO receitaP_categoria (id_receitaPrincipal, id_categoria) VALUES (?, ?)";

        try (Connection connection = ConnectionSingleton.getInstance().conexao) {
            connection.setAutoCommit(false); // Início da transação

            // Atualizar a receita
            try (PreparedStatement receitaStmt = connection.prepareStatement(updateReceitaSQL)) {
                receitaStmt.setString(1, receita.getTitulo());
                receitaStmt.setString(2, receita.getDescricao());
                receitaStmt.setString(3, receita.getModoPreparo());
                
                // Verificar ou criar a categoria
                int idCategoria = obterOuCriarCategoria(receita.getCategoria(), connection);
                receitaStmt.setInt(4, idCategoria); // Usar ID da categoria
                receitaStmt.setInt(5, receita.getTempoPreparo());
                receitaStmt.setInt(6, receita.getId()); // Usar o ID da receita existente
                receitaStmt.executeUpdate();

                // Atualizar os ingredientes da receita
                try (PreparedStatement deleteIngredienteStmt = connection.prepareStatement(deleteReceitaIngredienteSQL)) {
                    deleteIngredienteStmt.setInt(1, receita.getId()); // Deletar os ingredientes antigos
                    deleteIngredienteStmt.executeUpdate();
                }

                for (Ingrediente ingrediente : receita.getIngredientes()) {
                    // Verificar ou criar ingrediente
                    int idIngrediente = obterOuCriarIngrediente(ingrediente.getNome(), ingrediente.getQuantidade(), connection);

                    // Inserir na tabela de associação ReceitaP_ingrediente
                    try (PreparedStatement insertIngredienteStmt = connection.prepareStatement(insertReceitaIngredienteSQL)) {
                        insertIngredienteStmt.setInt(1, receita.getId()); // Associar com a receita
                        insertIngredienteStmt.setInt(2, idIngrediente); // Associar com o ingrediente
                        insertIngredienteStmt.executeUpdate();
                    }
                }

                // Atualizar a categoria da receita
                try (PreparedStatement deleteCategoriaStmt = connection.prepareStatement(deleteReceitaCategoriaSQL)) {
                    deleteCategoriaStmt.setInt(1, receita.getId()); // Deletar a categoria antiga
                    deleteCategoriaStmt.executeUpdate();
                }

                // Associar nova categoria à receita
                try (PreparedStatement insertCategoriaStmt = connection.prepareStatement(insertReceitaCategoriaSQL)) {
                    insertCategoriaStmt.setInt(1, receita.getId()); // Passando o ID da receita para 'id_receitaPrincipal'
                    insertCategoriaStmt.setInt(2, idCategoria); // Passando o ID da categoria para 'id_categoria'
                    insertCategoriaStmt.executeUpdate();
                }

                connection.commit(); // Confirmar a transação
            } catch (SQLException e) {
                connection.rollback(); // Reverter em caso de erro
                throw e;
            }
        }
    }

    public void remover(ReceitaPrincipal receita) throws SQLException {
        // Primeiro, buscamos a receita para garantir que ela existe
        buscar(receita);
        
        // Se a receita não for encontrada, retornamos com uma mensagem de erro
        if (receita.getId() == 0) {
            System.out.println("Erro: Receita com o ID " + receita.getId() + " não encontrada.");
            return;
        }

        // Deletar as associações nas tabelas de relacionamento
        String deleteIngredientesSQL = "DELETE FROM ReceitaP_ingrediente WHERE id_receitaPrincipal = ?";
        String deleteCategoriaSQL = "DELETE FROM receitaP_categoria WHERE id_receitaPrincipal = ?";
        String deleteReceitaSQL = "DELETE FROM receita_principal WHERE id = ?";

        try {
            connection.setAutoCommit(false); // Iniciar a transação

            // Deletar os ingredientes associados
            try (PreparedStatement stmtIngredientes = connection.prepareStatement(deleteIngredientesSQL)) {
                stmtIngredientes.setInt(1, receita.getId());
                stmtIngredientes.executeUpdate();
            }

            // Deletar a associação da receita com a categoria
            try (PreparedStatement stmtCategoria = connection.prepareStatement(deleteCategoriaSQL)) {
                stmtCategoria.setInt(1, receita.getId());
                stmtCategoria.executeUpdate();
            }

            // Agora, podemos excluir a receita principal
            try (PreparedStatement stmtReceita = connection.prepareStatement(deleteReceitaSQL)) {
                stmtReceita.setInt(1, receita.getId());
                stmtReceita.executeUpdate();
            }

            connection.commit(); // Confirma a transação
            System.out.println("Receita com ID " + receita.getId() + " foi removida com sucesso.");

        } catch (SQLException e) {
            try {
                connection.rollback(); // Reverter em caso de erro
                System.out.println("Erro ao remover a receita. Transação revertida.");
            } catch (SQLException rollbackEx) {
                System.out.println("Erro ao tentar reverter a transação: " + rollbackEx.getMessage());
            }
            System.out.println("Erro ao remover a receita: " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true); // Retornar ao modo de commit automático
            } catch (SQLException ex) {
                System.out.println("Erro ao restaurar o auto-commit: " + ex.getMessage());
            }
        }
    }

<<<<<<< HEAD
   
=======
    
>>>>>>> bbd7f8fd95bbf3765526c52bf3d6ab634227426c
    @Override
    public ArrayList<ReceitaPrincipal> listarTodos() throws SQLException {
        // Verifique se a conexão está aberta e válida
        if (connection == null || connection.isClosed()) {
            System.out.println("Erro: Conexão com o banco de dados está fechada.");
            return new ArrayList<>(); // Retorna uma lista vazia se a conexão estiver fechada
        }

        // Ajuste a consulta para usar a tabela correta (presumindo que 'receita_principal' seja a tabela correta)
        String sql = """
            SELECT r.id, r.titulo, r.descricao, r.modo_preparo, r.tempo_preparo, r.categoriaId
            FROM receita_principal r
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
                int tempoPreparo = rs.getInt("tempo_preparo");

                // Obter o ID da categoria associada à receita
                int categoriaId = rs.getInt("categoriaId");
                Categoria categoria = new Categoria(); // Criar a categoria
                categoria.setId(categoriaId); // Definir o ID da categoria

                // Criar a ReceitaPrincipal com a categoria associada
                ReceitaPrincipal receita = new ReceitaPrincipal(titulo, descricao, modoPreparo, new ArrayList<>(), categoria, tempoPreparo);
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
