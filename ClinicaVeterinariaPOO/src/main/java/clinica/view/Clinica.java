package clinica.view;

import clinica.model.dao.ServicoDAO;
import clinica.model.entity.Cliente;
import clinica.model.entity.Consulta;
import clinica.model.entity.Pagamento;
import clinica.model.entity.Pet;
import clinica.model.entity.Produto;
import clinica.model.entity.Servico;
import clinica.model.entity.Venda;
import clinica.model.entity.Veterinario;
import clinica.model.service.ClinicaService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
                    exibirMenuVenda();
                    break;
                case 3:
                    exibirMenuPagamento();
                    break;
                case 4:
                    exibirMenuConsulta();
                    break;
                case 5:
                    exibirMenuServico();
                    break;
                case 6:
                    exibirMenuCliente();
                    break;
                case 7:
                    exibirMenuPet();
                    break;
                case 8:
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
        System.out.println("1 - Operações Produto");
        System.out.println("2 - Operações Venda");
        System.out.println("3 - Operações Pagamento");
        System.out.println("4 - Operações Consulta");
        System.out.println("5 - Operações Serviço");
        System.out.println("6 - Operações Cliente");
        System.out.println("7 - Operações Pet");
        System.out.println("8 - Operações Veterinário");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma classe: ");
    }

    private static void exibirMenuProduto() {
        int option;
        do {
            System.out.println("\n*** Menu Produto ***");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Editar Produto");
            System.out.println("3 - Excluir Produto");
            System.out.println("4 - Consultar Produto");
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
                case 0:
                    return; // Volta para o menu principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);
    }

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
            scanner.nextLine();  // Clear buffer

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

    private static void exibirMenuPagamento() {
        int option;
        do {
            System.out.println("\n*** Menu Pagamento ***");
            System.out.println("1 - Cadastrar Pagamento");
            System.out.println("2 - Editar Pagamento");
            System.out.println("3 - Excluir Pagamento");
            System.out.println("4 - Consultar Pagamento");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma operação: ");
            option = scanner.nextInt();
            scanner.nextLine();  // Clear buffer

            switch (option) {
                case 1:
                    cadastrarPagamento();
                    break;
                case 2:
                    editarPagamento();
                    break;
                case 3:
                    excluirPagamento();
                    break;
                case 4:
                    consultarPagamento();
                    break;
                case 0:
                    return; // Volta para o menu principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);
    }

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
                case 0:
                    return; // Volta para o menu principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);
    }

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


    // Produto
    private static void cadastrarProduto() {
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o valor do produto: ");
        float valor = scanner.nextFloat();
        scanner.nextLine();  // Clear buffer
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setPreco(valor);
        clinicaService.salvarProduto(produto);
        System.out.println("Produto cadastrado com sucesso.");
    }

    private static void editarProduto() {
        System.out.print("Digite o ID do produto a ser editado: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Clear buffer
        Produto produto = clinicaService.consultarProduto(id);
        if (produto != null) {
            System.out.print("Digite o novo nome do produto: ");
            produto.setNome(scanner.nextLine());
            System.out.print("Digite o novo valor do produto: ");
            produto.setPreco(scanner.nextFloat());
            scanner.nextLine();  // Clear buffer
            clinicaService.editarProduto(produto);
            System.out.println("Produto editado com sucesso.");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static void excluirProduto() {
        System.out.print("Digite o ID do produto a ser excluído: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Clear buffer
        Produto produto = clinicaService.consultarProduto(id);
        if (produto != null) {
            clinicaService.excluirProduto(produto);
            System.out.println("Produto excluído com sucesso.");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static void consultarProduto() {
        System.out.print("Digite o ID do produto a ser consultado: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Clear buffer
        Produto produto = clinicaService.consultarProduto(id);
        if (produto != null) {
            System.out.println("Produto encontrado: " + produto.getNome() + " - R$ " + produto.getPreco());
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    // Venda
    private static void cadastrarVenda() {
        System.out.print("Digite o ID do produto: ");
        int produtoId = scanner.nextInt();
        System.out.print("Digite o ID do pagamento: ");
        int pagamentoId = scanner.nextInt();
        System.out.print("Digite a quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        // Obtém a data atual para a venda
        Date dataVenda = new Date(); // Captura a data e hora atuais

        Venda venda = new Venda();
        venda.setProduto(clinicaService.consultarProduto(produtoId));
        venda.setQuantidade(quantidade);
        venda.setData(dataVenda); // Definindo a data da venda
        clinicaService.salvarVenda(venda);

        System.out.println("Venda cadastrada com sucesso.");
    }

    private static void editarVenda() {
        System.out.print("Digite o ID da venda a ser editada: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Clear buffer
        Venda venda = clinicaService.consultarVenda(id);
        if (venda != null) {
            System.out.print("Digite a nova quantidade: ");
            venda.setQuantidade(scanner.nextInt());
            scanner.nextLine();  // Clear buffer
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
            clinicaService.excluirVenda(venda);
            System.out.println("Venda excluída com sucesso.");
        } else {
            System.out.println("Venda não encontrada.");
        }
    }

    private static void consultarVenda() {
        System.out.print("Digite o ID da venda a ser consultada: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Clear buffer
        Venda venda = clinicaService.consultarVenda(id);
        if (venda != null) {
            System.out.println("Venda encontrada. Produto: " + venda.getProduto().getNome() + ", Quantidade: " + venda.getQuantidade());
        } else {
            System.out.println("Venda não encontrada.");
        }
    }

    // Pagamento
    private static void cadastrarPagamento() {
        System.out.print("Digite o ID da consulta: ");
        int consultaId = scanner.nextInt();
        System.out.print("Digite o valor do pagamento: ");
        float valor = scanner.nextFloat();
        scanner.nextLine();  // Clear buffer
        System.out.print("Digite a forma de pagamento: ");
        String formaPagamento = scanner.nextLine();
        System.out.print("Digite o status do pagamento: ");
        String status = scanner.nextLine();

        Pagamento pagamento = new Pagamento();
        pagamento.setConsulta(clinicaService.consultarConsulta(consultaId)); // Consulta associada ao pagamento
        pagamento.setValor(valor);
        pagamento.setFormaPagamento(formaPagamento);
        pagamento.setStatus(status);

        clinicaService.salvarPagamento(pagamento);
        System.out.println("Pagamento cadastrado com sucesso.");
    }

    private static void editarPagamento() {
        System.out.print("Digite o ID do pagamento para editar: ");
        int pagamentoId = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        // Buscar pagamento
        Pagamento pagamento = clinicaService.consultarPagamento(pagamentoId);
        if (pagamento != null) {
            System.out.print("Digite o novo valor do pagamento: ");
            float valor = scanner.nextFloat();
            scanner.nextLine();  // Clear buffer
            System.out.print("Digite a nova forma de pagamento: ");
            String formaPagamento = scanner.nextLine();
            System.out.print("Digite o novo status do pagamento: ");
            String status = scanner.nextLine();

            pagamento.setValor(valor);
            pagamento.setFormaPagamento(formaPagamento);
            pagamento.setStatus(status);

            clinicaService.editarPagamento(pagamento);
            System.out.println("Pagamento editado com sucesso.");
        } else {
            System.out.println("Pagamento não encontrado.");
        }
    }

    private static void excluirPagamento() {
        System.out.print("Digite o ID do pagamento para excluir: ");
        int pagamentoId = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        Pagamento pagamento = clinicaService.consultarPagamento(pagamentoId);
        if (pagamento != null) {
            clinicaService.excluirPagamento(pagamento);
            System.out.println("Pagamento excluído com sucesso.");
        } else {
            System.out.println("Pagamento não encontrado.");
        }
    }

    private static void consultarPagamento() {
        System.out.print("Digite o ID do pagamento para consultar: ");
        int pagamentoId = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        Pagamento pagamento = clinicaService.consultarPagamento(pagamentoId);
        if (pagamento != null) {
            System.out.println("Pagamento encontrado:");
            System.out.println("ID: " + pagamento.getId());
            System.out.println("Valor: " + pagamento.getValor());
            System.out.println("Forma de Pagamento: " + pagamento.getFormaPagamento());
            System.out.println("Status: " + pagamento.getStatus());
        } else {
            System.out.println("Pagamento não encontrado.");
        }
    }


    // Consulta
    private static void cadastrarConsulta() {
        System.out.print("Digite o ID do serviço: ");
        int servicoId = scanner.nextInt();
        System.out.print("Digite o ID do pet: ");
        int petId = scanner.nextInt();
        scanner.nextLine();  // Clear buffer
        System.out.print("Digite a data e hora da consulta (dd/MM/yyyy HH:mm): ");
        String dataConsultaStr = scanner.nextLine();

        // Converte a data string para LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataConsulta = null;
        try {
            dataConsulta = LocalDateTime.parse(dataConsultaStr, formatter);
        } catch (Exception e) {
            System.out.println("Formato de data e hora inválido!");
            return;
        }

        // Consultar Pet, Cliente (via Pet) e Serviço
        Pet pet = clinicaService.consultarPet(petId);
        Servico servico = clinicaService.consultarServico(servicoId);

        if (pet == null) {
            System.out.println("Pet não encontrado.");
            return;
        }
        if (servico == null) {
            System.out.println("Serviço não encontrado.");
            return;
        }

        // Criar e salvar consulta
        Consulta consulta = new Consulta();
        consulta.setPet(pet);  // Associando consulta ao pet (e indiretamente ao cliente)
        consulta.setDateTime(dataConsulta); // A data e hora da consulta
        consulta.addServico(servico); // Adicionando o serviço à consulta

        clinicaService.salvarConsulta(consulta);
        System.out.println("Consulta cadastrada com sucesso.");
    }

    private static void editarConsulta() {
        System.out.print("Digite o ID da consulta para editar: ");
        int consultaId = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        // Buscar consulta
        Consulta consulta = clinicaService.consultarConsulta(consultaId);
        if (consulta != null) {
            System.out.print("Digite o novo ID do serviço: ");
            int servicoId = scanner.nextInt();
            System.out.print("Digite o novo ID do pet: ");
            int petId = scanner.nextInt();
            scanner.nextLine();  // Clear buffer
            System.out.print("Digite a nova data e hora da consulta (dd/MM/yyyy HH:mm): ");
            String dataConsultaStr = scanner.nextLine();

            // Converte a nova data para LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime dataConsulta = null;
            try {
                dataConsulta = LocalDateTime.parse(dataConsultaStr, formatter);
            } catch (Exception e) {
                System.out.println("Formato de data e hora inválido!");
                return;
            }

            // Consultar Pet, Cliente (via Pet) e Serviço
            Pet pet = clinicaService.consultarPet(petId);
            Servico servico = clinicaService.consultarServico(servicoId);

            if (pet == null) {
                System.out.println("Pet não encontrado.");
                return;
            }
            if (servico == null) {
                System.out.println("Serviço não encontrado.");
                return;
            }

            consulta.setPet(pet);  // Associando a consulta ao novo pet
            consulta.setDateTime(dataConsulta);  // Atualizando a data e hora
            consulta.addServico(servico);  // Adicionando o novo serviço à consulta

            clinicaService.editarConsulta(consulta);
            System.out.println("Consulta editada com sucesso.");
        } else {
            System.out.println("Consulta não encontrada.");
        }
    }

    private static void excluirConsulta() {
        System.out.print("Digite o ID da consulta para excluir: ");
        int consultaId = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        Consulta consulta = clinicaService.consultarConsulta(consultaId);
        if (consulta != null) {
            clinicaService.excluirConsulta(consulta);
            System.out.println("Consulta excluída com sucesso.");
        } else {
            System.out.println("Consulta não encontrada.");
        }
    }

    private static void consultarConsulta() {
        System.out.print("Digite o ID da consulta para consultar: ");
        int consultaId = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        Consulta consulta = clinicaService.consultarConsulta(consultaId);
        if (consulta != null) {
            System.out.println("Consulta encontrada:");
            System.out.println("ID: " + consulta.getId());
            System.out.println("Pet: " + consulta.getPet().getNome());
            System.out.println("Cliente: " + consulta.getPet().getCliente().getNome());
            System.out.println("Serviços: ");
            for (Servico servico : consulta.getServicos()) {
                System.out.println(" - " + servico.getNome());
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            System.out.println("Data: " + formatter.format(consulta.getDateTime())); // Exibe a data e hora formatada
        } else {
            System.out.println("Consulta não encontrada.");
        }
    }
    // Cadastrar Serviço
    private static void cadastrarServico() {
        System.out.print("Digite o nome do serviço: ");
        String nome = scanner.nextLine();
        System.out.print("Digite a descrição do serviço: ");
        String descricao = scanner.nextLine();
        System.out.print("Digite o preço do serviço: ");
        float preco = scanner.nextFloat();
        scanner.nextLine();  // Clear buffer

        Servico servico = new Servico();
        servico.setNome(nome);
        servico.setDescricao(descricao);
        servico.setPreco(preco);

        clinicaService.salvarServico(servico);
        System.out.println("Serviço cadastrado com sucesso!");
    }

    // Editar Serviço
    private static void editarServico() {
        System.out.print("Digite o ID do serviço que deseja editar: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        Servico servico = clinicaService.consultarServico(id);
        if (servico == null) {
            System.out.println("Serviço não encontrado.");
            return;
        }

        System.out.print("Digite o novo nome do serviço (atual: " + servico.getNome() + "): ");
        String nome = scanner.nextLine();
        System.out.print("Digite a nova descrição do serviço (atual: " + servico.getDescricao() + "): ");
        String descricao = scanner.nextLine();
        System.out.print("Digite o novo preço do serviço (atual: " + servico.getPreco() + "): ");
        float preco = scanner.nextFloat();
        scanner.nextLine();  // Clear buffer

        servico.setNome(nome);
        servico.setDescricao(descricao);
        servico.setPreco(preco);

        clinicaService.editarServico(servico);
        System.out.println("Serviço editado com sucesso!");
    }

    // Excluir Serviço
    private static void excluirServico() {
        System.out.print("Digite o ID do serviço que deseja excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        clinicaService.excluirServico(id);
        System.out.println("Serviço excluído com sucesso!");
    }

    // Consultar Serviço
    private static void consultarServico() {
        System.out.print("Digite o ID do serviço que deseja consultar: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        Servico servico = clinicaService.consultarServico(id);
        if (servico == null) {
            System.out.println("Serviço não encontrado.");
            return;
        }

        System.out.println("ID: " + servico.getId());
        System.out.println("Nome: " + servico.getNome());
        System.out.println("Descrição: " + servico.getDescricao());
        System.out.println("Preço: " + servico.getPreco());
    }
    // Métodos de operação para Cliente
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

        clinicaService.excluirCliente(id);
        System.out.println("Cliente excluído com sucesso!");
    }

    private static void consultarCliente() {
        System.out.print("ID do Cliente para consulta: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        Cliente cliente = clinicaService.consultarCliente(id);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.println("Cliente encontrado: ");
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("Telefone: " + cliente.getTelefone());
        System.out.println("Email: " + cliente.getEmail());
    }

    // Métodos de operação para Pet
    private static void cadastrarPet() {
        System.out.print("Nome do Pet: ");
        String nome = scanner.nextLine();
        System.out.print("Espécie do Pet: ");
        String especie = scanner.nextLine();
        System.out.print("Raça do Pet: ");
        String raca = scanner.nextLine();

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
        pet.setCliente(cliente);                      
        clinicaService.salvarPet(pet);
        System.out.println("Pet cadastrado com sucesso!");
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

        pet.setNome(nome);
        pet.setEspecie(especie);
        pet.setRaca(raca);
        clinicaService.editarPet(pet);
        System.out.println("Pet editado com sucesso!");
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

        clinicaService.excluirPet(id);
        System.out.println("Pet excluído com sucesso!");
    }

    private static void consultarPet() {
        System.out.print("ID do Pet para consulta: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        Pet pet = clinicaService.consultarPet(id);
        if (pet == null) {
            System.out.println("Pet não encontrado!");
            return;
        }

        System.out.println("Pet encontrado: ");
        System.out.println("Nome: " + pet.getNome());
        System.out.println("Espécie: " + pet.getEspecie());
        System.out.println("Raça: " + pet.getRaca());
        System.out.println("Dono: " + pet.getCliente().getNome());
    }

    // Métodos de operação para Veterinário
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
    }

    private static void editarVeterinario() {
        System.out.print("ID do Veterinário para edição: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

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
    }

    private static void excluirVeterinario() {
        System.out.print("ID do Veterinário para exclusão: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        Veterinario veterinario = clinicaService.consultarVeterinario(id);
        if (veterinario == null) {
            System.out.println("Veterinário não encontrado!");
            return;
        }

        clinicaService.excluirVeterinario(id);
        System.out.println("Veterinário excluído com sucesso!");
    }

    private static void consultarVeterinario() {
        System.out.print("ID do Veterinário para consulta: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        Veterinario veterinario = clinicaService.consultarVeterinario(id);
        if (veterinario == null) {
            System.out.println("Veterinário não encontrado!");
            return;
        }

        System.out.println("Veterinário encontrado: ");
        System.out.println("Nome: " + veterinario.getNome());
        System.out.println("Especialidade: " + veterinario.getEspecializacao());
        System.out.println("Telefone: " + veterinario.getTelefone());
    }



}
