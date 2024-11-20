package receitasOnline.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import receitasOnline.Entidades.Avaliacao;
import receitasOnline.Entidades.Categoria;
import receitasOnline.Entidades.Ingrediente;
import receitasOnline.Entidades.Receita;
import receitasOnline.Entidades.ReceitaPrincipal;
import receitasOnline.Entidades.ReceitaSobremesa;
import receitasOnline.Entidades.Usuario;
import receitasOnline.IRepositorio.*;
import receitasOnline.Repositorio.*;

// Classe principal que contém o método main e os menus de interação com o usuário
public class Main {    
    // Scanner para entrada do usuário
    private static Scanner scanner = new Scanner(System.in);
    
    private static IRepositorioAvaliacao repositorioAvaliacaoSql = new RepositorioAvaliacaoSQL();
    private static IRepositorioCategoria repositorioCategoriaSql = new RepositorioCategoriaSQL();
    private static IRepositorioIngrediente repositorioIngredienteSql = new RepositorioIngredienteSQL();
    private static IRepositorioReceitaPrincipal repositorioReceitaPrincipalSql = new RepositorioReceitaPrincipalSQL();
    private static IRepositorioReceitaSobremesa repositorioReceitaSobremesaSql = new RepositorioReceitaSobremesaSQL();
    private static IRepositorioUsuario repositorioUsuarioSql = new RepositorioUsuarioSQL();
    
    // Lista encadeada para armazenar receitas
    //private static ListaEncadeada<Receita> listaReceitas = new ListaEncadeada<>();

    // Método principal que inicia o programa e exibe o menu principal
    public static void main(String[] args) throws SQLException, InterruptedException {
        while (true) {
            // Exibe o menu principal
            System.out.println("Menu:");
            System.out.println("1. Usuários");
            System.out.println("2. Avaliações");
            System.out.println("3. Receitas");
            System.out.println("4. Ingredientes");
            System.out.println("5. Categorias");
            System.out.println("6. Sair");
            // Lê a opção escolhida pelo usuário
            int opcao = scanner.nextInt();
            scanner.nextLine();

            // Executa a ação correspondente à opção escolhida
            switch (opcao) {
                case 1:
                    menuUsuarios(); // Menu de interação com usuários
                    break;
                case 2:
                    menuAvaliacoes(); // Menu de interação com avaliações
                    break;
                case 3:
                    menuReceitas(); // Menu de interação com receitas
                    break;
                case 4:
                    menuIngredientes(); // Menu de interação com ingredientes
                    break;
                case 5:
                    menuCategorias(); // Menu de interação com categorias
                    break;
                case 6:
                    System.exit(0); // Encerra o programa
                    break;
                default:
                    System.out.println("Opção inválida."); // Mensagem de opção inválida
            }
        }
    }

