package receitasOnline.Entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReceitaPrincipal extends Receita {
    private int tempoPreparo;

    public ReceitaPrincipal() {
        // Construtor padrão
    }
    
    //Construtor completo sem Id
    public ReceitaPrincipal(String titulo, String descricao, String modoPreparo, List<Ingrediente> ingredientes, Categoria categoria, int tempoPreparo) {
        super(titulo, descricao, modoPreparo, ingredientes != null ? ingredientes : new ArrayList<>(), categoria);
        this.tempoPreparo = tempoPreparo;
    }

    
    //Construtor completo com Id
    public ReceitaPrincipal(Integer id, String titulo, String descricao, String modoPreparo, List<Ingrediente> ingredientes, Categoria categoria, int tempoPreparo) {
        super(id, titulo, descricao, modoPreparo, ingredientes != null ? ingredientes : new ArrayList<>(), categoria);
        this.tempoPreparo = tempoPreparo;
    }

    
    //Metodos Gets e Sets
    public int getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(int tempoPreparo) {
        if (tempoPreparo < 0) {
            throw new IllegalArgumentException("O tempo de preparo não pode ser negativo.");
        }
        this.tempoPreparo = tempoPreparo;
    }
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Receita Principal [ID: ").append(getId())
          .append(", Título: ").append(getTitulo())
          .append(", Descrição: ").append(getDescricao())
          .append(", Modo de Preparo: ").append(getModoPreparo())
          .append(", Tempo de Preparo: ")
          .append(tempoPreparo > 0 ? tempoPreparo + " minutos" : "Tempo de preparo não especificado")
          .append(", Categoria: ")
          .append(getCategoria() != null ? getCategoria().getNome() : "Categoria não especificada")
          .append(", Ingredientes: ");

        if (getIngredientes() != null && !getIngredientes().isEmpty()) {
            sb.append(getIngredientes().stream()
                    .map(ingrediente -> ingrediente.getNome() + ": " + ingrediente.getQuantidade())
                    .collect(Collectors.joining(", ")));
        } else {
            sb.append("Nenhum ingrediente especificado");
        }

        sb.append("]");
        return sb.toString();
    }
}
