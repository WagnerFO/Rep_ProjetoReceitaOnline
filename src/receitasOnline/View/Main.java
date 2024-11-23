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
            	// Solicitar o ID do usuário
            	System.out.println("Digite o ID do usuário:");
            	int id = scanner.nextInt();
            	// Chamar o método buscar e armazenar o retorno
            	Usuario usuario2 = repositorioUsuarioSql.buscar(id);

            	// Verificar se o usuário foi encontrado e exibir o resultado
            	if (usuario2 != null) {
            	    System.out.println("Usuário encontrado: " + usuario2.getNome());
            	} else {
            	    System.out.println("Nenhum usuário encontrado com o ID fornecido.");
            	}
                break;

            case 3:
            	// Solicitar o ID do usuário
            	System.out.println("Digite o ID do usuário a ser atualizado:");
            	int id1 = scanner.nextInt();
            	scanner.nextLine();  // Limpar o buffer

            	// Solicitar os novos dados
            	System.out.println("Digite o Novo Nome:");
            	String nome1 = scanner.nextLine();
            	System.out.println("Digite o Novo Email:");
            	String email1 = scanner.nextLine();
            	System.out.println("Digite a Nova Senha:");
            	String senha1 = scanner.nextLine();
            	System.out.println("Digite a Nova Rua:");
            	String rua1 = scanner.nextLine();
            	System.out.println("Digite o Novo Número:");
            	int numero1 = scanner.nextInt();
            	scanner.nextLine();  // Limpar o buffer
            	System.out.println("Digite a Nova Cidade:");
            	String cidade1 = scanner.nextLine();
            	System.out.println("Digite o Novo stado:");
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
            	// Código onde você vai solicitar o ID do usuário e chamar o método remover
            	System.out.println("Digite o ID do usuário a ser removido:");
            	Usuario usuario4 = new Usuario();
            	usuario4.setId(scanner.nextInt());  // Solicita o ID do usuário a ser removido

            	// Chama o método de remoção
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
            	// Solicitar dados para adicionar a avaliação
                System.out.println("Digite o ID do usuário:");
                int usuarioId = scanner.nextInt();
                System.out.println("Digite a nota (1 a 10):");
                int nota = scanner.nextInt();
                scanner.nextLine();  // Limpar o buffer
                System.out.println("Digite o comentário:");
                String comentario = scanner.nextLine();

                // Criar o objeto Avaliacao
                Avaliacao avaliacao = new Avaliacao(nota, comentario, usuarioId);
                
                // Chamar o método para adicionar a avaliação
                try {
                    repositorioAvaliacaoSql.adicionar(avaliacao);
                } catch (SQLException e) {
                    System.out.println("Erro ao adicionar a avaliação: " + e.getMessage());
                }
                break;
                
            case 2:
            	// Solicitar o ID do usuário
            	System.out.println("Digite o ID da Avaliação:");
            	int id2 = scanner.nextInt();
            	// Chamar o método buscar e armazenar o retorno
            	Avaliacao avaliacao2 = repositorioAvaliacaoSql.buscar(id2);

            	// Verificar se o avaliacao foi encontrado e exibir o resultado
            	if (avaliacao2 != null) {
            	    System.out.println("Avaliação encontrada: a Nota é: " + avaliacao2.getNota());
            	} else {
            	    System.out.println("Nenhum avaliação encontrado com o ID fornecido.");
            	}
                break;
            case 3:
            	System.out.println("Digite o ID da avaliação que deseja atualizar:");
            	int idAvaliacao = scanner.nextInt();
            	scanner.nextLine(); // Limpar o buffer do scanner

            	// Busca a avaliação existente no banco de dados
            	Avaliacao avaliacaoExistente = repositorioAvaliacaoSql.buscar(idAvaliacao);

            	if (avaliacaoExistente == null) {
            	    System.out.println("Erro: Avaliação com o ID " + idAvaliacao + " não encontrada.");
            	} else {
            	    // Solicitar os novos dados
            	    System.out.println("Digite a Nova Nota:");
            	    int novaNota = scanner.nextInt();
            	    scanner.nextLine(); // Limpar o buffer do scanner

            	    System.out.println("Digite o Novo Comentário:");
            	    String novoComentario = scanner.nextLine();

            	    // Atualizar os campos da avaliação
            	    avaliacaoExistente.setNota(novaNota);
            	    avaliacaoExistente.setComentario(novoComentario);

            	    // Chamar o método de atualização
            	    try {
            	        repositorioAvaliacaoSql.atualizar(avaliacaoExistente);
            	    } catch (SQLException e) {
            	        System.out.println("Erro ao atualizar a avaliação: " + e.getMessage());
            	    }
            	}
            	break;
            case 4:
            	 // Código onde você vai solicitar o ID da Avaliação e chamar o método remover
            	System.out.println("Digite o ID da Avaliação a ser removido:");
            	Avaliacao avaliacao4 = new Avaliacao();
            	avaliacao4.setId(scanner.nextInt());  // Solicita o ID do usuário a ser removido

            	// Chama o método de remoção
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

                // Pedir o nome da categoria, não o ID
                System.out.println("Digite o nome da categoria:");
                String nomeCategoria = scanner.nextLine();
                Categoria categoria = new Categoria();
                categoria.setNome(nomeCategoria);

                System.out.println("Digite o tempo de preparo (em minutos):");
                int tempoPreparo = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha após o int

                // Agora, pedimos para o usuário inserir os ingredientes com quantidade
                System.out.println("Digite os ingredientes com quantidade (formato: nome:quantidade), separados por vírgula:");
                String ingredientesInput = scanner.nextLine();

                // Criar uma lista de objetos Ingrediente a partir da entrada do usuário
                List<Ingrediente> ingredientes = new ArrayList<>();
                for (String ingredienteInput : ingredientesInput.split(",")) {
                    String[] partes = ingredienteInput.split(":");
                    if (partes.length == 2) {
                        String nomeIngrediente = partes[0].trim();
                        try {
                            double quantidadeIngrediente = Double.parseDouble(partes[1].trim());
                            if (quantidadeIngrediente <= 0) {
                                System.out.println("A quantidade do ingrediente deve ser um valor positivo: " + nomeIngrediente);
                                continue;
                            }
                            Ingrediente ingrediente = new Ingrediente(nomeIngrediente, quantidadeIngrediente);
                            ingredientes.add(ingrediente);
                        } catch (NumberFormatException e) {
                            System.out.println("Erro ao interpretar a quantidade para o ingrediente: " + nomeIngrediente);
                        }
                    } else {
                        System.out.println("Formato inválido para o ingrediente: " + ingredienteInput);
                    }
                }

                // Criar o objeto ReceitaPrincipal com a lista de ingredientes
                ReceitaPrincipal receitaP = new ReceitaPrincipal(titulo, descricao, modoPreparo, ingredientes, categoria, tempoPreparo);

                try {
                    // Verifica ou cria a categoria, e depois adiciona a receita
                    repositorioReceitaPrincipalSql.adicionar(receitaP);
                    System.out.println("Receita adicionada com sucesso!");
                } catch (SQLException e) {
                    System.out.println("Erro ao adicionar receita: " + e.getMessage());
                }
                break;
            case 2: 
                ReceitaPrincipal receitaP2 = new ReceitaPrincipal();
                System.out.println("Digite o ID:");
                receitaP2.setId(scanner.nextInt());
                System.out.println("\n");
                
                // Buscar a receita, o método buscar agora não retorna valor, então temos que verificar a receita
                repositorioReceitaPrincipalSql.buscar(receitaP2); // Preenche a receita com as informações

                // Verificar se a receita foi encontrada
                if (receitaP2.getId() != 0) {
                    // Imprimir informações da receita
                    System.out.println("Receita Encontrada:");
                    System.out.println("Título: " + receitaP2.getTitulo());
                    System.out.println("Descrição: " + receitaP2.getDescricao());
                    System.out.println("Modo de Preparo: " + receitaP2.getModoPreparo());
                    System.out.println("Tempo de Preparo: " + receitaP2.getTempoPreparo() + " minutos");
                    
                    // Imprimir a categoria
                    System.out.println("Categoria: " + receitaP2.getCategoria().getNome());

                    // Imprimir os ingredientes
                    System.out.println("\nIngredientes:");
                    for (Ingrediente ingrediente : receitaP2.getIngredientes()) {
                        System.out.println(ingrediente.getNome() + ": " + ingrediente.getQuantidade() + " unidades");
                    }
                } else {
                    System.out.println("Receita não encontrada!");
                }
                break;
                case 3:System.out.println("Digite o ID:");
                int idRP = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Digite o título da receita:");
                String titulo3 = scanner.nextLine();
                System.out.println("Digite a descrição da receita:");
                String descricao3 = scanner.nextLine();
                System.out.println("Digite o modo de preparo:");
                String modoPreparo3 = scanner.nextLine();
                System.out.println("Digite o ID da categoria:");
                int idCategoria = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha após o int

                // Agora buscamos a categoria no banco de dados usando o ID fornecido
                Categoria categoria3 = repositorioCategoriaSql.buscar(idCategoria); // Método buscar no repositório de categorias
                if (categoria3 == null) {
                    System.out.println("Categoria com ID " + idCategoria + " não encontrada.");
                    break;  // Encerra a operação caso a categoria não seja encontrada
                }

                System.out.println("Digite o tempo de preparo (em minutos):");
                int tempoPreparo3 = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha após o int

                // Agora, pedimos para o usuário inserir os ingredientes com quantidade
                System.out.println("Digite os ingredientes com quantidade (formato: nome:quantidade), separados por vírgula:");
                String ingredientesInput3 = scanner.nextLine();

                // Criar uma lista de objetos Ingrediente a partir da entrada do usuário
                List<Ingrediente> ingredientes3 = new ArrayList<>();
                for (String ingredienteInput : ingredientesInput3.split(",")) {
                    String[] partes = ingredienteInput.split(":");
                    if (partes.length == 2) {
                        String nomeIngrediente = partes[0].trim();
                        try {
                            double quantidadeIngrediente = Double.parseDouble(partes[1].trim());
                            if (quantidadeIngrediente <= 0) {
                                System.out.println("A quantidade do ingrediente deve ser um valor positivo: " + nomeIngrediente);
                                continue;
                            }
                            Ingrediente ingrediente3 = new Ingrediente(nomeIngrediente, quantidadeIngrediente);
                            ingredientes3.add(ingrediente3);
                        } catch (NumberFormatException e) {
                            System.out.println("Erro ao interpretar a quantidade para o ingrediente: " + nomeIngrediente);
                        }
                    } else {
                        System.out.println("Formato inválido para o ingrediente: " + ingredienteInput);
                    }
                }

                ReceitaPrincipal receitaP3 = new ReceitaPrincipal(idRP, titulo3, descricao3, modoPreparo3, ingredientes3, categoria3, tempoPreparo3);

                try {
                    repositorioReceitaPrincipalSql.atualizar(receitaP3);
                } catch (SQLException e) {
                    System.out.println("Erro ao atualizar Receita: " + e.getMessage());
                }
                break;
                case 4:
                    System.out.println("Digite o ID da receita que deseja remover:");
                    ReceitaPrincipal receitaP4 = new ReceitaPrincipal();
                    receitaP4.setId(scanner.nextInt());

                    // Chama o método remover
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
            	System.out.println("Digite o Nome do Ingrediente:");
                String nome = scanner.nextLine();
                System.out.println("Digite a Quantidade do Ingrediente:");
                double quantidade = scanner.nextDouble();
                scanner.nextLine(); // Limpar o buffer do scanner

                Ingrediente ingrediente = new Ingrediente(nome, quantidade);
                try {
                    repositorioIngredienteSql.adicionar(ingrediente); // Não precisa passar receitaId aqui
                    System.out.println("Ingrediente adicionado com sucesso!");
                } catch (SQLException e) {
                    System.out.println("Erro ao adicionar o ingrediente: " + e.getMessage());
                }
                break;
            case 2:
            	Ingrediente ingrediente2 = new Ingrediente();
            	System.out.println("Digite o ID:");
            	ingrediente2.setId(scanner.nextInt());

            	// Chama o método buscar e armazena o resultado na variável ingrediente2
            	Ingrediente ingredienteEncontrado = repositorioIngredienteSql.buscar(ingrediente2.getId());

            	if (ingredienteEncontrado != null) {
            	    System.out.println("Ingrediente: " + ingredienteEncontrado.getNome());
            	} else {
            	    System.out.println("Ingrediente não encontrado.");
            	}
            	break;
            case 3:
                System.out.println("Digite o ID do ingrediente a ser atualizado:");
                int id3 = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer de entrada

                System.out.println("Digite o Novo Nome:");
                String nome3 = scanner.nextLine();

                // Adicionando validação para o tipo de dado de quantidade (double)
                double quantidade3 = 0;
                boolean validQuantidade = false;
                while (!validQuantidade) {
                    System.out.println("Digite a nova quantidade: ");
                    if (scanner.hasNextDouble()) {
                        quantidade3 = scanner.nextDouble();
                        validQuantidade = true;
                    } else {
                        System.out.println("Erro: Digite um número válido para a quantidade.");
                        scanner.nextLine(); // Limpa o buffer de entrada para o próximo valor
                    }
                }

                // Criar o objeto Ingrediente
                Ingrediente ingrediente3 = new Ingrediente(id3, nome3, quantidade3);

                // Chamar o método para atualizar
                try {
                    repositorioIngredienteSql.atualizar(ingrediente3); // Passa o ingrediente para o repositório
                } catch (SQLException e) {
                    System.out.println("Erro ao atualizar o ingrediente: " + e.getMessage());
                }
                break;


            case 4:
            	 // Código onde você vai solicitar o ID do Ingrediente e chamar o método remover
            	System.out.println("Digite o ID do Ingrediente a ser removido:");
            	Ingrediente ingrediente4 = new Ingrediente();
            	ingrediente4.setId(scanner.nextInt());  // Solicita o ID do ingrediente a ser removido

            	// Chama o método de remoção
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
                System.out.println("Digite o nome da Categoria:");
                String nome1 = scanner.nextLine();
                Categoria categoria = new Categoria(nome1);
                try {
            	    repositorioCategoriaSql.adicionar(categoria);
            	    System.out.println("Categoria adicionada com sucesso!");
            	} catch (SQLException e) {
            	    System.out.println("Erro ao adicionar a categoria: " + e.getMessage());
            	}
                break;
            case 2:
            	Categoria categoria2 = new Categoria();
                System.out.println("Digite o ID:");
                categoria2.setId(scanner.nextInt());
                
                Categoria categoriaEncontrada = repositorioCategoriaSql.buscar(categoria2.getId());
                
                if (categoriaEncontrada != null) {
                    System.out.println("Categoria: " + categoriaEncontrada.getNome());
                }else {
                	System.out.println("Categoria não encontrada. ");
                }
                break;
            case 3:
                System.out.println("Digite o ID da categoria a ser atualizada:");
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

}
