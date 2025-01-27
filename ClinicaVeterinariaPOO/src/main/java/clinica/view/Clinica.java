package clinica.view;

import clinica.model.entity.Consulta;
import clinica.model.entity.Pagamento;
import clinica.model.entity.Pet;
import clinica.model.entity.Produto;
import clinica.model.entity.Servico;
import clinica.model.entity.Venda;
import clinica.model.service.ClinicaService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Scanner;

public class Clinica {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClinicaService clinicaService = new ClinicaService();

    public static void main(String[] args) {
        int option;
        do {
            exibirMenu();
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
                case 5:
                    cadastrarVenda();
                    break;
                case 6:
                    editarVenda();
                    break;
                case 7:
                    excluirVenda();
                    break;
                case 8:
                    consultarVenda();
                    break;
                case 9:
                    cadastrarPagamento();
                    break;
                case 10:
                    editarPagamento();
                    break;
                case 11:
                    excluirPagamento();
                    break;
                case 12:
                    consultarPagamento();
                    break;
                case 13:
                    cadastrarConsulta();
                    break;
                case 14:
                    editarConsulta();
                    break;
                case 15:
                    excluirConsulta();
                    break;
                case 16:
                    consultarConsulta();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);
    }

    private static void exibirMenu() {
        System.out.println("\n*** Menu da Clínica ***");
        System.out.println("1 - Cadastrar Produto");
        System.out.println("2 - Editar Produto");
        System.out.println("3 - Excluir Produto");
        System.out.println("4 - Consultar Produto");
        System.out.println("5 - Cadastrar Venda");
        System.out.println("6 - Editar Venda");
        System.out.println("7 - Excluir Venda");
        System.out.println("8 - Consultar Venda");
        System.out.println("9 - Cadastrar Pagamento");
        System.out.println("10 - Editar Pagamento");
        System.out.println("11 - Excluir Pagamento");
        System.out.println("12 - Consultar Pagamento");
        System.out.println("13 - Cadastrar Consulta");
        System.out.println("14 - Editar Consulta");
        System.out.println("15 - Excluir Consulta");
        System.out.println("16 - Consultar Consulta");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
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
        System.out.print("Digite a data da consulta (dd/MM/yyyy): ");
        String dataConsultaStr = scanner.nextLine();

        // Converte a data string para Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dataConsulta = null;
        try {
            dataConsulta = dateFormat.parse(dataConsultaStr);
        } catch (ParseException e) {
            System.out.println("Formato de data inválido!");
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
        consulta.setData(dataConsulta); // A data da consulta
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
            System.out.print("Digite a nova data da consulta (dd/MM/yyyy): ");
            String dataConsultaStr = scanner.nextLine();

            // Converte a nova data para Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dataConsulta = null;
            try {
                dataConsulta = dateFormat.parse(dataConsultaStr);
            } catch (ParseException e) {
                System.out.println("Formato de data inválido!");
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
            consulta.setData(dataConsulta);  // Atualizando a data
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("Data: " + dateFormat.format(consulta.getData())); // Exibe a data formatada
        } else {
            System.out.println("Consulta não encontrada.");
        }
    }


}
