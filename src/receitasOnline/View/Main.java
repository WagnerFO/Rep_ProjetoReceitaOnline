package receitasOnline.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import receitasOnline.Entidades.Avaliacao;
import receitasOnline.Entidades.Categoria;
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
    public static void main(String[] args) {
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
    private static void menuReceitas() {
        System.out.println("Menu de Receitas:");
        System.out.println("1. Adicionar");
        System.out.println("2. Buscar");
        System.out.println("3. Atualizar");
        System.out.println("4. Remover");
        System.out.println("5. Listar todas");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                System.out.println("Digite o ID:");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Digite o Nome:");
                String nome = scanner.nextLine();
                System.out.println("Digite a Descrição:");
                String descricao = scanner.nextLine();
                Receita novaReceita = new Receita(id, nome, descricao);
                receitaServico.adicionarReceita(novaReceita);
                listaReceitas.adicionar(novaReceita); // Armazenar na lista encadeada
                break;
            case 2:
                System.out.println("Digite o ID:");
                id = scanner.nextInt();
                Receita receita = receitaServico.buscarReceita(id);
                if (receita != null) {
                    System.out.println("Receita: " + receita.getNome() + ", Descrição: " + receita.getDescricao());
                } else {
                    System.out.println("Receita não encontrada.");
                }
                break;
            case 3:
                System.out.println("Digite o ID:");
                id = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Digite o Novo Nome:");
                nome = scanner.nextLine();
                System.out.println("Digite a Nova Descrição:");
                descricao = scanner.nextLine();
                receitaServico.atualizarReceita(new Receita(id, nome, descricao));
                break;
            case 4:
                System.out.println("Digite o ID:");
                id = scanner.nextInt();
                receitaServico.removerReceita(id);
                break;
            case 5:
                List<Receita> receitas = receitaServico.listarReceitas();
                for (Receita r : receitas) {
                    System.out.println("ID: " + r.getId() + ", Nome: " + r.getNome() + ", Descrição: " + r.getDescricao());
                }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // Menu de interação com ingredientes
    private static void menuIngredientes() {
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
                System.out.println("Digite o ID:");
                int id = scanner.nextInt();
                System.out.println("Digite o Nome:");
                String nome = scanner.nextLine();
                ingredienteServico.adicionarIngrediente(new Ingrediente(id, nome));
                break;
            case 2:
                System.out.println("Digite o ID:");
                id = scanner.nextInt();
                Ingrediente ingrediente = ingredienteServico.buscarIngrediente(id);
                if (ingrediente != null) {
                    System.out.println("Ingrediente: " + ingrediente.getNome());
                } else {
                    System.out.println("Ingrediente não encontrado.");
                }
                break;
            case 3:
                System.out.println("Digite o ID:");
                id = scanner.nextInt();
                System.out.println("Digite o Novo Nome:");
                nome = scanner.nextLine();
                ingredienteServico.atualizarIngrediente(new Ingrediente(id, nome));
                break;
            case 4:
                System.out.println("Digite o ID:");
                id = scanner.nextInt();
                ingredienteServico.removerIngrediente(id);
                break;
            case 5:
                List<Ingrediente> ingredientes = ingredienteServico.listarIngredientes();
                for (Ingrediente i : ingredientes) {
                    System.out.println("ID: " + i.getId() + ", Nome: " + i.getNome());
                }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // Menu de interação com categorias
    private static void menuCategorias() {
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
                id = scanner.nextInt();
                System.out.println("Digite o Novo Nome:");
                nome = scanner.nextLine();
                categoriaServico.atualizarCategoria(new Categoria(id, nome));
                break;
            case 4:
                System.out.println("Digite o ID:");
                id = scanner.nextInt();
                categoriaServico.removerCategoria(id);
                break;
            case 5:
                List<Categoria> categorias = categoriaServico.listarCategorias();
                for (Categoria c : categorias) {
                    System.out.println("ID: " + c.getId() + ", Nome: " + c.getNome());
                }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // Método para exibir todas as receitas armazenadas na lista encadeada
    private static void exibirReceitas() {
        System.out.println("Receitas Armazenadas:");
        listaReceitas.listar(); // Chama o método listar da lista encadeada
    }
}
