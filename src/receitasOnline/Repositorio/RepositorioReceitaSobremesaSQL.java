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
        
        String insertReceitaSQL = "INSERT INTO receita_sobremesa (titulo, descricao, modo_preparo, categoriaId, contem_acucar, tipo_acucar) VALUES (?, ?, ?, ?, ?, ?)";
        String insertReceitaIngredienteSQL = "INSERT INTO ReceitaS_ingrediente (id_receitaSobremesa, id_ingrediente) VALUES (?, ?)"; // Alterado o nome da tabela
        String insertReceitaCategoriaSQL = "INSERT INTO receitaS_categoria (id_receitaSobremesa, id_categoria) VALUES (?, ?)"; // Tabela de associação
        
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
                receitaStmt.setBoolean(5, receita.isContemAcucar());
                receitaStmt.setString(6,  receita.getTipoAcucar());
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
                    receitaCategoriaStmt.setInt(1, idReceita); // Passando o ID da receita para 'id_receitaSobremesa'
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
    public void buscar(ReceitaSobremesa receita) {
        String sql = "SELECT * FROM receita_sobremesa WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, receita.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                receita.setId(rs.getInt("id"));
                receita.setTitulo(rs.getString("titulo"));
                receita.setDescricao(rs.getString("descricao"));
                receita.setModoPreparo(rs.getString("modo_preparo"));
                receita.setContemAcucar(rs.getBoolean("contem_acucar"));
                receita.setTipoAcucar(rs.getString("tipo_acucar"));
                
                int categoriaId = rs.getInt("categoriaId");
                Categoria categoria = new Categoria();
                categoria.setId(categoriaId);
                receita.setCategoria(categoria);

                // Corrigido: Usar stmtCategoria para a execução da consulta de categoria
                String sqlCategoria = "SELECT nome FROM categoria WHERE id = ?";
                try (PreparedStatement stmtCategoria = connection.prepareStatement(sqlCategoria)) {
                    stmtCategoria.setInt(1, categoriaId);
                    try (ResultSet rsCategoria = stmtCategoria.executeQuery()) {
                        if (rsCategoria.next()) {
                            categoria.setNome(rsCategoria.getString("nome"));
                        }
                    }
                }

                // Corrigido: A consulta de ingredientes foi verificada para o nome correto de tabela
                String sqlIngredientes = "SELECT i.nome, ri.id_ingrediente, i.quantidade FROM ingrediente i " +
                                          "JOIN ReceitaS_ingrediente ri ON ri.id_ingrediente = i.id " +
                                          "WHERE ri.id_receitaSobremesa = ?";
                try (PreparedStatement stmtIngredientes = connection.prepareStatement(sqlIngredientes)) {
                    stmtIngredientes.setInt(1, receita.getId());
                    try (ResultSet rsIngredientes = stmtIngredientes.executeQuery()) {
                        List<Ingrediente> ingredientes = new ArrayList<>();
                        while (rsIngredientes.next()) {
                            String nomeIngrediente = rsIngredientes.getString("nome");
                            double quantidadeIngrediente = rsIngredientes.getDouble("quantidade");
                            Ingrediente ingrediente = new Ingrediente(nomeIngrediente, quantidadeIngrediente);
                            ingredientes.add(ingrediente);
                        }
                        receita.setIngredientes(ingredientes); // Preencher a lista de ingredientes
                    }
                }
            } else {
                receita.setId(0);  // Se não encontrar a receita, o ID será 0
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Receita: " + e.getMessage());
            e.printStackTrace();
        }
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
    public void atualizar(ReceitaSobremesa receita) throws SQLException, InterruptedException {
  
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
        String updateReceitaSQL = "UPDATE receita_sobremesa SET titulo = ?, descricao = ?, modo_preparo = ?, categoriaId = ?, contem_acucar = ?, tipo_acucar = ? WHERE id = ?";
        String deleteReceitaIngredienteSQL = "DELETE FROM ReceitaS_ingrediente WHERE id_receitaSobremesa = ?"; 
        String insertReceitaIngredienteSQL = "INSERT INTO ReceitaS_ingrediente (id_receitaSobremesa, id_ingrediente) VALUES (?, ?)";
        String deleteReceitaCategoriaSQL = "DELETE FROM receitaS_categoria WHERE id_receitaSobremesa = ?";
        String insertReceitaCategoriaSQL = "INSERT INTO receitaS_categoria (id_receitaSobremesa, id_categoria) VALUES (?, ?)";

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
                receitaStmt.setBoolean(5, receita.isContemAcucar());
                receitaStmt.setString(6,  receita.getTipoAcucar());
                receitaStmt.setInt(7, receita.getId()); // Usar o ID da receita existente
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
                    insertCategoriaStmt.setInt(1, receita.getId()); // Passando o ID da receita para 'id_receitaSobremesa'
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
    	 

    @Override
    public void remover(ReceitaSobremesa receita) {
    	
    	// Primeiro, buscamos a receita para garantir que ela existe
        buscar(receita);
        
        // Se a receita não for encontrada, retornamos com uma mensagem de erro
        if (receita.getId() == 0) {
            System.out.println("Erro: Receita com o ID " + receita.getId() + " não encontrada.");
            return;
        }

        // Deletar as associações nas tabelas de relacionamento
        String deleteIngredientesSQL = "DELETE FROM ReceitaS_ingrediente WHERE id_receitaPrincipal = ?";
        String deleteCategoriaSQL = "DELETE FROM receitaS_categoria WHERE id_receitaPrincipal = ?";
        String deleteReceitaSQL = "DELETE FROM receita_sobremesa WHERE id = ?";

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
    
    
    @Override
    public ArrayList<ReceitaSobremesa> listarTodos() throws SQLException {

    	// Verifique se a conexão está aberta e válida
        if (connection == null || connection.isClosed()) {
            System.out.println("Erro: Conexão com o banco de dados está fechada.");
            return new ArrayList<>(); // Retorna uma lista vazia se a conexão estiver fechada
        }

        // Ajuste a consulta para usar a tabela correta (presumindo que 'receita_principal' seja a tabela correta)
        String sql = """
            SELECT r.id, r.titulo, r.descricao, r.modo_preparo, r.contem_acucar, r.tipo_acucar, r.categoriaId
            FROM receita_sobremesa r
        """;

        ArrayList<ReceitaSobremesa> receitas = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Obter os dados da receita principal
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");
                String modoPreparo = rs.getString("modo_preparo");
                boolean contemAcucar = rs.getBoolean("contem_acucar");
                String tipoAcucar = rs.getString("tipo_acucar");

                // Obter o ID da categoria associada à receita
                int categoriaId = rs.getInt("categoriaId");
                Categoria categoria = new Categoria(); // Criar a categoria
                categoria.setId(categoriaId); // Definir o ID da categoria

                // Criar a ReceitaPrincipal com a categoria associada
                ReceitaSobremesa receita = new ReceitaSobremesa (titulo, descricao, modoPreparo, new ArrayList<>(), categoria, contemAcucar, tipoAcucar);
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
