package receitasOnline.Repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import receitasOnline.Entidades.Avaliacao;
import receitasOnline.Factory.ConnectionSingleton;
import receitasOnline.IRepositorio.IRepositorioAvaliacao;

public class RepositorioAvaliacaoSQL implements IRepositorioAvaliacao {

    private Connection connection;

    public RepositorioAvaliacaoSQL() {
        try {
            this.connection = ConnectionSingleton.getInstance().conexao;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void adicionar(Avaliacao avaliacao) throws SQLException {
        String sql = "INSERT INTO avaliacao (nota, comentario, usuarioId) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, avaliacao.getNota());
            stmt.setString(2, avaliacao.getComentario());
            stmt.setInt(3, avaliacao.getUsuarioId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Avaliacao buscar(int id) {
        String sql = "SELECT * FROM avaliacao WHERE id = ?";
        Avaliacao avaliacao = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                avaliacao = new Avaliacao();
                avaliacao.setId(rs.getInt("id"));
                avaliacao.setNota(rs.getInt("nota"));
                avaliacao.setComentario(rs.getString("comentario"));
                avaliacao.setUsuarioId(rs.getInt("usuarioId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avaliacao;
    }

    @Override
    public void atualizar(Avaliacao avaliacao) throws SQLException {
        String sql = "UPDATE avaliacao SET nota = ?, comentario = ?, usuarioId = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, avaliacao.getNota());
            stmt.setString(2, avaliacao.getComentario());
            stmt.setInt(3, avaliacao.getUsuarioId());
            stmt.setInt(4, avaliacao.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void remover(Avaliacao avaliacao) {
        String sql = "DELETE FROM avaliacao WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, avaliacao.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Avaliacao> listarTodos() throws SQLException {
        String sql = "SELECT * FROM avaliacao";
        ArrayList<Avaliacao> avaliacoes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(rs.getInt("id"));
                avaliacao.setNota(rs.getInt("nota"));
                avaliacao.setComentario(rs.getString("comentario"));
                avaliacao.setUsuarioId(rs.getInt("usuarioId"));
                avaliacoes.add(avaliacao);
            }
        }
        return avaliacoes;
    }
}
