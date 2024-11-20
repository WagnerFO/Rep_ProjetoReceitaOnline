package receitasOnline.Entidades;

import java.util.ArrayList;
import java.util.List;

public abstract class  Receita {
    private Integer id;
    private String titulo;
    private String descricao;
    private String modoPreparo;
    private List<String> ingredientes;
    private Categoria categoria;

    public Receita(){
        //Construtor padrão
    }

    // Construtores existentes
    public Receita(String titulo, String descricao, String modoPreparo, List<String> ingredientes, Categoria categoria) {
        this.titulo = titulo; // ou this.nome = nome se você quiser usar 'nome'
        this.descricao = descricao;
        this.modoPreparo = "";  // Defina um valor padrão se necessário
        this.ingredientes = new ArrayList<>(); // Defina uma lista vazia por padrão
        this.categoria=categoria;
    }

    // Construtor completo, usado quando a receita já tem um ID
    public Receita(Integer id, String titulo, String descricao, String modoPreparo, List<String> ingredientes, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.modoPreparo = modoPreparo;
        this.ingredientes = ingredientes;
        this.categoria=categoria;
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

    public Categoria getCategoria(){
        return categoria;
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

    public void setCategoria(Categoria categoria){
        this.categoria=categoria;
    }
    @Override
    public String toString() {
        return "Receita: " + titulo + ", Descrição: " + descricao + ", Modo de Preparo: " + modoPreparo;
    }
}
