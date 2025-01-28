package clinica.model.service;

import clinica.model.dao.ProdutoDAO;
import clinica.model.dao.VendaDAO;
import clinica.model.dao.PagamentoDAO;
import clinica.model.dao.ConsultaDAO;
import clinica.model.dao.ServicoDAO;
import clinica.model.dao.ClienteDAO;
import clinica.model.dao.PetDAO;  // Importando o DAO do Pet
import clinica.model.dao.VeterinarioDAO;
import clinica.model.entity.Produto;
import clinica.model.entity.Venda;
import clinica.model.entity.Pagamento;
import clinica.model.entity.Consulta;
import clinica.model.entity.Servico;
import clinica.model.entity.Cliente;
import clinica.model.entity.Pet;  // Classe Pet
import clinica.model.entity.Veterinario;

public class ClinicaService {
    private ProdutoDAO produtoDAO;
    private VendaDAO vendaDAO;
    private PagamentoDAO pagamentoDAO;
    private ConsultaDAO consultaDAO;
    private ServicoDAO servicoDAO;
    private ClienteDAO clienteDAO;
    private PetDAO petDAO;  // Inicializando o PetDAO
    private VeterinarioDAO veterinarioDAO;
    
    public ClinicaService() {
        this.produtoDAO = new ProdutoDAO();
        this.vendaDAO = new VendaDAO();
        this.pagamentoDAO = new PagamentoDAO();
        this.consultaDAO = new ConsultaDAO();
        this.servicoDAO = new ServicoDAO();
        this.clienteDAO = new ClienteDAO();
        this.petDAO = new PetDAO();  // Inicializando o PetDAO
        this.veterinarioDAO = new VeterinarioDAO();
    }

    // Produto
    public void salvarProduto(Produto produto) {
        produtoDAO.create(produto);
    }

    public void editarProduto(Produto produto) {
        produtoDAO.update(produto);
    }

    public void excluirProduto(Produto produto) {
        produtoDAO.delete(produto.getId());
    }

    public Produto consultarProduto(int id) {
        return produtoDAO.find(id);
    }

    // Venda
    public void salvarVenda(Venda venda) {
        vendaDAO.create(venda);
    }

    public void editarVenda(Venda venda) {
        vendaDAO.update(venda);
    }

    public void excluirVenda(Venda venda) {
        vendaDAO.delete(venda.getId());
    }

    public Venda consultarVenda(int id) {
        return vendaDAO.find(id);
    }

    // Pagamento
    public void salvarPagamento(Pagamento pagamento) {
        pagamentoDAO.create(pagamento);
    }

    public void editarPagamento(Pagamento pagamento) {
        pagamentoDAO.update(pagamento);
    }

    public void excluirPagamento(Pagamento pagamento) {
        pagamentoDAO.delete(pagamento.getId());
    }

    public Pagamento consultarPagamento(int id) {
        return pagamentoDAO.find(id);
    }

    // Consulta
    public void salvarConsulta(Consulta consulta) {
        consultaDAO.create(consulta);
    }

    public void editarConsulta(Consulta consulta) {
        consultaDAO.update(consulta);
    }

    public void excluirConsulta(Consulta consulta) {
        consultaDAO.delete(consulta.getId());
    }

    public Consulta consultarConsulta(int id) {
        return consultaDAO.find(id);
    }

    // Associa serviços à consulta (usando addServico)
    public void adicionarServicoNaConsulta(Consulta consulta, Servico servico) {
        consulta.addServico(servico);  // Método da consulta para adicionar um serviço
        consultaDAO.update(consulta);  // Atualiza a consulta com os novos serviços
    }
     // Salvar Cliente
    public void salvarCliente(Cliente cliente) {
        clienteDAO.create(cliente);
    }

    // Editar Cliente
    public void editarCliente(Cliente cliente) {
        clienteDAO.update(cliente);
    }

    // Excluir Cliente
    public void excluirCliente(int id) {
        clienteDAO.delete(id);
    }

    // Consultar Cliente por ID
    public Cliente consultarCliente(int id) {
        return clienteDAO.find(id);
    }
    
    // Serviço
    public Servico consultarServico(int id) {
        return servicoDAO.find(id);
    }
    
    // Salvar Serviço
    public void salvarServico(Servico servico) {
        servicoDAO.create(servico);
    }

    // Editar Serviço
    public void editarServico(Servico servico) {
        servicoDAO.update(servico);
    }

    // Excluir Serviço
    public void excluirServico(int id) {
        servicoDAO.delete(id);
    }
    
    // Pet
    public Pet consultarPet(int id) {
        return petDAO.find(id);  // Método para consultar o Pet associado ao Cliente
    }
    // Salvar Pet
    public void salvarPet(Pet pet) {
        petDAO.create(pet);
    }

    // Editar Pet
    public void editarPet(Pet pet) {
        petDAO.update(pet);
    }

    // Excluir Pet
    public void excluirPet(int id) {
        petDAO.delete(id);
    }
    
    // Veterinário
    public void salvarVeterinario(Veterinario veterinario) {
        veterinarioDAO.create(veterinario);
    }

    public void editarVeterinario(Veterinario veterinario) {
        veterinarioDAO.update(veterinario);
    }

    public void excluirVeterinario(int id) {
        veterinarioDAO.delete(id);
    }

    public Veterinario consultarVeterinario(int id) {
        return veterinarioDAO.find(id);
    }
    
    
    
}
