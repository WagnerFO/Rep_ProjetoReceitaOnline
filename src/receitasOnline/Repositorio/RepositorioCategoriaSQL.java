package receitasOnline.Repositorio;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import receitasOnline.Entidades.Categoria;
import receitasOnline.Entidades.Receita;
import receitasOnline.Entidades.ReceitaPrincipal;
import receitasOnline.Entidades.ReceitaSobremesa;
import receitasOnline.Factory.ConnectionSingleton;
import receitasOnline.IRepositorio.IRepositorioCategoria;

public class RepositorioCategoriaSQL implements IRepositorioCategoria {

    private Connection connection;
    
    public RepositorioCategoriaSQL() {
        try {
            this.connection = ConnectionSingleton.getInstance().conexao;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void adicionar(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categoria (nome) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.executeUpdate();
        }
    }
    
    public int obterOuCriarCategoria(String nomeCategoria, Connection connection) throws SQLException {
        String selectCategoriaSQL = "SELECT id FROM categoria WHERE nome = ?";
        String insertCategoriaSQL = "INSERT INTO categoria (nome) VALUES (?)";

        // Verificar se a categoria já existe
        try (PreparedStatement selectStmt = connection.prepareStatement(selectCategoriaSQL)) {
            selectStmt.setString(1, nomeCategoria);
            ResultSet rsCategoria = selectStmt.executeQuery();
            if (rsCategoria.next()) {
                return rsCategoria.getInt("id"); // Se a categoria existe, retorna o ID
            }
        }

        // Se não existir, insere uma nova categoria
        try (PreparedStatement insertStmt = connection.prepareStatement(insertCategoriaSQL, Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setString(1, nomeCategoria);
            insertStmt.executeUpdate();
            ResultSet rsInsert = insertStmt.getGeneratedKeys();
            if (rsInsert.next()) {
                return rsInsert.getInt(1); // Retorna o ID da nova categoria
            } else {
                throw new SQLException("Erro ao obter o ID da categoria inserida.");
            }
        }
    }

    @Override
    public Categoria buscar(int id) {
        String sql = "SELECT * FROM categoria WHERE id = ?";
        Categoria categoria = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setNome(rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Categoria " + e.getMessage());
            e.printStackTrace();
        }
        return categoria;
    }

        public Categoria buscarCategoriaComReceitas(int categoriaId) throws SQLException {
            String sql = "SELECT * FROM categoria WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, categoriaId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Integer idCategoria = rs.getInt("id");
                        String nomeCategoria = rs.getString("nome");

                        // Criando a categoria
                        Categoria categoria = new Categoria(idCategoria, nomeCategoria);

                        // Buscando as receitas dessa categoria
                        String sqlReceitas = "SELECT * FROM receita WHERE categoriaId = ?";
                        try (PreparedStatement stmtReceitas = connection.prepareStatement(sqlReceitas)) {
                            stmtReceitas.setInt(1, idCategoria);
                            try (ResultSet rsReceitas = stmtReceitas.executeQuery()) {
                                while (rsReceitas.next()) {
                                    // Verificando o tipo da receita
                                    String tipoReceita = rsReceitas.getString("tipo");

                                    Receita receita = null;
                                    if ("sobremesa".equals(tipoReceita)) {
                                        receita = new ReceitaSobremesa();
                                        ((ReceitaSobremesa) receita).setContemAcucar(rsReceitas.getBoolean("contem_acucar"));
                                        ((ReceitaSobremesa) receita).setTipoAcucar(rsReceitas.getString("tipo_acucar"));
                                    } else {
                                        receita = new ReceitaPrincipal();
                                        ((ReceitaPrincipal) receita).setTempoPreparo(rsReceitas.getInt("tempo_preparo"));
                                    }

                                    // Configurando os dados da receita
                                    receita.setId(rsReceitas.getInt("id"));
                                    receita.setTitulo(rsReceitas.getString("titulo"));
                                    receita.setDescricao(rsReceitas.getString("descricao"));
                                    receita.setModoPreparo(rsReceitas.getString("modo_preparo"));

                                    // Adicionando a receita à categoria
                                    categoria.adicionarReceita(receita);
                                }
                            }
                        }
                        return categoria;
                    }
                }
            }
            return null;  // Caso a categoria não seja encontrada
        }


        @Override
        public void atualizar(Categoria categoria) throws SQLException {
            Categoria categoriaExistente = buscar(categoria.getId());

            if (categoriaExistente == null) {
                System.out.println("Erro: Categoria com o ID " + categoria.getId() + " não encontrada.");
                return; // Interrompe a execução se o ID não existir
            }

            // SQL com dois parâmetros: nome e id
            String sql = "UPDATE categoria SET nome = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, categoria.getNome());  // Define o nome da categoria
                stmt.setInt(2, categoria.getId());       // Define o id da categoria para filtrar no WHERE

                stmt.executeUpdate(); // Executa a atualização
                System.out.println("Categoria atualizada com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao alterar Categoria: " + e.getMessage());
                throw e;
            }
        }


    @Override
    public void remover(Categoria categoria) {
    	Categoria categoriaExistente = buscar(categoria.getId());

    	if (categoriaExistente == null) {
    	    System.out.println("Erro: Categoria com o ID " + categoria.getId() + " não encontrada.");
    	    return; // Interrompe a execução se o ID não existir
    	}
        String sql = "DELETE FROM categoria WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, categoria.getId());
            int rowsAffected = stmt.executeUpdate();
            
            if(rowsAffected > 0) {
            	System.out.println("Categoria " + categoriaExistente.getNome() + " removido com sucesso.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Categoria> listarTodos() throws SQLException {
        String sql = "SELECT * FROM categoria";
        ArrayList<Categoria> categorias = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Integer categoriaId = rs.getInt("id");
                String nomeCategoria = rs.getString("nome");
                
                // Criar a categoria sem associar receitas
                Categoria categoria = new Categoria(categoriaId, nomeCategoria);
                categorias.add(categoria);
            }
        }

        return categorias;
    }


}
