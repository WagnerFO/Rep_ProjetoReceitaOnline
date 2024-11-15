package receitasOnline.Entidades;

public class Ingrediente {
    // Atributos da entidade INGREDIENTE
    private Integer id; // Identificador único do ingrediente
    private String nome; // Nome do ingrediente
    private double quantidade; // Quantidade padrão do ingrediente

    public Ingrediente(){
        //construtor padrão
    }

    // Construtor completo, útil para casos onde ID já existe (e.g., ao carregar do banco)
    public Ingrediente(Integer id, String nome, Double quantidade) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
    }

    // Métodos getter para acessar os atributos
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getQuantidade() {
        return quantidade;
    }

    // Métodos setter para modificar os atributos
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Ingrediente [ID: " + id + ", Nome: " + nome + ", Quantidade: " + quantidade + "]";
    }
}
