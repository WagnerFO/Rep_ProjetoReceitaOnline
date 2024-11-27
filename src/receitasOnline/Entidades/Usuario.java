package receitasOnline.Entidades;

public class Usuario {
    // Atributos da entidade USUARIO
    private Integer id; // Identificador único do usuário, gerado pelo banco
    private String nome;
    private String email;
    private String senha;
    private String rua;
    private int numero;
    private String cidade;
    private String estado;

    public Usuario(){
        //Construtor padrão;
    }
    
    // Construtor sem ID (id será gerado pelo banco)
    public Usuario(String nome, String email, String senha, String rua, int numero, String cidade, String estado) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
    }
    
    // Construtor completo, útil para casos onde ID já existe (e.g., ao carregar do banco)
    public Usuario(Integer id, String nome, String email, String senha, String rua, int numero, String cidade, String estado) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
    }
    
    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public String getRua() {
        return rua;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public String getCidade() {
        return cidade;
    }
    
    public String getEstado() {
        return estado;
    }
    
    // Métodos setter para modificar os atributos
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public void setRua(String rua) {
        this.rua = rua;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return "Usuario {" +
               "ID = " + id +
               ", Nome = '" + nome + '\'' +
               ", Email = '" + email + '\'' +
               ", Senha = '" + senha + '\'' +
               ", Endereço = '" + rua + ", " + numero + ", " + cidade + " - " + estado + '\'' +
               '}';
    }

}
