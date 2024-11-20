package receitasOnline.Entidades;

import java.util.List;

// A classe ReceitaPrincipal herda da classe Receita
public class ReceitaPrincipal extends Receita {
    // Atributo específico da classe ReceitaPrincipal
    private int tempoPreparo; // Tempo de preparo da receita em minutos
    private String dificuldade;

    
    public ReceitaPrincipal() {
    	//Construtor padrão
    }
    // Construtor sem ID, para receitas novas (ID será gerado pelo banco)
    public ReceitaPrincipal(String titulo, String descricao, String modoPreparo, String dificuldade, List<String> ingredientes, Categoria categoria, int tempoPreparo) {
        super(tempoPreparo, titulo, descricao, modoPreparo, ingredientes, categoria); // Chama o construtor da classe Receita
        this.setTempoPreparo(tempoPreparo);
        this.dificuldade=dificuldade;
    }

    // Construtor completo, útil para casos onde ID já existe (e.g., ao carregar do banco)
    public ReceitaPrincipal(Integer id, String titulo, String descricao, String modoPreparo, String dificuldade, List<String> ingredientes, Categoria categoria, int tempoPreparo) {
        super(id, titulo, descricao, modoPreparo, ingredientes, categoria); // Chama o construtor da classe Receita
        this.setTempoPreparo(tempoPreparo);
        this.dificuldade=dificuldade;
    }

    // Método getter para acessar o atributo tempoPreparo
    public int getTempoPreparo() {
        return tempoPreparo;
    }
    
    public String getDificuldade() {
    	return dificuldade;
    }

    // Método setter para modificar o atributo tempoPreparo
    public void setTempoPreparo(int tempoPreparo) {
        if (tempoPreparo < 0) {
            throw new IllegalArgumentException("O tempo de preparo não pode ser negativo.");
        }
        this.tempoPreparo = tempoPreparo;
    }
    
    public void setDificuldade(String dificuldade) {
    	this.dificuldade=dificuldade;
    }
    

    @Override
    public String toString() {
        return super.toString() + ", Tempo de Preparo: " + tempoPreparo + " minutos e sua dificuldade é: "+dificuldade;
    }
}
