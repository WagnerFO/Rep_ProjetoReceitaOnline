package receitasOnline.Entidades;

public class Avaliacao {
    // Atributos da entidade AVALIACAO
    private Integer id; // Identificador único da avaliação
    private int nota; // Nota atribuída na avaliação
    private String comentario; // Comentário adicional da avaliação

    public Avaliacao(){
        //Construtor padrão
    }
    // Construtor sem ID, para novas avaliações (ID será gerado pelo banco)
    public Avaliacao(int nota, String comentario) {
        this.nota = nota;
        this.comentario = comentario;
    }

    // Construtor completo, útil para casos onde ID já existe (e.g., ao carregar do banco)
    public Avaliacao(Integer id, int nota, String comentario) {
        this.id = id;
        this.nota = nota;
        this.comentario = comentario;
    }

    // Métodos getter para acessar os atributos
    public Integer getId() {
        return id;
    }

    public int getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

    // Métodos setter para modificar os atributos
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Avaliacao [ID: " + id + ", Nota: " + nota + ", Comentário: " + comentario + "]";
    }
}
