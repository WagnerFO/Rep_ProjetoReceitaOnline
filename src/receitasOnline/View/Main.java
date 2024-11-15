package receitasOnline.View;

import java.util.Scanner;
import java.util.List;
import receitasOnline.Servicos.*;
import receitasOnline.Entidades.*;
import receitasOnline.Repositorio.*;
import receitasOnline.Estruturas.ListaEncadeada; // Importando a lista encadeada

// Classe principal que contém o método main e os menus de interação com o usuário
public class Main {    
    // Scanner para entrada do usuário
    private static Scanner scanner = new Scanner(System.in);
    
    // Serviços para lidar com usuários, avaliações, receitas, ingredientes e categorias
    private static IUsuarioServico usuarioServico = new UsuarioServico(new UsuarioRepositorio());
    private static IAvaliacaoServico avaliacaoServico = new AvaliacaoServico(new AvaliacaoRepositorio());
    private static IReceitaServico receitaServico = new ReceitaServico(new ReceitaRepositorio());
    private static IIngredienteServico ingredienteServico = new IngredienteServico(new IngredienteRepositorio());
    private static ICategoriaServico categoriaServico = new CategoriaServico(new CategoriaRepositorio());

    // Lista encadeada para armazenar receitas
    private static ListaEncadeada<Receita> listaReceitas = new ListaEncadeada<>();

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
    private static void menuUsuarios() {
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
                System.out.println("Digite o ID:");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Digite o Nome:");
                String nome = scanner.nextLine();
                usuarioServico.adicionarUsuario(new Usuario(id, nome));
                break;
            case 2:
                System.out.println("Digite o ID:");
                id = scanner.nextInt();
                Usuario usuario = usuarioServico.buscarUsuario(id);
                if (usuario != null) {
                    System.out.println("Usuário: " + usuario.getNome());
                } else {
                    System.out.println("Usuário não encontrado.");
                }
                break;
            case 3:
                System.out.println("Digite o ID:");
                id = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Digite o Novo Nome:");
                nome = scanner.nextLine();
                usuarioServico.atualizarUsuario(new Usuario(id, nome));
                break;
            case 4:
                System.out.println("Digite o ID:");
                id = scanner.nextInt();
                usuarioServico.removerUsuario(id);
                break;
            case 5:
                List<Usuario> usuarios = usuarioServico.listarUsuarios();
                for (Usuario u : usuarios) {
                    System.out.println("ID: " + u.getId() + ", Nome: " + u.getNome());
                }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // Menu de interação com avaliações
    private static void menuAvaliacoes() {
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
                System.out.println("Digite o ID:");
                int id = scanner.nextInt();
                System.out.println("Digite a Nota:");
                int nota = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Digite o Comentário:");
                String comentario = scanner.nextLine();
                avaliacaoServico.adicionarAvaliacao(new Avaliacao(id, nota, comentario));
                break;
            case 2:
                System.out.println("Digite o ID:");
                id = scanner.nextInt();
                Avaliacao avaliacao = avaliacaoServico.buscarAvaliacao(id);
                if (avaliacao != null) {
                    System.out.println("Nota: " + avaliacao.getNota() + ", Comentário: " + avaliacao.getComentario());
                } else {
                    System.out.println("Avaliação não encontrada.");
                }
                break;
            case 3:
                System.out.println("Digite o ID:");
                id = scanner.nextInt();
                System.out.println("Digite a Nova Nota:");
                nota = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Digite o Novo Comentário:");
                comentario = scanner.nextLine();
                avaliacaoServico.atualizarAvaliacao(new Avaliacao(id, nota, comentario));
                break;
            case 4:
                System.out.println("Digite o ID:");
                id = scanner.nextInt();
                avaliacaoServico.removerAvaliacao(id);
                break;
            case 5:
                List<Avaliacao> avaliacoes = avaliacaoServico.listarAvaliacoes();
                for (Avaliacao a : avaliacoes) {
                    System.out.println("ID: " + a.getId() + ", Nota: " + a.getNota() + ", Comentário: " + a.getComentario());
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
                System.out.println("Digite o ID:");
                int id = scanner.nextInt();
                System.out.println("Digite o Nome:");
                String nome = scanner.nextLine();
                categoriaServico.adicionarCategoria(new Categoria(id, nome));
                break;
            case 2:
                System.out.println("Digite o ID:");
                id = scanner.nextInt();
                Categoria categoria = categoriaServico.buscarCategoria(id);
                if (categoria != null) {
                    System.out.println("Categoria: " + categoria.getNome());
                } else {
                    System.out.println("Categoria não encontrada.");
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
