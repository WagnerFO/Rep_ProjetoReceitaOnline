package receitasOnline.Entidades;

public class Avaliacao {
    private Integer id; // Identificador da avaliação
    private int nota;
    private String comentario;
    private Integer usuarioId; // Relacionamento com o Usuario (responsável pela avaliação)

    // Construtor padrão
    public Avaliacao() {}

    // Construtor sem ID, para novas avaliações (ID será gerado pelo banco)
    public Avaliacao(int nota, String comentario, Integer usuarioId) {
        this.nota = nota;
        this.comentario = comentario;
        this.usuarioId=usuarioId;
    }
    // Construtor completo, útil para casos onde ID já existe (e.g., ao carregar do banco)
    public Avaliacao(Integer id, int nota, String comentario, Integer usuarioId) {
        this.id = id;
        this.nota = nota;
        this.comentario = comentario;
        this.usuarioId = usuarioId;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public String toString() {
        return "Avaliacao [ID: " + id + ", Nota: " + nota + ", Comentário: " + comentario + "]";
    }
}
