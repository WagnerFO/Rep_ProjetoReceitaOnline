package receitasOnline.Entidades;

public class Categoria {
    // Atributos da entidade CATEGORIA
    private Integer id; // Identificador único da categoria
    private String nome; // Nome da categoria

    public Categoria(){
        //Construtor padrão
    }
    // Construtor sem ID (ID será gerado pelo banco)
    public Categoria(String nome) {
        this.nome = nome;
    }

    // Construtor completo, útil para casos onde ID já existe (e.g., ao carregar do banco)
    public Categoria(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Métodos getter para acessar os atributos
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    // Métodos setter para modificar os atributos
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Categoria [ID: " + id + ", Nome: " + nome + "]";
    }
}
