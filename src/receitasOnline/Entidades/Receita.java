package receitasOnline.Entidades;

import java.util.List;

public class Receita {
    private Integer id;
    private String titulo;
    private String descricao;
    private String modoPreparo;
    private List<String> ingredientes;

    // Construtor para novas receitas (sem ID)
    public Receita(String titulo, String descricao, String modoPreparo, List<String> ingredientes) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.modoPreparo = modoPreparo;
        this.ingredientes = ingredientes;
    }

    // Construtor completo, usado quando a receita já tem um ID
    public Receita(Integer id, String titulo, String descricao, String modoPreparo, List<String> ingredientes) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.modoPreparo = modoPreparo;
        this.ingredientes = ingredientes;
    }

    // Getters e setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getModoPreparo() {
        return modoPreparo;
    }

    public void setModoPreparo(String modoPreparo) {
        this.modoPreparo = modoPreparo;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    @Override
    public String toString() {
        return "Receita: " + titulo + ", Descrição: " + descricao + ", Modo de Preparo: " + modoPreparo;
    }
}
