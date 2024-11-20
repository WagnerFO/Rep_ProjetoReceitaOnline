package receitasOnline.Entidades;

import java.util.List;

// A classe ReceitaPrincipal herda da classe Receita
public class ReceitaPrincipal extends Receita {
    // Atributo específico da classe ReceitaPrincipal
    private int tempoPreparo; // Tempo de preparo da receita em minutos

    // Construtor sem ID, para receitas novas (ID será gerado pelo banco)
    public ReceitaPrincipal(String titulo, String descricao, String modoPreparo, List<String> ingredientes, Categoria categoria, int tempoPreparo) {
        super(tempoPreparo, titulo, descricao, modoPreparo, ingredientes, categoria); // Chama o construtor da classe Receita
        this.setTempoPreparo(tempoPreparo);
    }

    // Construtor completo, útil para casos onde ID já existe (e.g., ao carregar do banco)
    public ReceitaPrincipal(Integer id, String titulo, String descricao, String modoPreparo, List<String> ingredientes, Categoria categoria, int tempoPreparo) {
        super(id, titulo, descricao, modoPreparo, ingredientes, categoria); // Chama o construtor da classe Receita
        this.setTempoPreparo(tempoPreparo);
    }

    // Método getter para acessar o atributo tempoPreparo
    public int getTempoPreparo() {
        return tempoPreparo;
    }

    // Método setter para modificar o atributo tempoPreparo
    public void setTempoPreparo(int tempoPreparo) {
        if (tempoPreparo < 0) {
            throw new IllegalArgumentException("O tempo de preparo não pode ser negativo.");
        }
        this.tempoPreparo = tempoPreparo;
    }

    @Override
    public String toString() {
        return super.toString() + ", Tempo de Preparo: " + tempoPreparo + " minutos";
    }
}
