package receitasOnline.Entidades;

import java.math.BigDecimal;

public class Ingrediente {
    // Atributos da entidade INGREDIENTE
    private Integer id; // Identificador único do ingrediente
    private String nome; // Nome do ingrediente
    private BigDecimal quantidade; // Quantidade padrão do ingrediente

    // Construtor sem ID, para novos ingredientes (ID será gerado pelo banco)
    public Ingrediente(String nome, BigDecimal quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }

    // Construtor completo, útil para casos onde ID já existe (e.g., ao carregar do banco)
    public Ingrediente(Integer id, String nome, BigDecimal quantidade) {
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

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    // Métodos setter para modificar os atributos
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Ingrediente [ID: " + id + ", Nome: " + nome + ", Quantidade: " + quantidade + "]";
    }
}
