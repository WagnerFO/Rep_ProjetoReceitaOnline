package receitasOnline.Entidades;

import java.util.ArrayList;

public class Categoria {

    private Integer id; 
    private String nome; 
    private ArrayList<Receita> receitas;


    public Categoria(){
        //Construtor padrão
    }
    
    public Categoria( String nome) {
    	this.nome=nome; //Construtor só com nome
    }
    
    public Categoria(Integer id, String nome) {
    	this.id=id;
    	this.nome=nome; //Construtor com ID e Nome para listar receitas por categoria
    }
    // Construtor sem ID (ID será gerado pelo banco)
    public Categoria(String nome, ArrayList<Receita> receitas) {
        this.nome = nome;
        this.receitas=receitas;
    }

    // Construtor completo, útil para casos onde ID já existe (e.g., ao carregar do banco)
    public Categoria(Integer id, String nome, ArrayList<Receita> receitas) {
        this.id = id;
        this.nome = nome;
        this.receitas=receitas;
    }

    // Métodos getter para acessar os atributos
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Receita> getReceitas(){
        return receitas;
    }

    // Métodos setter para modificar os atributos
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setReceitas(ArrayList<Receita> receitas){
        this.receitas=receitas;
    }
    
    public void adicionarReceita(Receita receita) {
        this.receitas.add(receita);
    }

    @Override
    public String toString() {
        return "Categoria [ID: " + id + ", Nome: " + nome + "]";
    }
}
