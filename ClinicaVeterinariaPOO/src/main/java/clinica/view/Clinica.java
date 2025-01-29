package clinica.view;

import clinica.model.dao.ProdutoDAO;
import clinica.model.entity.Cliente;
import clinica.model.entity.Consulta;
import clinica.model.entity.Pet;
import clinica.model.entity.Produto;
import clinica.model.entity.Servico;
import clinica.model.entity.Venda;
import clinica.model.entity.Veterinario;
import clinica.model.service.ClinicaService;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;


public class Clinica {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClinicaService clinicaService = new ClinicaService();

    public static void main(String[] args) {
        int option;
        do {
            exibirMenuPrincipal();
            option = scanner.nextInt();
            scanner.nextLine();  // Clear buffer

            switch (option) {
                case 1:
                    exibirMenuProduto();
                    break;
                case 2:
                    exibirMenuCliente();
                    break;
                case 3:
                    exibirMenuConsulta();
                    break;
                case 4:
                    exibirMenuServico();
                    break;
                case 5:
                    exibirMenuVenda();
                    break;
                case 6:
                    exibirMenuPet();
                    break;
                case 7:
                    exibirMenuVeterinario();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n*** Menu Principal da Clínica ***");
        System.out.println("1 - Operecoes Produto");
        System.out.println("2 - Operecoes Cliente");
        System.out.println("3 - Operecoes Consulta");
        System.out.println("4 - Operecoes Serviço");
        System.out.println("5 - Operecoes Venda");
        System.out.println("6 - Operecoes Pet");
        System.out.println("7 - Operecoes Veterinario");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma classe: ");
    }
    
    // Produto
    private static void exibirMenuProduto() {
        int option;
        do {
            System.out.println("\n*** Menu Produto ***");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Editar Produto");
            System.out.println("3 - Excluir Produto");
            System.out.println("4 - Consultar Produto");
            System.out.println("6 - Atualizar Preço do Produto");
            System.out.println("7 - Atualizar Estoque do Produto");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma operação: ");
            option = scanner.nextInt();
            scanner.nextLine();  // Clear buffer

            switch (option) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    editarProduto();
                    break;
                case 3:
                    excluirProduto();
                    break;
                case 4:
                    consultarProduto();
                    break;
                case 6:
                    atualizarPrecoProduto();
                    break;
                case 7:
                    atualizarEstoqueProduto();
                    break;
                case 0:
                    return; // Volta para o menu principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);
    }
    
    private static void cadastrarProduto() {
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a descricao do produto: ");
        String descricao = scanner.nextLine();

        System.out.print("Digite o valor do produto: ");
        float preco = scanner.nextFloat();

        System.out.print("Digite a quantidade em estoque: ");
        int estoque = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setEstoque(estoque);

        clinicaService.salvarProduto(produto);
        System.out.println("Produto cadastrado com sucesso.");

        exibirProduto(produto); // Exibir produto após o cadastro
    }

    private static void editarProduto() {
        System.out.print("Digite o ID do produto a ser editado: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        Produto produto = clinicaService.consultarProduto(id);
        if (produto != null) {
            System.out.print("Digite o novo nome do produto: ");
            produto.setNome(scanner.nextLine());

            System.out.print("Digite a nova descrição do produto: ");
            produto.setDescricao(scanner.nextLine());

            System.out.print("Digite o novo valor do produto: ");
            produto.setPreco(scanner.nextFloat());

            System.out.print("Digite a nova quantidade em estoque: ");
            produto.setEstoque(scanner.nextInt());
            scanner.nextLine();  // Clear buffer

            clinicaService.editarProduto(produto);
            System.out.println("Produto editado com sucesso.");

            exibirProduto(produto); // Exibir produto após a edição
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static void excluirProduto() {
        System.out.print("Digite o ID do produto a ser excluído: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Limpar buffer

        Produto produto = clinicaService.consultarProduto(id);
        if (produto == null) {
            System.out.println("Produto não encontrado!");
            return;
        }

        // Verificar se o produto tem registros vinculados
        boolean temRegistros = clinicaService.temRegistrosRelacionados(id,"produto");

        // Se houver registros vinculados, exibir aviso
        if (temRegistros) {
            System.out.println("\n⚠️ ATENÇÃO: Este produto possui registros vinculados (como vendas).");
            System.out.println("Ao prosseguir com a exclusão, TODOS esses registros também serão apagados.");
        }

        // Exibir os dados do produto antes de perguntar sobre a exclusão
        exibirProduto(produto);

        // Perguntar ao usuário se deseja continuar com a exclusão
        System.out.print("Tem certeza que deseja excluir este produto e todos os registros vinculados? (S/N): ");
        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("S")) {
            // Excluir o produto (com exclusão em cascata dos registros vinculados)
            clinicaService.excluirProduto(produto);
            System.out.println("Produto excluído com sucesso!");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    private static void consultarProduto() {
        System.out.println("Buscar produto por:");
        System.out.println("1 - ID");
        System.out.println("2 - Nome");
        System.out.println("3 - Listar Todos");
        System.out.print("Escolha uma opção: ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (escolha == 1) {
            System.out.print("ID do Produto para consulta: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer
            Produto produto = clinicaService.consultarProduto(id);
            exibirProduto(produto); // Usar a função para exibir o produto
        } else if (escolha == 2) {
            System.out.print("Nome do Produto para consulta: ");
            String nome = scanner.nextLine();
            List<Produto> produtos = clinicaService.consultarProduto(nome);
            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto encontrado!");
            } else {
                System.out.println("\nProdutos encontrados:");
                for (Produto produto : produtos) {
                    exibirProduto(produto); // Usar a função para exibir o produto
                }
            }
        } else if (escolha == 3) {
            List<Produto> produtos = clinicaService.listarTodosProdutos();
            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto encontrado.");
            } else {
                System.out.println("\nLista de Produtos:");
                for (Produto produto : produtos) {
                    exibirProduto(produto); // Usar a função para exibir o produto
                }
            }
        } else {
            System.out.println("Opção inválida!");
        }
    }

    private static void atualizarPrecoProduto() {
        System.out.print("Digite o ID do produto: ");
        int id = scanner.nextInt();

        System.out.print("Digite o novo preço do produto: ");
        float novoPreco = scanner.nextFloat();
        scanner.nextLine();  // Clear buffer

        boolean atualizado = clinicaService.atualizarPrecoProduto(id, novoPreco);
        if (atualizado) {
            System.out.println("Preço atualizado com sucesso.");
            Produto produtoAtualizado = clinicaService.consultarProduto(id); // Obter o produto atualizado
            exibirProduto(produtoAtualizado); // Exibir os detalhes do produto após atualização
        } else {
            System.out.println("Erro ao atualizar preço. Verifique o ID.");
        }
    }

    private static void atualizarEstoqueProduto() {
        System.out.print("Digite o ID do produto: ");
        int id = scanner.nextInt();

        System.out.print("Digite a nova quantidade em estoque: ");
        int novoEstoque = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        boolean atualizado = clinicaService.atualizarEstoqueProduto(id, novoEstoque);
        if (atualizado) {
            System.out.println("Estoque atualizado com sucesso.");
            Produto produtoAtualizado = clinicaService.consultarProduto(id); // Obter o produto atualizado
            exibirProduto(produtoAtualizado); // Exibir os detalhes do produto após atualização
        } else {
            System.out.println("Erro ao atualizar estoque. Verifique o ID.");
        }
    }

    private static void exibirProduto(Produto produto) {
        if (produto != null) {
            System.out.println("\nDetalhes do Produto:");
            System.out.println("ID: " + produto.getId());
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Descrição: " + produto.getDescricao());
            System.out.println("Preço: R$ " + produto.getPreco());
            System.out.println("Estoque: " + produto.getEstoque());
            System.out.println("----------------------------");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }
    //Fim Produto
    
    
    //Cliente
    private static void exibirMenuCliente() {
        int option;
        do {
            System.out.println("\n*** Menu Cliente ***");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Editar Cliente");
            System.out.println("3 - Excluir Cliente");
            System.out.println("4 - Consultar Cliente");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma operação: ");
            option = scanner.nextInt();
            scanner.nextLine();  // Clear buffer

            switch (option) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    editarCliente();
                    break;
                case 3:
                    excluirCliente();
                    break;
                case 4:
                    consultarCliente();
                    break;
                case 0:
                    return; // Volta para o menu principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);
    }
    
    private static void cadastrarCliente() {
        System.out.print("Nome do Cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone do Cliente: ");
        String telefone = scanner.nextLine();
        System.out.print("Email do Cliente: ");
        String email = scanner.nextLine();
        Cliente cliente = new Cliente();
        cliente.setNome(nome);           // Atribui o nome
        cliente.setTelefone(telefone);   // Atribui o telefone
        cliente.setEmail(email);         // Atribui o email
        clinicaService.salvarCliente(cliente);
        System.out.println("Cliente cadastrado com sucesso!");

        exibirCliente(cliente); // Exibir cliente após o cadastro
    }

    private static void editarCliente() {
        System.out.print("ID do Cliente para edição: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        Cliente cliente = clinicaService.consultarCliente(id);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.print("Novo Nome do Cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Novo Telefone do Cliente: ");
        String telefone = scanner.nextLine();
        System.out.print("Novo Email do Cliente: ");
        String email = scanner.nextLine();

        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
        clinicaService.editarCliente(cliente);
        System.out.println("Cliente editado com sucesso!");

        exibirCliente(cliente); // Exibir cliente após a edição
    }

    private static void excluirCliente() {
        System.out.print("ID do Cliente para exclusão: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        Cliente cliente = clinicaService.consultarCliente(id);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        // Verificar se o cliente tem registros vinculados
        boolean temRegistros = clinicaService.temRegistrosRelacionados(id, "cliente");

        // Se houver registros vinculados, exibir aviso
        if (temRegistros) {
            System.out.println("\n⚠️ ATENÇÃO: Este cliente possui registros vinculados (como pet ou vendas).");
            System.out.println("Ao prosseguir com a exclusão, TODOS esses registros também serão apagados.");
        }

        // Exibir os dados do cliente antes de perguntar sobre a exclusão
        exibirCliente(cliente);

        // Perguntar ao usuário se deseja continuar com a exclusão
        System.out.print("Tem certeza que deseja excluir este cliente e todos os registros vinculados? (S/N): ");
        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("S")) {
            // Excluir o cliente (com exclusão em cascata dos registros vinculados)
            clinicaService.excluirCliente(cliente.getId());
            System.out.println("Cliente excluído com sucesso!");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    private static void consultarCliente() {
        System.out.println("Buscar cliente por:");
        System.out.println("1 - ID");
        System.out.println("2 - Nome");
        System.out.println("3 - Todos os Clientes");
        System.out.print("Escolha uma opção: ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (escolha == 1) {
            System.out.print("ID do Cliente para consulta: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer
            Cliente cliente = clinicaService.consultarCliente(id);
            if (cliente == null) {
                System.out.println("Cliente não encontrado!");
            } else {
                System.out.println("Cliente encontrado: ");
                exibirCliente(cliente); // Exibir cliente encontrado
            }
        } else if (escolha == 2) {
            System.out.print("Nome do Cliente para consulta: ");
            String nome = scanner.nextLine();
            List<Cliente> clientes = clinicaService.consultarCliente(nome);
            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente encontrado!");
            } else {
                System.out.println("\nClientes encontrados:");
                for (Cliente cliente : clientes) {
                    exibirCliente(cliente); // Exibir cliente encontrado
                }
            }
        } else if (escolha == 3) {
            List<Cliente> clientes = clinicaService.listarTodosClientes();
            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente encontrado.");
            } else {
                System.out.println("\n*** Lista de Clientes ***");
                for (Cliente cliente : clientes) {
                    exibirCliente(cliente); // Exibir cada cliente da lista
                }
            }
        } else {
            System.out.println("Opção inválida!");
        }
    }

    private static void exibirCliente(Cliente cliente) {
        if (cliente != null) {
            System.out.println("\nDetalhes do Cliente:");
            System.out.println("ID: " + cliente.getId());
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Telefone: " + cliente.getTelefone());
            System.out.println("Email: " + cliente.getEmail());
            System.out.println("----------------------------");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    //Fim Cliente
    
    
    // Venda
    private static void exibirMenuVenda() {
        int option;
        do {
            System.out.println("\n*** Menu Venda ***");
            System.out.println("1 - Cadastrar Venda");
            System.out.println("2 - Editar Venda");
            System.out.println("3 - Excluir Venda");
            System.out.println("4 - Consultar Venda");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma operação: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (option) {
                case 1:
                    cadastrarVenda();
                    break;
                case 2:
                    editarVenda();
                    break;
                case 3:
                    excluirVenda();
                    break;
                case 4:
                    consultarVenda();
                    break;
           
                case 0:
                    return; // Volta para o menu principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);
    }
    
    private static void cadastrarVenda() {
        System.out.print("Digite o ID do cliente: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        // Verificar se o cliente existe
        Cliente cliente = clinicaService.consultarCliente(clienteId);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.print("Digite o ID do produto: ");
        int produtoId = scanner.nextInt();
        System.out.print("Digite a quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        // Verificar se o produto existe
        Produto produto = clinicaService.consultarProduto(produtoId);
        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        // Verificar se há estoque suficiente
        if (produto.getEstoque() < quantidade) {
            System.out.println("Estoque insuficiente para o produto.");
            return;
        }

        System.out.print("Digite a forma de pagamento (Pix, Cartão de Crédito, etc.): ");
        String formaPagamento = scanner.nextLine();

        // Solicitar data e hora ao usuário (formato: yyyy-MM-dd HH:mm)
        System.out.print("Digite a data e hora da venda (formato: yyyy-MM-dd HH:mm): ");
        String dataHoraString = scanner.nextLine();

        // Converter a string para Timestamp, mantendo a hora corretamente
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        java.util.Date utilDate = null;
        try {
            utilDate = formatoData.parse(dataHoraString); // Converte para Date utilitário
        } catch (ParseException e) {
            System.out.println("Formato de data e hora inválido.");
            return;
        }

        // Converte de util.Date para java.sql.Timestamp para persistir corretamente
        Timestamp dataVenda = new Timestamp(utilDate.getTime());

        // Calcular o valor da venda
        double valorPraticado = produto.getPreco() * quantidade;

        // Criar e configurar o objeto Venda
        Venda venda = new Venda();
        venda.setCliente(cliente); // Set cliente
        venda.setProduto(produto); // Set produto
        venda.setQuantidade(quantidade); // Set quantidade
        venda.setData(dataVenda); // Set data da venda
        venda.setFormaPagamento(formaPagamento); // Set forma de pagamento
        venda.setValorPraticado(valorPraticado); // Set valor praticado

        // Salvar a venda
        clinicaService.salvarVenda(venda);

        // Atualizar o estoque após a venda
        int novoEstoque = produto.getEstoque() - quantidade;
        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.atualizarEstoque(produto.getId(), novoEstoque); // Atualizando o estoque no banco de dados

        // Exibir as informações detalhadas da venda
        System.out.println("\nVenda cadastrada com sucesso!");
        exibirDetalhesVenda(venda); // Exibindo os detalhes da venda cadastrada
    }

    private static void editarVenda() {
        System.out.print("Digite o ID da venda a ser editada: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        Venda venda = clinicaService.consultarVenda(id);
        if (venda != null) {
            System.out.print("Digite a nova quantidade: ");
            int novaQuantidade = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            Produto produto = venda.getProduto(); // Produto da venda original
            int estoqueAtual = produto.getEstoque();

            // Verificar se há estoque suficiente para a nova quantidade
            if (novaQuantidade > estoqueAtual) {
                System.out.println("Estoque insuficiente para a nova quantidade.");
                return;
            }

            // Se a quantidade for diminuída, adicionar de volta ao estoque
            if (novaQuantidade < venda.getQuantidade()) {
                int quantidadeDevolver = venda.getQuantidade() - novaQuantidade;
                ProdutoDAO produtoDAO = new ProdutoDAO();
                produtoDAO.atualizarEstoque(produto.getId(), estoqueAtual + quantidadeDevolver);
            } else if (novaQuantidade > venda.getQuantidade()) {
                // Se a quantidade for aumentada, reduzir o estoque
                int quantidadeRetirar = novaQuantidade - venda.getQuantidade();
                ProdutoDAO produtoDAO = new ProdutoDAO();
                produtoDAO.atualizarEstoque(produto.getId(), estoqueAtual - quantidadeRetirar);
            }

            // Atualizar a quantidade na venda
            venda.setQuantidade(novaQuantidade);

            // Recalcular o valor praticado
            double novoValorPraticado = produto.getPreco() * novaQuantidade;
            venda.setValorPraticado(novoValorPraticado);

            System.out.print("Digite a nova forma de pagamento (Pix, Cartão de Crédito, etc.): ");
            String novaFormaPagamento = scanner.nextLine();
            venda.setFormaPagamento(novaFormaPagamento); // Atualizando a forma de pagamento

            // Editar a venda no banco de dados
            clinicaService.editarVenda(venda);
            System.out.println("Venda editada com sucesso.");
        } else {
            System.out.println("Venda não encontrada.");
        }
    }

    private static void excluirVenda() {
        System.out.print("Digite o ID da venda a ser excluída: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        Venda venda = clinicaService.consultarVenda(id);
        if (venda != null) {
            // Exibindo os detalhes da venda usando a função exibirDetalhesVenda
            exibirDetalhesVenda(venda);

            // Perguntando ao usuário se realmente deseja excluir a venda
            System.out.print("Tem certeza que deseja excluir essa venda? (s/n): ");
            String resposta = scanner.nextLine();

            if (resposta.equalsIgnoreCase("s")) {
                // Verificando o estoque do produto antes de excluir a venda
                Produto produto = venda.getProduto();
                ProdutoDAO produtoDAO = new ProdutoDAO();

                // Atualizando o estoque do produto, somando a quantidade da venda
                boolean estoqueAtualizado = produtoDAO.atualizarEstoque(produto.getId(), produto.getEstoque() + venda.getQuantidade());

                if (estoqueAtualizado) {
                    // Excluindo a venda
                    clinicaService.excluirVenda(venda);
                    System.out.println("\nVenda excluída com sucesso e estoque atualizado.");
                } else {
                    System.out.println("Erro ao atualizar o estoque. Venda não excluída.");
                }
            } else {
                System.out.println("Exclusão cancelada.");
            }
        } else {
            System.out.println("Venda não encontrada.");
        }
    }

    private static void consultarVenda() {
        System.out.println("Escolha o tipo de consulta:");
        System.out.println("1 - Por ID");
        System.out.println("2 - Entre datas");
        System.out.println("3 - Entre valores");
        System.out.println("4 - Por método de pagamento");
        System.out.println("5 - Por ID de produto");
        System.out.println("6 - Mostrar todas as vendas");
        int opcao = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        switch (opcao) {
            case 1: // Consulta por ID
                System.out.print("Digite o ID da venda a ser consultada: ");
                int id = scanner.nextInt();
                scanner.nextLine();  // Clear buffer
                Venda venda = clinicaService.consultarVenda(id);
                if (venda != null) {
                    exibirDetalhesVenda(venda);
                } else {
                    System.out.println("Venda não encontrada.");
                }
                break;

            case 2: // Consulta entre datas
                System.out.print("Digite a data inicial (yyyy-MM-dd): ");
                String dataInicialStr = scanner.nextLine();
                System.out.print("Digite a data final (yyyy-MM-dd): ");
                String dataFinalStr = scanner.nextLine();
                List<Venda> vendasPorData = clinicaService.consultarVendasEntreDatas(dataInicialStr, dataFinalStr);
                if (vendasPorData.isEmpty()) {
                    System.out.println("Nenhuma venda encontrada nesse intervalo de datas.");
                } else {
                    for (Venda v : vendasPorData) {
                        exibirDetalhesVenda(v);
                    }
                }
                break;

            case 3: // Consulta entre valores
                System.out.print("Digite o valor mínimo: ");
                double valorMinimo = scanner.nextDouble();
                System.out.print("Digite o valor máximo: ");
                double valorMaximo = scanner.nextDouble();
                List<Venda> vendasPorValor = clinicaService.consultarVendasEntreValores(valorMinimo, valorMaximo);
                if (vendasPorValor.isEmpty()) {
                    System.out.println("Nenhuma venda encontrada nesse intervalo de valores.");
                } else {
                    for (Venda v : vendasPorValor) {
                        exibirDetalhesVenda(v);
                    }
                }
                break;

            case 4: // Consulta por método de pagamento
                System.out.print("Digite o método de pagamento (ex: 'cartão', 'dinheiro', etc.): ");
                String metodoPagamento = scanner.nextLine();
                List<Venda> vendasPorPagamento = clinicaService.consultarVendasPorMetodoPagamento(metodoPagamento);
                if (vendasPorPagamento.isEmpty()) {
                    System.out.println("Nenhuma venda encontrada com esse método de pagamento.");
                } else {
                    for (Venda v : vendasPorPagamento) {
                        exibirDetalhesVenda(v);
                    }
                }
                break;

            case 5: // Consulta por ID de produto
                System.out.print("Digite o ID do produto: ");
                int produtoId = scanner.nextInt();
                scanner.nextLine();  // Clear buffer
                List<Venda> vendasPorProduto = clinicaService.consultarVendasPorProdutoId(produtoId);
                if (vendasPorProduto.isEmpty()) {
                    System.out.println("Nenhuma venda encontrada para esse produto.");
                } else {
                    for (Venda v : vendasPorProduto) {
                        exibirDetalhesVenda(v);
                    }
                }
                break;

            case 6: // Mostrar todas as vendas
                List<Venda> todasVendas = clinicaService.consultarTodasVendas();
                if (todasVendas.isEmpty()) {
                    System.out.println("Nenhuma venda encontrada.");
                } else {
                    for (Venda v : todasVendas) {
                        exibirDetalhesVenda(v);
                    }
                }
                break;

            default:
                System.out.println("Opção inválida.");
        }
    }

    private static void exibirDetalhesVenda(Venda venda) {
        System.out.println("\n--- Detalhes da Venda ---");
        System.out.println("ID da Venda: " + venda.getId());
        System.out.println("Produto: " + venda.getProduto().getNome());
        System.out.println("Quantidade: " + venda.getQuantidade());
        System.out.println("Cliente: " + (venda.getCliente() != null ? venda.getCliente().getNome() : "Não encontrado"));
        System.out.println("Forma de pagamento: " + venda.getFormaPagamento());
        System.out.println("Data da venda: " + venda.getData());
        System.out.println("Valor praticado: R$ " + String.format("%.2f", venda.getValorPraticado()));
        System.out.println("-------------------------\n");
    }
    //Fim Venda
    
    

    
    //Servico
    private static void exibirMenuServico() {
        int option;
        do {
            System.out.println("\n*** Menu Serviço ***");
            System.out.println("1 - Cadastrar Serviço");
            System.out.println("2 - Editar Serviço");
            System.out.println("3 - Excluir Serviço");
            System.out.println("4 - Consultar Serviço");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma operação: ");
            option = scanner.nextInt();
            scanner.nextLine();  // Clear buffer

            switch (option) {
                case 1:
                    cadastrarServico();
                    break;
                case 2:
                    editarServico();
                    break;
                case 3:
                    excluirServico();
                    break;
                case 4:
                    consultarServico();
                    break;
                case 0:
                    return; // Volta para o menu principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);
    }
    
    private static void cadastrarServico() {
        System.out.print("Nome do Serviço: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição do Serviço: ");
        String descricao = scanner.nextLine();
        System.out.print("Preço do Serviço: ");
        float preco = scanner.nextFloat();

        Servico servico = new Servico();
        servico.setNome(nome);
        servico.setDescricao(descricao);
        servico.setPreco(preco);

        clinicaService.cadastrarServico(servico);
        System.out.println("Serviço cadastrado com sucesso!");
        exibirServico(servico); // Exibe o serviço cadastrado
    }

    private static void editarServico() {
        System.out.print("Informe o ID do Serviço para editar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        Servico servico = clinicaService.buscarServicoPorId(id);
        if (servico != null) {
            System.out.print("Novo Nome do Serviço: ");
            servico.setNome(scanner.nextLine());
            System.out.print("Nova Descrição do Serviço: ");
            servico.setDescricao(scanner.nextLine());
            System.out.print("Novo Preço do Serviço: ");
            servico.setPreco(scanner.nextFloat());

            clinicaService.editarServico(servico);
            System.out.println("Serviço editado com sucesso!");
            exibirServico(servico); // Exibe o serviço editado
        } else {
            System.out.println("Serviço não encontrado!");
        }
    }

    private static void excluirServico() {
        System.out.print("Informe o ID do Serviço para excluir: ");
        int id = scanner.nextInt();

        Servico servico = clinicaService.buscarServicoPorId(id);
        if (servico != null) {
            System.out.println("Serviço encontrado:");
            exibirServico(servico); // Exibe o serviço que será excluído

            System.out.print("Tem certeza que deseja excluir esse serviço? (S/N): ");
            scanner.nextLine(); // Limpar buffer
            String resposta = scanner.nextLine();

            if (resposta.equalsIgnoreCase("S")) {
                clinicaService.excluirServico(id);
                System.out.println("Serviço excluído com sucesso!");
            } else {
                System.out.println("Exclusão cancelada.");
            }
        } else {
            System.out.println("Serviço não encontrado!");
        }
    }

    private static void consultarServico() {
        System.out.println("*** Opções de Consulta de Serviço ***");
        System.out.println("1 - Consultar por ID");
        System.out.println("2 - Consultar por Nome (parcial)");
        System.out.println("3 - Consultar por Faixa de Preço");
        System.out.println("4 - Consultar Serviços por ID da Consulta");
        System.out.println("5 - Mostrar Todos os Serviços");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer após o número

        switch (opcao) {
            case 1:
                System.out.print("Informe o ID do Serviço para consultar: ");
                int id = scanner.nextInt();
                Servico servicoPorId = clinicaService.buscarServicoPorId(id);
                exibirServico(servicoPorId); // Exibe o serviço consultado
                break;

            case 2:
                System.out.print("Informe o Nome (ou parte do nome) do Serviço para consultar: ");
                String nome = scanner.nextLine();
                List<Servico> servicosPorNome = clinicaService.buscarServicoPorNome(nome);
                if (servicosPorNome.isEmpty()) {
                    System.out.println("Nenhum serviço encontrado com esse nome.");
                } else {
                    for (Servico servico : servicosPorNome) {
                        exibirServico(servico); // Exibe todos os serviços encontrados
                    }
                }
                break;

            case 3:
                System.out.print("Informe o preço mínimo: ");
                float precoMinimo = scanner.nextFloat();
                System.out.print("Informe o preço máximo: ");
                float precoMaximo = scanner.nextFloat();
                List<Servico> servicosPorFaixaDePreco = clinicaService.buscarServicoPorFaixaDePreco(precoMinimo, precoMaximo);
                if (servicosPorFaixaDePreco.isEmpty()) {
                    System.out.println("Nenhum serviço encontrado nesse intervalo de preço.");
                } else {
                    for (Servico servico : servicosPorFaixaDePreco) {
                        exibirServico(servico); // Exibe todos os serviços encontrados
                    }
                }
                break;

            case 4:
                System.out.print("Informe o ID da Consulta para consultar os serviços relacionados: ");
                int consultaId = scanner.nextInt();
                List<Servico> servicosDaConsulta = clinicaService.buscarServicosPorConsultaId(consultaId);
                if (servicosDaConsulta.isEmpty()) {
                    System.out.println("Nenhum serviço encontrado para essa consulta.");
                } else {
                    for (Servico servico : servicosDaConsulta) {
                        exibirServico(servico); // Exibe os serviços encontrados para a consulta
                    }
                }
                break;

            case 5:
                List<Servico> todosServicos = clinicaService.listarTodosServicos();
                if (todosServicos.isEmpty()) {
                    System.out.println("Nenhum serviço cadastrado.");
                } else {
                    for (Servico servico : todosServicos) {
                        exibirServico(servico); // Exibe todos os serviços cadastrados
                    }
                }
                break;

            default:
                System.out.println("Opção inválida! Tente novamente.");
                break;
        }
    }


    private static void exibirServico(Servico servico) {
        if (servico != null) {
            System.out.println("=====================================");
            System.out.println("           DETALHES DO SERVIÇO       ");
            System.out.println("=====================================");
            System.out.println("ID:        " + servico.getId());
            System.out.println("Nome:      " + servico.getNome());
            System.out.println("Descrição: " + servico.getDescricao());
            System.out.printf("Preço:     R$ %.2f\n", servico.getPreco());
            System.out.println("=====================================");
        } else {
            System.out.println("=====================================");
            System.out.println("           SERVIÇO NÃO ENCONTRADO    ");
            System.out.println("=====================================");
        }
    }
    //Fim servico
    
    
    //Pet
    private static void exibirMenuPet() {
        int option;
        do {
            System.out.println("\n*** Menu Pet ***");
            System.out.println("1 - Cadastrar Pet");
            System.out.println("2 - Editar Pet");
            System.out.println("3 - Excluir Pet");
            System.out.println("4 - Consultar Pet");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma operação: ");
            option = scanner.nextInt();
            scanner.nextLine();  // Clear buffer

            switch (option) {
                case 1:
                    cadastrarPet();
                    break;
                case 2:
                    editarPet();
                    break;
                case 3:
                    excluirPet();
                    break;
                case 4:
                    consultarPet();
                    break;
                case 0:
                    return; // Volta para o menu principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);
    }

    private static void cadastrarPet() {
        System.out.print("Nome do Pet: ");
        String nome = scanner.nextLine();
        System.out.print("Espécie do Pet: ");
        String especie = scanner.nextLine();
        System.out.print("Raça do Pet: ");
        String raca = scanner.nextLine();
        System.out.print("Idade do Pet(Em Anos): ");
        int idade = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        System.out.print("ID do Cliente dono do Pet: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        Cliente cliente = clinicaService.consultarCliente(clienteId);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        Pet pet = new Pet();                            

        pet.setNome(nome);                              
        pet.setEspecie(especie);                        
        pet.setRaca(raca);                             
        pet.setIdade(idade);  // Definindo a idade
        pet.setCliente(cliente);                      

        clinicaService.salvarPet(pet);
        System.out.println("Pet cadastrado com sucesso!");
        exibirPet(pet); // Exibe os detalhes do pet cadastrado
    }

    private static void editarPet() {
        System.out.print("ID do Pet para edição: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        Pet pet = clinicaService.consultarPet(id);
        if (pet == null) {
            System.out.println("Pet não encontrado!");
            return;
        }

        System.out.print("Novo Nome do Pet: ");
        String nome = scanner.nextLine();
        System.out.print("Nova Espécie do Pet: ");
        String especie = scanner.nextLine();
        System.out.print("Nova Raça do Pet: ");
        String raca = scanner.nextLine();
        System.out.print("Nova Idade do Pet: ");
        int idade = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        System.out.print("ID do Novo Dono (Cliente) do Pet (deixe em branco para manter o atual): ");
        String inputDono = scanner.nextLine();

        if (!inputDono.isEmpty()) {
            int novoClienteId = Integer.parseInt(inputDono);
            Cliente novoCliente = clinicaService.consultarCliente(novoClienteId);
            if (novoCliente != null) {
                pet.setCliente(novoCliente); // Atualiza o dono do pet
            } else {
                System.out.println("Cliente não encontrado! Mantendo o dono atual.");
            }
        }

        pet.setNome(nome);
        pet.setEspecie(especie);
        pet.setRaca(raca);
        pet.setIdade(idade); // Atualizando a idade

        clinicaService.editarPet(pet);
        System.out.println("Pet editado com sucesso!");
        exibirPet(pet); // Exibe os detalhes do pet editado
    }

    private static void excluirPet() {
        System.out.print("ID do Pet para exclusão: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        Pet pet = clinicaService.consultarPet(id);
        if (pet == null) {
            System.out.println("Pet não encontrado!");
            return;
        }

        // Verificar se o pet tem registros vinculados
        boolean temRegistros = clinicaService.temRegistrosRelacionados(id, "pet");

        // Se houver registros vinculados, exibir aviso
        if (temRegistros) {
            System.out.println("\n⚠️ ATENÇÃO: Este pet possui registros vinculados (como vendas ou consultas).");
            System.out.println("Ao prosseguir com a exclusão, TODOS esses registros também serão apagados.");
        }

        // Exibir os dados do pet antes de perguntar sobre a exclusão
        exibirPet(pet);

        // Perguntar ao usuário se deseja continuar com a exclusão
        System.out.print("Tem certeza que deseja excluir este pet e todos os registros vinculados? (S/N): ");
        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("S")) {
            // Excluir o pet (com exclusão em cascata dos registros vinculados)
            clinicaService.excluirPet(pet.getId());
            System.out.println("Pet excluído com sucesso!");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    private static void consultarPet() {
        System.out.println("\n*** Consultar Pet ***");
        System.out.println("Escolha um filtro para consulta:");
        System.out.println("1 - Consultar por ID");
        System.out.println("2 - Consultar por Raça");
        System.out.println("3 - Consultar por Espécie");
        System.out.println("4 - Consultar por Idade");
        System.out.println("5 - Consultar por Dono");
        System.out.println("6 - Mostrar Todos os Pets");
        System.out.print("Escolha a opção: ");
        int filtro = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        List<Pet> pets = new ArrayList<>();

        switch (filtro) {
            case 1:
                System.out.print("ID do Pet para consulta: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer
                Pet petById = clinicaService.consultarPet(id);
                if (petById != null) {
                    pets.add(petById);
                } else {
                    System.out.println("Pet não encontrado!");
                }
                break;
            case 2:
                System.out.print("Raça do Pet: ");
                String raca = scanner.nextLine();
                pets = clinicaService.consultarPetsPorRaca(raca);
                break;
            case 3:
                System.out.print("Espécie do Pet: ");
                String especie = scanner.nextLine();
                pets = clinicaService.consultarPetsPorEspecie(especie);
                break;
            case 4:
                System.out.print("Idade do Pet: ");
                int idade = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer
                pets = clinicaService.consultarPetsPorIdade(idade);
                break;
            case 5:
                System.out.print("ID do Dono (Cliente): ");
                int clienteId = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer
                pets = clinicaService.consultarPetsPorDono(clienteId);
                break;
            case 6:
                pets = clinicaService.consultarTodosPets();
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }

        if (pets.isEmpty()) {
            System.out.println("Nenhum pet encontrado com os critérios informados.");
        } else {
            for (Pet pet : pets) {
                exibirPet(pet);
            }
        }
    }
    
    private static void exibirPet(Pet pet) {
        if (pet != null) {
            System.out.println("\n*** Detalhes do Pet ***");
            System.out.println("ID: " + pet.getId());
            System.out.println("Nome: " + pet.getNome());
            System.out.println("Espécie: " + pet.getEspecie());
            System.out.println("Raça: " + pet.getRaca());
            System.out.println("Idade: " + pet.getIdade());  // Exibindo a idade
            System.out.println("Dono: " + pet.getCliente().getNome());
        } else {
            System.out.println("Pet não encontrado!");
        }
    }
    //Fim Pet
    
    
    //Veterinario
    private static void exibirMenuVeterinario() {
        int option;
        do {
            System.out.println("\n*** Menu Veterinário ***");
            System.out.println("1 - Cadastrar Veterinário");
            System.out.println("2 - Editar Veterinário");
            System.out.println("3 - Excluir Veterinário");
            System.out.println("4 - Consultar Veterinário");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma operação: ");
            option = scanner.nextInt();
            scanner.nextLine();  // Clear buffer

            switch (option) {
                case 1:
                    cadastrarVeterinario();
                    break;
                case 2:
                    editarVeterinario();
                    break;
                case 3:
                    excluirVeterinario();
                    break;
                case 4:
                    consultarVeterinario();
                    break;
                case 0:
                    return; // Volta para o menu principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);
    }
    
    private static void cadastrarVeterinario() {
        System.out.print("Nome do Veterinário: ");
        String nome = scanner.nextLine();
        System.out.print("Especialidade do Veterinário: ");
        String especialidade = scanner.nextLine();
        System.out.print("Telefone do Veterinário: ");
        String telefone = scanner.nextLine();

        Veterinario veterinario = new Veterinario();
        veterinario.setNome(nome);
        veterinario.setEspecializacao(especialidade);
        veterinario.setTelefone(telefone);

        clinicaService.salvarVeterinario(veterinario);
        System.out.println("Veterinário cadastrado com sucesso!");

        // Exibir veterinário cadastrado
        exibirVeterinario(veterinario);
    }

    private static void editarVeterinario() {
        System.out.print("ID do Veterinário para edição: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        Veterinario veterinario = clinicaService.consultarVeterinario(id);
        if (veterinario == null) {
            System.out.println("Veterinário não encontrado!");
            return;
        }

        System.out.print("Novo Nome do Veterinário: ");
        String nome = scanner.nextLine();
        System.out.print("Nova Especialidade do Veterinário: ");
        String especialidade = scanner.nextLine();
        System.out.print("Novo Telefone do Veterinário: ");
        String telefone = scanner.nextLine();

        veterinario.setNome(nome);
        veterinario.setEspecializacao(especialidade);
        veterinario.setTelefone(telefone);
        clinicaService.editarVeterinario(veterinario);

        System.out.println("Veterinário editado com sucesso!");

        // Exibir veterinário atualizado
        exibirVeterinario(veterinario);
    }

    private static void excluirVeterinario() {
        System.out.print("ID do Veterinário para exclusão: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        Veterinario veterinario = clinicaService.consultarVeterinario(id);
        if (veterinario == null) {
            System.out.println("Veterinário não encontrado!");
            return;
        }

        boolean temRegistros = clinicaService.temRegistrosRelacionados(id,"veterinario");

        // Se houver registros vinculados, exibir aviso
        if (temRegistros) {
            System.out.println("\n⚠️ ATENÇÃO: Este veterinário possui registros vinculados (como atendimentos).");
            System.out.println("Ao prosseguir com a exclusão, TODOS esses registros também serão apagados.");
        }

        exibirVeterinario(veterinario); // Exibe os detalhes do veterinário antes de excluir
        
        System.out.print("Tem certeza que deseja excluir este veterinário e todos os registros vinculados? (S/N): ");
        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("S")) {
            clinicaService.excluirVeterinario(id);
            System.out.println("Veterinário e registros vinculados excluídos com sucesso!");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    private static void consultarVeterinario() {
        System.out.println("\n--- Consultar Veterinário ---");
        System.out.println("1. Consultar por ID");
        System.out.println("2. Consultar por Nome");
        System.out.println("3. Consultar por Especialidade");
        System.out.println("4. Listar todos os veterinários");
        System.out.print("Escolha uma opção: ");

        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        List<Veterinario> veterinarios; // Declaração única da variável

        switch (opcao) {
            case 1:
                System.out.print("ID do Veterinário: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer

                Veterinario veterinario = clinicaService.consultarVeterinario(id);
                exibirVeterinario(veterinario);
                break;
            case 2:
                System.out.print("Digite o nome do Veterinário: ");
                String nome = scanner.nextLine();

                veterinarios = clinicaService.consultarVeterinarioPorNome(nome);

                if (veterinarios.isEmpty()) {
                    System.out.println("Nenhum veterinário encontrado com esse nome.");
                } else {
                    System.out.println("\nVeterinários encontrados:");
                    for (Veterinario vet : veterinarios) {
                        exibirVeterinario(vet);
                    }
                }
                break;
            case 3:
                System.out.print("Digite a especialidade: ");
                String especialidade = scanner.nextLine();

                veterinarios = clinicaService.consultarVeterinarioPorEspecialidade(especialidade);

                if (veterinarios.isEmpty()) {
                    System.out.println("Nenhum veterinário encontrado com essa especialidade.");
                } else {
                    System.out.println("\nVeterinários encontrados:");
                    for (Veterinario vet : veterinarios) {
                        exibirVeterinario(vet);
                    }
                }
                break;
            case 4:
                veterinarios = clinicaService.listarTodosVeterinarios();
                if (veterinarios.isEmpty()) {
                    System.out.println("Nenhum veterinário cadastrado.");
                } else {
                    System.out.println("\nLista de todos os veterinários:");
                    for (Veterinario vet : veterinarios) {
                        exibirVeterinario(vet);
                    }
                }
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }

    private static void exibirVeterinario(Veterinario veterinario) {
        if (veterinario == null) {
            System.out.println("Veterinário não encontrado!");
            return;
        }

        System.out.println("\n--- Dados do Veterinário ---");
        System.out.println("ID: " + veterinario.getId());
        System.out.println("Nome: " + veterinario.getNome());
        System.out.println("Especialidade: " + veterinario.getEspecializacao());
        System.out.println("Telefone: " + veterinario.getTelefone());
        System.out.println("-----------------------------\n");
    }
    //Fim Veterinario
    
    
    
    // Consulta
    private static void exibirMenuConsulta() {
        int option;
        do {
            System.out.println("\n*** Menu Consulta ***");
            System.out.println("1 - Cadastrar Consulta");
            System.out.println("2 - Editar Consulta");
            System.out.println("3 - Excluir Consulta");
            System.out.println("4 - Consultar Consulta");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma operação: ");
            option = scanner.nextInt();
            scanner.nextLine();  // Clear buffer

            switch (option) {
                case 1:
                    cadastrarConsulta();
                    break;
                case 2:
                    editarConsulta();
                    break;
                case 3:
                    excluirConsulta();
                    break;
                case 4:
                    consultarConsulta();
                    break;
                case 5:
                  
                case 0:
                    return; // Volta para o menu principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);
    }

    private static void cadastrarConsulta() {
        System.out.print("Digite os detalhes da consulta...\n");

        // Detalhes da consulta
        System.out.print("Digite o ID do Pet: ");
        int petId = scanner.nextInt();
        scanner.nextLine();  // Limpar o buffer
        Pet pet = clinicaService.consultarPet(petId);

        // Verificar se o Pet existe
        if (pet == null) {
            System.out.println("Pet não encontrado. Tente novamente.");
            return;  // Encerra a função caso o pet não seja encontrado
        }

        System.out.print("Digite o ID do Veterinário: ");
        int veterinarioId = scanner.nextInt();
        scanner.nextLine();  // Limpar o buffer
        Veterinario veterinario = clinicaService.consultarVeterinario(veterinarioId);

        // Verificar se o Veterinário existe
        if (veterinario == null) {
            System.out.println("Veterinário não encontrado. Tente novamente.");
            return;  // Encerra a função caso o veterinário não seja encontrado
        }

        // Solicitar data e hora da consulta (formato: yyyy-MM-dd HH:mm)
        System.out.print("Digite a data e hora da consulta (formato: yyyy-MM-dd HH:mm): ");
        String dataHoraString = scanner.nextLine();

        // Converter a string para Timestamp
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        java.util.Date utilDate = null;
        try {
            utilDate = formatoData.parse(dataHoraString);  // Converte para Date utilitário
        } catch (ParseException e) {
            System.out.println("Formato de data e hora inválido.");
            return;
        }

        // Converte de util.Date para java.sql.Timestamp para persistir corretamente
        Timestamp timestampConsulta = new Timestamp(utilDate.getTime());

        // Verificar disponibilidade do veterinário para o horário solicitado usando clinicaService
        boolean isDisponivel = clinicaService.verificarDisponibilidade(veterinarioId, timestampConsulta);

        if (!isDisponivel) {
            System.out.println("O veterinário não está disponível para a consulta neste horário.");
            return;
        }
        
        System.out.print("Digite a descrição da consulta: ");
        String descricao = scanner.nextLine();

        // Cria a consulta
        Consulta consulta = new Consulta();
        consulta.setPet(pet);
        consulta.setVeterinario(veterinario);
        consulta.setDescricao(descricao);
        consulta.setDateTime(timestampConsulta);  // Atribuindo o Timestamp à consulta

        // Exibir todos os serviços disponíveis usando listarTodos()
        System.out.println("\n*** Serviços Disponíveis ***");
        List<Servico> servicosDisponiveis = clinicaService.listarTodosServicos();
        for (Servico servico : servicosDisponiveis) {
            exibirServico(servico);  // Exibir os detalhes do serviço com a função exibirServico
        }

        // Permitir ao usuário selecionar múltiplos serviços
        System.out.print("\nDigite os IDs dos serviços que você deseja adicionar (separados por vírgula): ");
        String[] idsServicos = scanner.nextLine().split(",");

        for (String idServico : idsServicos) {
            int id = Integer.parseInt(idServico.trim());
            Servico servico = clinicaService.buscarServicoPorId(id);
            if (servico != null) {
                consulta.addServico(servico);  // Adicionando os serviços selecionados à consulta
            }
        }

        // Registrar o valor praticado e a forma de pagamento diretamente na consulta
        System.out.print("Digite o valor praticado para a consulta: ");
        float valorPraticado = scanner.nextFloat();
        scanner.nextLine();  // Limpar o buffer
        System.out.print("Digite a forma de pagamento (ex: Pix, Cartão de Crédito): ");
        String formaPagamento = scanner.nextLine();

        // Atribuindo as informações de pagamento à consulta
        consulta.setValorPraticado(valorPraticado);
        consulta.setFormaPagamento(formaPagamento);

        // Salvar a consulta com os serviços, valor praticado e forma de pagamento
        clinicaService.salvarConsulta(consulta);

        System.out.println("Consulta cadastrada com sucesso com os serviços e informações de pagamento!");
        exibirConsulta(consulta.getId());
    }

    private static void editarConsulta() {
        System.out.print("Digite o ID da consulta para editar: ");
        int consultaId = scanner.nextInt();
        scanner.nextLine();  // Limpar o buffer

        // Buscar consulta
        Consulta consulta = clinicaService.consultarConsulta(consultaId);
        if (consulta != null) {
            exibirConsulta(consultaId);  // Exibe a consulta atual
            System.out.print("Digite o novo ID do pet (deixe em branco para manter o atual): ");
            String petIdInput = scanner.nextLine();

            if (!petIdInput.isEmpty()) {
                int petId = Integer.parseInt(petIdInput);
                Pet pet = clinicaService.consultarPet(petId);
                if (pet != null) {
                    consulta.setPet(pet);  // Atualiza o pet
                } else {
                    System.out.println("Pet não encontrado.");
                    return;
                }
            }

            System.out.print("Digite o novo ID do veterinário (deixe em branco para manter o atual): ");
            String veterinarioIdInput = scanner.nextLine();

            if (!veterinarioIdInput.isEmpty()) {
                int veterinarioId = Integer.parseInt(veterinarioIdInput);
                Veterinario veterinario = clinicaService.consultarVeterinario(veterinarioId);
                if (veterinario != null) {
                    // Solicitar data e hora da consulta (formato: yyyy-MM-dd HH:mm)
                    System.out.print("Digite a nova data e hora da consulta (formato: yyyy-MM-dd HH:mm): ");
                    String dataHoraString = scanner.nextLine();

                    // Converter a string para Timestamp
                    SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    java.util.Date utilDate = null;
                    try {
                        utilDate = formatoData.parse(dataHoraString);  // Converte para Date utilitário
                    } catch (ParseException e) {
                        System.out.println("Formato de data e hora inválido.");
                        return;
                    }

                    // Converte de util.Date para java.sql.Timestamp para persistir corretamente
                    Timestamp timestampConsulta = new Timestamp(utilDate.getTime());

                    // Verificar disponibilidade do veterinário para o horário solicitado usando clinicaService
                    boolean isDisponivel = clinicaService.verificarDisponibilidade(veterinarioId, timestampConsulta);

                    if (!isDisponivel) {
                        System.out.println("O veterinário não está disponível para a consulta neste horário.");
                        return;
                    }

                    consulta.setVeterinario(veterinario);  // Atualiza o veterinário
                    consulta.setDateTime(timestampConsulta);  // Atualiza a data e hora da consulta
                } else {
                    System.out.println("Veterinário não encontrado.");
                    return;
                }
            }

            // Solicitar novos serviços, se houver
            System.out.println("\n*** Serviços Disponíveis ***");
            List<Servico> servicosDisponiveis = clinicaService.listarTodosServicos();
            for (Servico servico : servicosDisponiveis) {
                exibirServico(servico);  // Exibir os detalhes do serviço
            }

            System.out.print("\nDigite os IDs dos serviços que você deseja adicionar (separados por vírgula) ou deixe em branco para manter os atuais: ");
            String servicosInput = scanner.nextLine();

            if (!servicosInput.isEmpty()) {
                String[] idsServicos = servicosInput.split(",");
                consulta.limparServicos();  // Limpar serviços anteriores (caso o usuário deseje alterar)
                for (String idServico : idsServicos) {
                    int id = Integer.parseInt(idServico.trim());
                    Servico servico = clinicaService.buscarServicoPorId(id);
                    if (servico != null) {
                        consulta.addServico(servico);  // Adicionando os serviços selecionados à consulta
                    } else {
                        System.out.println("Serviço com ID " + id + " não encontrado.");
                    }
                }
            }

            // Valor e forma de pagamento
            System.out.print("Digite o novo valor praticado para a consulta (deixe em branco para manter o atual): ");
            String valorPraticadoInput = scanner.nextLine();

            if (!valorPraticadoInput.isEmpty()) {
                float valorPraticado = Float.parseFloat(valorPraticadoInput);
                consulta.setValorPraticado(valorPraticado);  // Atualiza o valor
            }

            System.out.print("Digite a nova forma de pagamento (deixe em branco para manter o atual): ");
            String formaPagamentoInput = scanner.nextLine();

            if (!formaPagamentoInput.isEmpty()) {
                consulta.setFormaPagamento(formaPagamentoInput);  // Atualiza a forma de pagamento
            }

            // Salvar a consulta com as novas informações
            clinicaService.editarConsulta(consulta);
            System.out.println("Consulta editada com sucesso.");
            exibirConsulta(consultaId);
        } else {
            System.out.println("Consulta não encontrada.");
        }
    }

    private static void excluirConsulta() {
        System.out.print("Digite o ID da consulta para excluir: ");
        int consultaId = scanner.nextInt();
        scanner.nextLine();  // Limpa o buffer

        Consulta consulta = clinicaService.consultarConsulta(consultaId);
        if (consulta != null) {
            // Exibir detalhes da consulta
            exibirConsulta(consulta.getId());

            // Perguntar se o usuário realmente quer excluir a consulta
            System.out.print("Tem certeza que deseja excluir esta consulta? (s/n): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("s")) {
                // Excluir a consulta
                clinicaService.excluirConsulta(consulta);
                System.out.println("Consulta excluída com sucesso.");
            } else {
                System.out.println("Exclusão cancelada.");
            }
        } else {
            System.out.println("Consulta não encontrada.");
        }
    }

    private static void consultarConsulta() {
        System.out.println("\n*** Opções de Busca ***");
        System.out.println("1 - Buscar por ID da Consulta");
        System.out.println("2 - Buscar por ID do Pet");
        System.out.println("3 - Buscar por ID do Veterinário");
        System.out.println("4 - Buscar por Data (Intervalo)");
        System.out.println("5 - Buscar por Forma de Pagamento");
        System.out.println("6 - Buscar por Valor Praticado (Intervalo)");
        System.out.println("7 - Buscar por Serviço");
        System.out.println("8 - Buscar todas consultas");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        switch (opcao) {
            case 1:
                buscarPorIdConsulta();
                break;
            case 2:
                buscarPorIdPet();
                break;
            case 3:
                buscarPorIdVeterinario();
                break;
            case 4:
                buscarPorData();
                break;
            case 5:
                buscarPorFormaPagamento();
                break;
            case 6:
                buscarPorValor();
                break;
            case 7:
                buscarPorServico();
                break;
            case 8:
                buscarTodasConsultas();
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }

    private static void buscarPorIdConsulta() {
        System.out.print("Digite o ID da consulta: ");
        int consultaId = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        Consulta consulta = clinicaService.consultarConsulta(consultaId);
        if (consulta != null) {
            exibirConsulta(consulta.getId());
        } else {
            System.out.println("Consulta não encontrada.");
        }
    }

    private static void buscarPorIdPet() {
        System.out.print("Digite o ID do pet: ");
        int petId = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        List<Consulta> consultas = clinicaService.buscarConsultasPorIdPet(petId);
        if (!consultas.isEmpty()) {
            for (Consulta consulta : consultas) {
                exibirConsulta(consulta.getId());
            }
        } else {
            System.out.println("Nenhuma consulta encontrada para este pet.");
        }
    }
    
    private static void buscarPorIdVeterinario() {
        System.out.print("Digite o ID do veterinário: ");
        int veterinarioId = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        List<Consulta> consultas = clinicaService.buscarConsultasPorIdVeterinario(veterinarioId);
        if (!consultas.isEmpty()) {
            for (Consulta consulta : consultas) {
                exibirConsulta(consulta.getId());
            }
        } else {
            System.out.println("Nenhuma consulta encontrada para este veterinário.");
        }
    }

    private static void buscarPorData() {
        System.out.print("Digite a data inicial (dd/MM/yyyy): ");
        String dataInicialStr = scanner.nextLine();
        System.out.print("Digite a data final (dd/MM/yyyy): ");
        String dataFinalStr = scanner.nextLine();

        // Converter as strings para LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataInicial = LocalDate.parse(dataInicialStr, formatter);
        LocalDate dataFinal = LocalDate.parse(dataFinalStr, formatter);

        List<Consulta> consultas = clinicaService.buscarConsultasPorData(dataInicial, dataFinal);
        if (!consultas.isEmpty()) {
            for (Consulta consulta : consultas) {
                exibirConsulta(consulta.getId());
            }
        } else {
            System.out.println("Nenhuma consulta encontrada nesse intervalo de datas.");
        }
    }

    private static void buscarPorFormaPagamento() {
        System.out.print("Digite a forma de pagamento: ");
        String formaPagamento = scanner.nextLine();

        List<Consulta> consultas = clinicaService.buscarConsultasPorFormaPagamento(formaPagamento);
        if (!consultas.isEmpty()) {
            for (Consulta consulta : consultas) {
                exibirConsulta(consulta.getId());
            }
        } else {
            System.out.println("Nenhuma consulta encontrada para esta forma de pagamento.");
        }
    }

    private static void buscarPorValor() {
        System.out.print("Digite o valor mínimo: ");
        double valorMinimo = scanner.nextDouble();
        System.out.print("Digite o valor máximo: ");
        double valorMaximo = scanner.nextDouble();
        scanner.nextLine();  // Clear buffer

        List<Consulta> consultas = clinicaService.buscarConsultasPorValor(valorMinimo, valorMaximo);
        if (!consultas.isEmpty()) {
            for (Consulta consulta : consultas) {
                exibirConsulta(consulta.getId());
            }
        } else {
            System.out.println("Nenhuma consulta encontrada nesse intervalo de valor.");
        }
    }

    private static void buscarPorServico() {
        System.out.print("Digite o ID do serviço: ");
        int servicoId = scanner.nextInt();
        scanner.nextLine();  // Limpar o buffer

        List<Consulta> consultas = clinicaService.buscarConsultasPorServico(servicoId);
        if (!consultas.isEmpty()) {
            for (Consulta consulta : consultas) {
                exibirConsulta(consulta.getId());
            }
        } else {
            System.out.println("Nenhuma consulta encontrada para esse serviço.");
        }
    }

    private static void buscarTodasConsultas() {
        List<Consulta> consultas = clinicaService.buscarTodasConsultas();
        if (!consultas.isEmpty()) {
            for (Consulta consulta : consultas) {
                exibirConsulta(consulta.getId());
            }
        } else {
            System.out.println("Nenhuma consulta encontrada.");
        }
    }
    
    private static void exibirConsulta(int consultaId) {
        // Buscar consulta no banco de dados usando o ID
        Consulta consulta = clinicaService.consultarConsulta(consultaId);

        // Verificar se a consulta existe
        if (consulta != null) {
            System.out.println("\n*** Detalhes da Consulta ***");
            System.out.println("=========================================");
            System.out.println("ID da Consulta: " + consulta.getId());
            System.out.println("Pet: " + consulta.getPet().getNome());
            System.out.println("Veterinário: " + consulta.getVeterinario().getNome());
            System.out.println("Descrição: " + consulta.getDescricao());
            System.out.println("Data e Hora: " + consulta.getDateTime());
            System.out.println("Valor Praticado: R$ " + String.format("%.2f", consulta.getValorPraticado()));
            System.out.println("Forma de Pagamento: " + consulta.getFormaPagamento());
            System.out.println("=========================================");

            // Exibir os serviços relacionados
            System.out.println("\n*** Serviços Associados ***");
            if (consulta.getServicos() != null && !consulta.getServicos().isEmpty()) {
                System.out.println("=========================================");
                for (Servico servico : consulta.getServicos()) {
                    System.out.println("ID Servico: " + servico.getId());
                    System.out.println("Serviço: " + servico.getNome());
                    System.out.println("Descrição: " + servico.getDescricao());
                    System.out.println("Preço: R$ " + String.format("%.2f", servico.getPreco()));
                    System.out.println("-----------------------------------------");
                }
                System.out.println("=========================================");
            } else {
                System.out.println("Nenhum serviço associado.");
            }
        } else {
            System.out.println("Consulta não encontrada.");
        }
    }
    //Fim Consulta

    
    

}
