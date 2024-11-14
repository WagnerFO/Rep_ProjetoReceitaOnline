package receitasOnline.Entidades;

import java.util.List;

// A classe ReceitaSobremesa herda da classe Receita
public class ReceitaSobremesa extends Receita {
    // Atributos específicos da classe ReceitaSobremesa
    private boolean contemAcucar;
    private String tipoAcucar; // Tipo de açúcar usado na receita, se aplicável

    // Construtor sem ID, para receitas novas (ID será gerado pelo banco)
    public ReceitaSobremesa(String titulo, String descricao, String modoPreparo, List<String> ingredientes, boolean contemAcucar, String tipoAcucar) {
        super(titulo, descricao, modoPreparo, ingredientes);
        this.contemAcucar = contemAcucar;
        this.tipoAcucar = contemAcucar ? tipoAcucar : null;
    }

    // Construtor completo, útil para casos onde ID já existe (e.g., ao carregar do banco)
    public ReceitaSobremesa(Integer id, String titulo, String descricao, String modoPreparo, List<String> ingredientes, boolean contemAcucar, String tipoAcucar) {
        super(id, titulo, descricao, modoPreparo, ingredientes);
        this.contemAcucar = contemAcucar;
        this.tipoAcucar = contemAcucar ? tipoAcucar : null;
    }

    // Método getter para acessar o atributo contemAcucar
    public boolean isContemAcucar() {
        return contemAcucar;
    }

    // Método setter para modificar o atributo contemAcucar
    public void setContemAcucar(boolean contemAcucar) {
        this.contemAcucar = contemAcucar;
        // Define o tipo de açúcar como nulo se a sobremesa não contiver açúcar
        if (!contemAcucar) {
            this.tipoAcucar = null;
        }
    }

    // Método getter para acessar o tipo de açúcar
    public String getTipoAcucar() {
        return tipoAcucar;
    }

    // Método setter para modificar o tipo de açúcar
    public void setTipoAcucar(String tipoAcucar) {
        if (contemAcucar) {
            this.tipoAcucar = tipoAcucar;
        } else {
            throw new IllegalStateException("Não é possível definir o tipo de açúcar para uma receita sem açúcar.");
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Contém Açúcar: " + contemAcucar + (contemAcucar ? ", Tipo de Açúcar: " + tipoAcucar : "");
    }
}
