package receitasOnline.Repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import receitasOnline.Entidades.Categoria;
import receitasOnline.Entidades.Receita;
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

    @Override
    public void atualizar(Categoria categoria) throws SQLException {
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
            // Criação da categoria
            Integer categoriaId = rs.getInt("id");
            String nomeCategoria = rs.getString("nome");

            // Agora, buscaremos as receitas associadas a essa categoria
            String sqlReceitas = "SELECT * FROM receita WHERE categoriaId = ?";
            ArrayList<Receita> receitas = new ArrayList<>();
            
            try (PreparedStatement stmtReceitas = connection.prepareStatement(sqlReceitas)) {
                stmtReceitas.setInt(1, categoriaId);
                ResultSet rsReceitas = stmtReceitas.executeQuery();
                
                while (rsReceitas.next()) {
                    Receita receita = new Receita();
                    receita.setId(rsReceitas.getInt("id"));
                    receita.setTitulo(rsReceitas.getString("titulo"));
                    receita.setDescricao(rsReceitas.getString("descricao"));
                    receitas.add(receita);
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
