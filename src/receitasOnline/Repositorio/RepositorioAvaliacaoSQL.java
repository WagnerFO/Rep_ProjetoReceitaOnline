package receitasOnline.Repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public void adicionar(Avaliacao avaliacao) throws SQLException {
        String sql = "INSERT INTO avaliacao (nota, comentario, usuario_id) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, avaliacao.getNota());
            stmt.setString(2, avaliacao.getComentario());
            stmt.setInt(3, avaliacao.getUsuarioId());

            // Executa o comando de inserção
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    avaliacao.setId(rs.getInt(1)); // Atribui o ID gerado ao objeto Avaliacao
                    System.out.println("Avaliação adicionada com sucesso!");
                }
            } else {
                System.out.println("Falha ao adicionar a avaliação.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar avaliação: " + e.getMessage());
            throw e;
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
                avaliacao.setUsuarioId(rs.getInt("usuario_id"));
                }
            } catch (SQLException e) {
            	System.out.println("Erro ao Buscar Avaliação: " + e.getMessage());
            	e.printStackTrace();
            	}
        return avaliacao;
        }

    @Override
    public void atualizar(Avaliacao avaliacao) throws SQLException {
        // Verifica se o ID é válido (não é null)
        if (avaliacao.getId() == null) {
            System.out.println("Erro: ID da avaliação não pode ser nulo.");
            return;
        }

        // Verifica se a avaliação existe antes de tentar atualizar
        Avaliacao avaliacaoExistente = buscar(avaliacao.getId());

        if (avaliacaoExistente == null) {
            System.out.println("Erro: Avaliação com o ID " + avaliacao.getId() + " não encontrada.");
            return; // Interrompe a execução se o ID não existir
        }

        // SQL para atualizar os dados
        String sql = "UPDATE avaliacao SET nota = ?, comentario = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Definir os parâmetros da atualização
            stmt.setInt(1, avaliacao.getNota());        // Atualiza a nota
            stmt.setString(2, avaliacao.getComentario());  // Atualiza o comentário
            stmt.setInt(3, avaliacao.getId());            // Usa o ID da avaliação para localizar

            int rowsAffected = stmt.executeUpdate();  // Executa a atualização no banco

            if (rowsAffected > 0) {
                System.out.println("Avaliação atualizada com sucesso.");
            } else {
                System.out.println("Erro ao atualizar avaliação. Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar avaliação: " + e.getMessage());
            throw e;  // Re-lança a exceção para ser tratada em outro lugar, se necessário
        }
    }



    @Override
    public void remover(Avaliacao avaliacao) {
    	
    	//Verificar se a Avaliacao existe antes de tentar remover
    	Avaliacao avaliacaoExistente = buscar(avaliacao.getId());
    	
    	if(avaliacaoExistente == null) {
    		System.out.println("Erro: Avaliação com o ID " + avaliacao.getId() + " não encontrado.");
    		return;
    	}
    	
        String sql = "DELETE FROM avaliacao WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, avaliacao.getId());
            int rowsAffected = stmt.executeUpdate(); //executa a remoção
            
            if(rowsAffected > 0) {
            	System.out.println("Avaliação removida com sucesso.");
            }
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
                avaliacao.setUsuarioId(rs.getInt("usuario_id"));
                avaliacoes.add(avaliacao);
            }
        }
        return avaliacoes;
    }
}
