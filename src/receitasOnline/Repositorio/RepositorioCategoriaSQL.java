package receitasOnline.Repositorio;

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
                                        ((ReceitaPrincipal) receita).setDificuldade(rsReceitas.getString("dificuldade"));
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
        String sql = "UPDATE categoria SET nome = ?, descricao = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setInt(3, categoria.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao alterar Categoria " + e.getMessage());
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
            stmt.executeUpdate();   
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

                String sqlReceitas = "SELECT * FROM receita WHERE categoriaId = ?";
                ArrayList<Receita> receitas = new ArrayList<>();
                
                try (PreparedStatement stmtReceitas = connection.prepareStatement(sqlReceitas)) {
                    stmtReceitas.setInt(1, categoriaId);
                    
                    try (ResultSet rsReceitas = stmtReceitas.executeQuery()) {
                        while (rsReceitas.next()) {
                            // Verificar o tipo da receita
                            String tipoReceita = rsReceitas.getString("tipo"); // Exemplo de coluna 'tipo'

                            Receita receita;
                            if ("sobremesa".equals(tipoReceita)) {
                                receita = new ReceitaSobremesa();  // Instancia ReceitaSobremesa
                                ((ReceitaSobremesa) receita).setContemAcucar(rsReceitas.getBoolean("contem_acucar"));
                                ((ReceitaSobremesa) receita).setTipoAcucar(rsReceitas.getString("tipo_acucar"));
                            } else if ("principal".equals(tipoReceita)) {
                                receita = new ReceitaPrincipal();  // Instancia ReceitaPrincipal
                            } else {
                                continue;  // Caso o tipo de receita não seja reconhecido, pula a iteração
                            }

                            receita.setId(rsReceitas.getInt("id"));
                            receita.setTitulo(rsReceitas.getString("titulo"));
                            receita.setDescricao(rsReceitas.getString("descricao"));
                            receitas.add(receita);
                        }
                    }
                }

                // Criação da categoria com as receitas associadas
                Categoria categoria = new Categoria(categoriaId, nomeCategoria, receitas);
                categorias.add(categoria);
            }
        }
        
        return categorias;
    }




}