    // Menu de interação com usuários
    private static void menuUsuarios() throws SQLException {
        System.out.println("Menu de Usuários:");
        System.out.println("1. Adicionar");
        System.out.println("2. Buscar");
        System.out.println("3. Atualizar");
        System.out.println("4. Remover");
        System.out.println("5. Listar todos");

        // Lê a opção escolhida pelo usuário
        int opcao = scanner.nextInt();
        scanner.nextLine();

        // Executa a ação correspondente à opção escolhida
        switch (opcao) {
            case 1:
            	System.out.println("Digite o Nome:");
            	String nome = scanner.nextLine();
            	System.out.println("Digite o Email:");
            	String email = scanner.nextLine();
            	System.out.println("Digite a Senha:");
            	String senha = scanner.nextLine();
            	System.out.println("Digite a Rua:");
            	String rua = scanner.nextLine();
            	System.out.println("Digite o Número:");
            	int numero = scanner.nextInt();
            	scanner.nextLine(); // Limpar o buffer novamente
            	System.out.println("Digite a Cidade:");
            	String cidade = scanner.nextLine();
            	System.out.println("Digite o Estado:");
            	String estado = scanner.nextLine();

            	Usuario usuario = new Usuario(nome, email, senha, rua, numero, cidade, estado);

            	try {
            	    repositorioUsuarioSql.adicionar(usuario);
            	    System.out.println("Usuário adicionado com sucesso!");
            	} catch (SQLException e) {
            	    System.out.println("Erro ao adicionar o usuário: " + e.getMessage());
            	}
                break;
            case 2:
            	Usuario usuario2 = new Usuario();
                System.out.println("Digite o ID:");
                usuario2.setId(scanner.nextInt());
                repositorioUsuarioSql.buscar(usuario2.getId());
                if (usuario2 != null) {
                    System.out.println("Usuário: " + usuario2.getNome());
                }
                break;
            case 3:
            	// Solicitar o ID do usuário
            	System.out.println("Digite o ID do usuário a ser atualizado:");
            	int id1 = scanner.nextInt();
            	scanner.nextLine();  // Limpar o buffer

            	// Solicitar os novos dados
            	System.out.println("Digite o Nome:");
            	String nome1 = scanner.nextLine();
            	System.out.println("Digite o Email:");
            	String email1 = scanner.nextLine();
            	System.out.println("Digite a Senha:");
            	String senha1 = scanner.nextLine();
            	System.out.println("Digite a Rua:");
            	String rua1 = scanner.nextLine();
            	System.out.println("Digite o Número:");
            	int numero1 = scanner.nextInt();
            	scanner.nextLine();  // Limpar o buffer
            	System.out.println("Digite a Cidade:");
            	String cidade1 = scanner.nextLine();
            	System.out.println("Digite o Estado:");
            	String estado1 = scanner.nextLine();

            	// Criar o objeto Usuario
            	Usuario usuario1 = new Usuario(id1, nome1, email1, senha1, rua1, numero1, cidade1, estado1);

            	// Chamar o método para atualizar
            	try {
            	    repositorioUsuarioSql.atualizar(usuario1);
            	} catch (SQLException e) {
            	    System.out.println("Erro ao atualizar o usuário: " + e.getMessage());
            	}
                break;
            case 4:
                System.out.println("Digite o ID:");
                Usuario usuario4 = new Usuario();
                usuario4.setId(scanner.nextInt());
                repositorioUsuarioSql.remover(usuario4);
                break;
            case 5:
                ArrayList<Usuario> usuarios = new ArrayList<>();
                usuarios = repositorioUsuarioSql.listarTodos();
                for (Usuario u : usuarios) {
                    System.out.println(u.toString());
                }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // Menu de interação com avaliações
    private static void menuAvaliacoes() throws SQLException {
        System.out.println("Menu de Avaliações:");
        System.out.println("1. Adicionar");
        System.out.println("2. Buscar");
        System.out.println("3. Atualizar");
        System.out.println("4. Remover");
        System.out.println("5. Listar todas");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                Avaliacao avaliacao = new Avaliacao();
                System.out.println("Digite a Nota:");
                avaliacao.setNota(scanner.nextInt());
                scanner.nextLine();
                System.out.println("Digite o Comentário:");
                avaliacao.setComentario(scanner.nextLine());
                repositorioAvaliacaoSql.adicionar(avaliacao);
                break;
            case 2:
                Avaliacao avaliacao2 = new Avaliacao();
                System.out.println("Digite o ID:");
                avaliacao2.setId(scanner.nextInt());
                repositorioAvaliacaoSql.buscar(avaliacao2.getId());

                if (avaliacao2 != null) {
                    System.out.println("Nota: " + avaliacao2.getNota() + ", Comentário: " + avaliacao2.getComentario());
                }
                break;
            case 3:
                Avaliacao avaliacao3 = new Avaliacao();
                System.out.println("Digite a Nova Nota:");
                avaliacao3.setNota(scanner.nextInt());
                scanner.nextLine();
                System.out.println("Digite o Novo Comentário:");
                avaliacao3.setComentario(scanner.nextLine());
                repositorioAvaliacaoSql.atualizar(avaliacao3);
                break;
            case 4:
                Avaliacao avaliacao4 = new Avaliacao();            
                System.out.println("Digite o ID:");
                avaliacao4.setId(scanner.nextInt());
                repositorioAvaliacaoSql.remover(avaliacao4);
                break;
            case 5:
                ArrayList<Avaliacao> avaliacoes = new ArrayList<>();
                avaliacoes = repositorioAvaliacaoSql.listarTodos();
                for (Avaliacao a : avaliacoes) {
                    System.out.println(a.toString());
                }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // Menu de interação com receitas
    private static void menuReceitas() throws SQLException, InterruptedException {
    	System.out.println("Digite 1 para Receita Principal e 2 para Receita Sobremesa: ");
    	int r = scanner.nextInt();
    	
    	if(r==1) {
    		System.out.println("Menu de Receita Principal:");
            System.out.println("1. Adicionar");
            System.out.println("2. Buscar");
            System.out.println("3. Atualizar");
            System.out.println("4. Remover");
            System.out.println("5. Listar todas");

            int opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                	System.out.println("Digite o título da receita:");
                    String titulo = scanner.nextLine();
                    System.out.println("Digite a descrição da receita:");
                    String descricao = scanner.nextLine();
                    System.out.println("Digite o modo de preparo:");
                    String modoPreparo = scanner.nextLine();
                    System.out.println("Digite a dificuldade da receita:");
                    String dificuldade = scanner.nextLine();
                    System.out.println("Digite o ID da categoria:");
                    Categoria categoria = new Categoria();
                    categoria.setId(scanner.nextInt());
                    scanner.nextLine(); // Consumir a quebra de linha após o int
                    System.out.println("Digite o tempo de preparo (em minutos):");
                    int tempoPreparo = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha após o int
                    System.out.println("Digite os ingredientes separados por vírgula:");
                    String ingredientesInput = scanner.nextLine();
                    
                    // Converter a string de ingredientes em uma lista
                    List<String> ingredientes = new ArrayList<>();
                    for (String ingrediente : ingredientesInput.split(",")) {
                        ingredientes.add(ingrediente.trim()); // Remover espaços extras antes de adicionar
                    }
                    
                    ReceitaPrincipal receitaP = new ReceitaPrincipal(titulo, descricao, modoPreparo, dificuldade, ingredientes, categoria, tempoPreparo );
                    
                    try {
                    	repositorioReceitaPrincipalSql.adicionar(receitaP);
                    	System.out.println("Receita adicionada com Sucesso!");
                    }catch(SQLException e) {
                    	System.out.println("Erro ao Adicionar Receita: "+e.getMessage());
                    }
                    break;
                case 2:
                	ReceitaPrincipal receitaP2 = new ReceitaPrincipal();
                    System.out.println("Digite o ID:");
                    receitaP2.setId(scanner.nextInt());
                    repositorioReceitaPrincipalSql.buscar(receitaP2.getId());
                    if (receitaP2 != null) {
                        System.out.println("Receita: " + receitaP2.getTitulo() + ", Descrição: " + receitaP2.getDescricao());
                    }
                    break;
                case 3:
                    System.out.println("Digite o ID:");
                    int idRP = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.println("Digite o título da receita:");
                    String titulo3 = scanner.nextLine();
                    System.out.println("Digite a descrição da receita:");
                    String descricao3 = scanner.nextLine();
                    System.out.println("Digite o modo de preparo:");
                    String modoPreparo3 = scanner.nextLine();
                    System.out.println("Digite a dificuldade da receita:");
                    String dificuldade3 = scanner.nextLine();
                    System.out.println("Digite o ID da categoria:");
                    Categoria categoria3 = new Categoria();
                    categoria3.setId(scanner.nextInt());
                    scanner.nextLine(); // Consumir a quebra de linha após o int
                    System.out.println("Digite o tempo de preparo (em minutos):");
                    int tempoPreparo3 = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha após o int
                    System.out.println("Digite os ingredientes separados por vírgula:");
                    String ingredientesInput3 = scanner.nextLine();
                    
                    // Converter a string de ingredientes em uma lista
                    List<String> ingredientes3 = new ArrayList<>();
                    for (String ingrediente : ingredientesInput3.split(",")) {
                        ingredientes3.add(ingrediente.trim()); // Remover espaços extras antes de adicionar
                    }
                    ReceitaPrincipal receitaP3 = new ReceitaPrincipal(idRP, titulo3, descricao3, modoPreparo3, dificuldade3, ingredientes3, categoria3, tempoPreparo3 );
                    
                    try {
                    	repositorioReceitaPrincipalSql.atualizar(receitaP3);
                    }catch(SQLException e) {
                    	System.out.println("Erro ao atualizar Receita: "+e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Digite o ID:");
                    ReceitaPrincipal receitaP4 = new ReceitaPrincipal();
                    receitaP4.setId(scanner.nextInt());
                    repositorioReceitaPrincipalSql.remover(receitaP4);
                    break;
                case 5:
                	ArrayList<ReceitaPrincipal> receitasP = new ArrayList<>();
                	receitasP = repositorioReceitaPrincipalSql.listarTodos();
                    for (Receita rP : receitasP) {
                        System.out.println(rP.toString());
                    }
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
            
    	}else if(r==2) {
    		System.out.println("Menu de Receitas Sobremesa:");
            System.out.println("1. Adicionar");
            System.out.println("2. Buscar");
            System.out.println("3. Atualizar");
            System.out.println("4. Remover");
            System.out.println("5. Listar todas");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                	System.out.println("Digite o título da receita:");
                    String titulo = scanner.nextLine();
                    System.out.println("Digite a descrição da receita:");
                    String descricao = scanner.nextLine();
                    System.out.println("Digite o modo de preparo:");
                    String modoPreparo = scanner.nextLine();
                    System.out.println("Essa Receita possui acucar? ");
                    boolean contemAcucar = scanner.nextBoolean();
                    String tipoAcucar=null;
                    if(contemAcucar == true) {
                    	System.out.println("Digite o tipo: ");
                    	tipoAcucar = scanner.nextLine();
                    }
                    System.out.println("Digite o ID da categoria:");
                    Categoria categoria = new Categoria();
                    categoria.setId(scanner.nextInt());
                    scanner.nextLine(); // Consumir a quebra de linha após o int
                    System.out.println("Digite os ingredientes separados por vírgula:");
                    String ingredientesInput = scanner.nextLine();
                    
                    // Converter a string de ingredientes em uma lista
                    List<String> ingredientes = new ArrayList<>();
                    for (String ingrediente : ingredientesInput.split(",")) {
                        ingredientes.add(ingrediente.trim()); // Remover espaços extras antes de adicionar
                    }
                    
                    ReceitaSobremesa receitaS = new ReceitaSobremesa (titulo, descricao, modoPreparo, ingredientes, categoria, contemAcucar, tipoAcucar);
                    
                    try {
                    	repositorioReceitaSobremesaSql.adicionar(receitaS);
                    	System.out.println("Receita adicionada com Sucesso!");
                    }catch(SQLException e) {
                    	System.out.println("Erro ao Adicionar Receita: "+e.getMessage());
                    }
                    break;
                case 2:
                	ReceitaSobremesa receitaS2 = new ReceitaSobremesa();
                    System.out.println("Digite o ID:");
                    receitaS2.setId(scanner.nextInt());
                    repositorioReceitaSobremesaSql.buscar(receitaS2.getId());
                    if (receitaS2 != null) {
                        System.out.println("Receita: " + receitaS2.getTitulo() + ", Descrição: " + receitaS2.getDescricao());
                    } 
                    break;
                case 3:
                    System.out.println("Digite o ID:");
                    int idRS = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.println("Digite o título da receita:");
                    String titulo3 = scanner.nextLine();
                    System.out.println("Digite a descrição da receita:");
                    String descricao3 = scanner.nextLine();
                    System.out.println("Digite o modo de preparo:");
                    String modoPreparo3 = scanner.nextLine();
                    System.out.println("Essa Receita possui acucar? ");
                    boolean contemAcucar3 = scanner.nextBoolean();
                    String tipoAcucar3=null;
                    if(contemAcucar3 == true) {
                    	System.out.println("Digite o tipo: ");
                    	tipoAcucar3 = scanner.nextLine();
                    }
                    System.out.println("Digite o ID da categoria:");
                    Categoria categoria3 = new Categoria();
                    categoria3.setId(scanner.nextInt());
                    scanner.nextLine(); // Consumir a quebra de linha após o int
                    System.out.println("Digite os ingredientes separados por vírgula:");
                    String ingredientesInput3 = scanner.nextLine();
                    
                    // Converter a string de ingredientes em uma lista
                    List<String> ingredientes3 = new ArrayList<>();
                    for (String ingrediente3 : ingredientesInput3.split(",")) {
                        ingredientes3.add(ingrediente3.trim()); // Remover espaços extras antes de adicionar
                    }
                    
                    ReceitaSobremesa receitaS3 = new ReceitaSobremesa (idRS, titulo3, descricao3, modoPreparo3, ingredientes3, categoria3, contemAcucar3, tipoAcucar3);
                    
                    try {
                    	repositorioReceitaSobremesaSql.atualizar(receitaS3);
                    	System.out.println("Receita Atualizada com Sucesso.");
                    }catch(SQLException e) {
                    	System.out.println("Erro ao atualizar Receita: "+e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Digite o ID:");
                    ReceitaSobremesa receitaS4 = new ReceitaSobremesa();
                    receitaS4.setId(scanner.nextInt());
                    repositorioReceitaSobremesaSql.remover(receitaS4);
                    break;
                case 5:
                	ArrayList<ReceitaSobremesa> receitasS = new ArrayList<>();
                	receitasS = repositorioReceitaSobremesaSql.listarTodos();
                    for (Receita rS : receitasS) {
                        System.out.println(rS.toString());
                    }
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

    	}else {
    		System.out.println("Opção Inválida! ");
    	}
    }

    // Menu de interação com ingredientes
    private static void menuIngredientes() throws SQLException {
        System.out.println("Menu de Ingredientes:");
        System.out.println("1. Adicionar");
        System.out.println("2. Buscar");
        System.out.println("3. Atualizar");
        System.out.println("4. Remover");
        System.out.println("5. Listar todos");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                System.out.println("Digite o Nome: ");
                String nome = scanner.nextLine();
                System.out.println("Digite a quantidade: ");
                double quantidade = scanner.nextDouble();
                
                Ingrediente ingrediente1 = new Ingrediente(nome, quantidade);
                try {
                	repositorioIngredienteSql.adicionar(ingrediente1);
                	System.out.println("Ingrediente adicionado com sucesso!");
                }catch(SQLException e) {
                	System.out.println("Erro ao adicionar o ingrediente: " + e.getMessage());
            	}
                break;
            case 2:
            	Ingrediente ingrediente2 = new Ingrediente();
                System.out.println("Digite o ID:");
                ingrediente2.setId(scanner.nextInt());
                repositorioIngredienteSql.buscar(ingrediente2.getId());
                if (ingrediente2 != null) {
                    System.out.println("Ingrediente: " + ingrediente2.getNome());
                }
                break;
            case 3:
                System.out.println("Digite o ID:");
                int id3 = scanner.nextInt();
                System.out.println(); //Limpar o buffer
                
                System.out.println("Digite o Novo Nome:");
                String nome3 = scanner.nextLine();
                System.out.println("Digite a quantidade: ");
                double quantidade3 = scanner.nextDouble();
                
                // Criar o objeto Ingrediente 
                Ingrediente ingrediente3 = new Ingrediente(id3, nome3, quantidade3);
                
                //Chamar o método para atualizar
                try {
                	repositorioIngredienteSql.atualizar(ingrediente3);
                }catch(SQLException e) {
                	System.out.println("Erro ao atualizar o ingrediente: " + e.getMessage());
                }
                break;
            case 4:
                System.out.println("Digite o ID:");
                Ingrediente ingrediente4 = new Ingrediente();
                ingrediente4.setId(scanner.nextInt());
                repositorioIngredienteSql.remover(ingrediente4);
                break;
            case 5:
            	ArrayList<Ingrediente> ingredientes = new ArrayList<>();
            	ingredientes = repositorioIngredienteSql.listarTodos();
                for (Ingrediente i : ingredientes) {
                    System.out.println(i.toString());
                }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // Menu de interação com categorias
    private static void menuCategorias() throws SQLException {
        System.out.println("Menu de Categorias:");
        System.out.println("1. Adicionar");
        System.out.println("2. Buscar");
        System.out.println("3. Atualizar");
        System.out.println("4. Remover");
        System.out.println("5. Listar todas");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
            	Categoria categoria = new Categoria();
                System.out.println("Digite o nome da Categoria:");
                categoria.setNome(scanner.nextLine());
                repositorioCategoriaSql.adicionar(categoria);
                break;
            case 2:
            	Categoria categoria2 = new Categoria();
                System.out.println("Digite o ID:");
                categoria2.setId(scanner.nextInt());
                repositorioCategoriaSql.buscar(categoria2.getId());
                
                if (categoria2 != null) {
                    System.out.println("Categoria: " + categoria2.getNome());
                }
                break;
            case 3:
                System.out.println("Digite o ID:");
                int id3 = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer
                
                System.out.println("Digite o Novo Nome:");
                String nome3 = scanner.nextLine();
                
                //Criar o objeto Categoria
                Categoria categoria3 = new Categoria(id3, nome3);
                
                
                //Chamar o método para atualizar
                try {
                	repositorioCategoriaSql.atualizar(categoria3);
                }catch(SQLException e) {
                	System.out.println("Erro ao atualizar a categoria: "+e.getMessage());
                }
                break;
            case 4:
                System.out.println("Digite o ID:");
                Categoria categoria4 = new Categoria();
                categoria4.setId(scanner.nextInt());
                repositorioCategoriaSql.remover(categoria4);
                break;
            case 5:
            	ArrayList<Categoria> categorias = new ArrayList<>();
            	categorias = repositorioCategoriaSql.listarTodos();
                for (Categoria c : categorias) {
                    System.out.println(c.toString());
                }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    /*/ Método para exibir todas as receitas armazenadas na lista encadeada
    private static void exibirReceitas() {
        System.out.println("Receitas Armazenadas:");
        listaReceitas.listar(); // Chama o método listar da lista encadeada
    }*/
}
