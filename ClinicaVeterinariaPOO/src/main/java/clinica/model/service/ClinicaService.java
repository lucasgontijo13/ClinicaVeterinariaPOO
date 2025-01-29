package clinica.model.service;

import clinica.model.dao.ProdutoDAO;
import clinica.model.dao.VendaDAO;
import clinica.model.dao.ConsultaDAO;
import clinica.model.dao.ServicoDAO;
import clinica.model.dao.ClienteDAO;
import clinica.model.dao.PetDAO;  // Importando o DAO do Pet
import clinica.model.dao.VeterinarioDAO;
import clinica.model.entity.Produto;
import clinica.model.entity.Venda;
import clinica.model.entity.Consulta;
import clinica.model.entity.Servico;
import clinica.model.entity.Cliente;
import clinica.model.entity.Pet;  // Classe Pet
import clinica.model.entity.Veterinario;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class ClinicaService {
    private ProdutoDAO produtoDAO;
    private VendaDAO vendaDAO;
    private ConsultaDAO consultaDAO;
    private ServicoDAO servicoDAO;
    private ClienteDAO clienteDAO;
    private PetDAO petDAO;  // Inicializando o PetDAO
    private VeterinarioDAO veterinarioDAO;
    
    
    public ClinicaService() {
        this.produtoDAO = new ProdutoDAO();
        this.vendaDAO = new VendaDAO();
        this.consultaDAO = new ConsultaDAO();
        this.clienteDAO = new ClienteDAO();
        this.petDAO = new PetDAO();  
        this.veterinarioDAO = new VeterinarioDAO();
        this.servicoDAO = new ServicoDAO();
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
    
    public List<Produto> consultarProduto(String nome) {
        return produtoDAO.findByName(nome);
    }
    
    public List<Produto> listarTodosProdutos() {
        return produtoDAO.findAll();
    }
    
    public boolean atualizarPrecoProduto(int id, float novoPreco) {
        return produtoDAO.atualizarPreco(id, novoPreco);
    }

    public boolean atualizarEstoqueProduto(int id, int novoEstoque) {
        return produtoDAO.atualizarEstoque(id, novoEstoque);
    }
    //Fim Produto
    
    
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
        return vendaDAO.consultarVenda(id);
    }

    public List<Venda> consultarVendasEntreDatas(String dataInicialStr, String dataFinalStr) {
        return vendaDAO.consultarVendasEntreDatas(dataInicialStr, dataFinalStr);
    }

    public List<Venda> consultarVendasEntreValores(double valorMinimo, double valorMaximo) {
        return vendaDAO.consultarVendasEntreValores(valorMinimo, valorMaximo);
    }
    
    public List<Venda> consultarVendasPorMetodoPagamento(String metodoPagamento) {
        return vendaDAO.consultarVendasPorMetodoPagamento(metodoPagamento);
    }
    
    public List<Venda> consultarVendasPorProdutoId(int produtoId) {
        return vendaDAO.consultarVendasPorProduto(produtoId);
    }

    public List<Venda> consultarTodasVendas() {
        return vendaDAO.findAll();
    }
    //Fim venda
    
    
    //Cliente
    public void salvarCliente(Cliente cliente) {
        clienteDAO.create(cliente);
    }
   
    public void editarCliente(Cliente cliente) {
        clienteDAO.update(cliente);
    }

    public void excluirCliente(int id) {
        clienteDAO.delete(id);
    }

    public Cliente consultarCliente(int id) {
        return clienteDAO.find(id);
    }
    
    public List<Cliente> consultarCliente(String nome) {
        return clienteDAO.findByName(nome);
    }
    
    public List<Cliente> listarTodosClientes() {
        return clienteDAO.findAll();
    }
    //Fim Cliente
    
    
    // Serviço
    public void cadastrarServico(Servico servico) {
        servicoDAO.salvar(servico);
    }

    public void editarServico(Servico servico) {
        servicoDAO.atualizar(servico);
    }

    public void excluirServico(int id) {
        servicoDAO.excluir(id);
    }

    public Servico buscarServicoPorId(int id) {
        return servicoDAO.buscarPorId(id);
    }

    public List<Servico> buscarServicoPorNome(String nome) {
        return servicoDAO.buscarPorNome(nome);
    }
   
    public List<Servico> buscarServicoPorFaixaDePreco(float precoMinimo, float precoMaximo) {
        return servicoDAO.buscarPorFaixaDePreco(precoMinimo, precoMaximo);
    }
 
    public List<Servico> listarTodosServicos() {
        return servicoDAO.listarTodos();
    }
    //Fim servico
    
    
    // Pet
    public void salvarPet(Pet pet) {
        petDAO.create(pet);
    }

    public void editarPet(Pet pet) {
        petDAO.update(pet);
    }

    public void excluirPet(int id) {
        petDAO.delete(id);
    }
    
    public Pet consultarPet(int id) {
        return petDAO.find(id);  
    }
    
    public List<Pet> consultarPetsPorRaca(String raca) {
        return petDAO.findByRaca(raca);
    }

    public List<Pet> consultarPetsPorEspecie(String especie) {
        return petDAO.findByEspecie(especie);
    }

    public List<Pet> consultarPetsPorIdade(int idade) {
        return petDAO.findByIdade(idade);
    }

    public List<Pet> consultarPetsPorDono(int clienteId) {
        return petDAO.findByDono(clienteId);
    }

    public List<Pet> consultarTodosPets() {
        return petDAO.findAll();
    }
    //Fim pet
    
    
    
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
    
    public List<Veterinario> consultarVeterinarioPorNome(String nome) {
        return veterinarioDAO.buscarPorNome(nome);
    }

    public List<Veterinario> consultarVeterinarioPorEspecialidade(String especialidade) {
        return veterinarioDAO.buscarPorEspecialidade(especialidade);
    }

    public List<Veterinario> listarTodosVeterinarios() {
        return veterinarioDAO.findAll();
    }
    //Fim veterinario
    
    
    
    
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

    public List<Consulta> buscarTodasConsultas() {
        return consultaDAO.buscarTodasConsultas();
    }
    
    public List<Consulta> buscarConsultasPorIdPet(int petId) {
        return consultaDAO.buscarConsultasPorIdPet(petId);
    }
    
    public List<Consulta> buscarConsultasPorIdVeterinario(int veterinarioId) {
        return consultaDAO.buscarConsultasPorIdVeterinario(veterinarioId);
    }
    
    public List<Consulta> buscarConsultasPorData(LocalDate dataInicial, LocalDate dataFinal) {
        return consultaDAO.buscarConsultasPorData(dataInicial, dataFinal);
    }
    
    public List<Consulta> buscarConsultasPorFormaPagamento(String formaPagamento) {
        return consultaDAO.buscarConsultasPorFormaPagamento(formaPagamento);
    }

    public List<Consulta> buscarConsultasPorValor(double valorMinimo, double valorMaximo) {
        return consultaDAO.buscarConsultasPorValor(valorMinimo, valorMaximo);
    }

    public List<Consulta> buscarConsultasPorServico(int servicoId) {
        return consultaDAO.buscarConsultasPorServico(servicoId);
    }
    
    public boolean verificarDisponibilidade(int veterinarioId, Timestamp dataHoraConsulta) {
        return consultaDAO.verificarDisponibilidade(veterinarioId, dataHoraConsulta);
    }
    // Fim Consulta
    
    
    //Metodo para verificar se ao excluir algum tabela tem vinculo relacionado
    public boolean temRegistrosRelacionados(int id, String tabela) {
        switch (tabela.toLowerCase()) {
            case "veterinario":
                return consultaDAO.existeConsultaParaVeterinario(id);
            case "produto":
                return produtoDAO.existeVendaParaProduto(id);
            case "cliente":
                return clienteDAO.existeRegistroParaCliente(id);
            case "pet":
                return petDAO.existeConsultaParaPet(id);
            default:
                throw new IllegalArgumentException("Tabela não reconhecida");
        }
    }
    
    
}
