package clinica.model.dao;

import clinica.model.entity.Cliente;
import clinica.model.entity.Venda;
import clinica.model.entity.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {
    private Conexao conexao;

    public VendaDAO() {
        this.conexao = new Conexao();
    }

    // Create
    public void create(Venda venda) {
        String sql = "INSERT INTO Venda (cliente_id, produto_id, quantidade, data, forma_pagamento, valor_praticado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Modificado aqui
            pst.setInt(1, venda.getCliente().getId());
            pst.setInt(2, venda.getProduto().getId());
            pst.setInt(3, venda.getQuantidade());
            pst.setTimestamp(4, new Timestamp(venda.getData().getTime()));
            pst.setString(5, venda.getFormaPagamento());
            pst.setDouble(6, venda.getValorPraticado());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    venda.setId(rs.getInt(1)); // Atribui o ID gerado
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Update
    public void update(Venda venda) {
        String sql = "UPDATE Venda SET data = ?, produto_id = ?, quantidade = ?, forma_pagamento = ?, valor_praticado = ? WHERE id = ?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setTimestamp(1, new Timestamp(venda.getData().getTime()));
            pst.setInt(2, venda.getProduto().getId());
            pst.setInt(3, venda.getQuantidade());
            pst.setString(4, venda.getFormaPagamento());
            pst.setDouble(5, venda.getValorPraticado()); // Atualizando o valor praticado
            pst.setInt(6, venda.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Delete
    public void delete(int id) {
        String sql = "DELETE FROM Venda WHERE id = ?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Consulta por ID
    public Venda consultarVenda(int id) {
        String sql = "SELECT * FROM Venda WHERE id = ?";
        Venda venda = null;
        
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setData(rs.getTimestamp("data"));
                Produto produto = new ProdutoDAO().find(rs.getInt("produto_id"));
                venda.setProduto(produto);
                venda.setQuantidade(rs.getInt("quantidade"));
                venda.setFormaPagamento(rs.getString("forma_pagamento"));
                venda.setValorPraticado(rs.getDouble("valor_praticado"));
                int clienteId = rs.getInt("cliente_id");
                Cliente cliente = new ClienteDAO().find(clienteId);
                venda.setCliente(cliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return venda;
    }

    // Consulta entre datas 
    public List<Venda> consultarVendasEntreDatas(String dataInicialStr, String dataFinalStr) {
        String sql = "SELECT * FROM Venda WHERE data >= ? AND data <= ?";
        List<Venda> vendas = new ArrayList<>();

        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            java.sql.Date dataInicial = java.sql.Date.valueOf(dataInicialStr);
            java.sql.Date dataFinal = java.sql.Date.valueOf(dataFinalStr);

            // Ajustando a data final para o último momento do dia
            Timestamp dataFinalComHora = new Timestamp(dataFinal.getTime() + 86400000 - 1); // 86400000 = 24 horas em milissegundos

            pst.setDate(1, dataInicial);  // Define a data inicial (maior ou igual)
            pst.setTimestamp(2, dataFinalComHora); // Define a data final com hora ajustada (menor ou igual)

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setData(rs.getTimestamp("data"));
                Produto produto = new ProdutoDAO().find(rs.getInt("produto_id"));
                venda.setProduto(produto);
                venda.setQuantidade(rs.getInt("quantidade"));
                venda.setValorPraticado(rs.getDouble("valor_praticado"));
                venda.setFormaPagamento(rs.getString("forma_pagamento"));
                int clienteId = rs.getInt("cliente_id");
                Cliente cliente = new ClienteDAO().find(clienteId);
                venda.setCliente(cliente);
                vendas.add(venda);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vendas;
    }


    // Consulta entre valores
    public List<Venda> consultarVendasEntreValores(double valorMinimo, double valorMaximo) {
        String sql = "SELECT * FROM Venda WHERE valor_praticado BETWEEN ? AND ?";
        List<Venda> vendas = new ArrayList<>();
        
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDouble(1, valorMinimo);
            pst.setDouble(2, valorMaximo);
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setData(rs.getTimestamp("data"));
                Produto produto = new ProdutoDAO().find(rs.getInt("produto_id"));
                venda.setProduto(produto);
                venda.setQuantidade(rs.getInt("quantidade"));
                venda.setValorPraticado(rs.getDouble("valor_praticado"));
                venda.setFormaPagamento(rs.getString("forma_pagamento"));
                int clienteId = rs.getInt("cliente_id");
                Cliente cliente = new ClienteDAO().find(clienteId);
                venda.setCliente(cliente);
                vendas.add(venda);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vendas;
    }


    // Consulta por Método de Pagamento com busca parcial
    public List<Venda> consultarVendasPorMetodoPagamento(String formaPagamento) {
        String sql = "SELECT * FROM Venda WHERE forma_pagamento LIKE ?";
        List<Venda> vendas = new ArrayList<>();

        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            // Adiciona os coringas (%) antes e depois do valor da pesquisa para permitir busca parcial
            pst.setString(1, "%" + formaPagamento + "%");

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setData(rs.getTimestamp("data"));
                Produto produto = new ProdutoDAO().find(rs.getInt("produto_id"));
                venda.setProduto(produto);
                venda.setQuantidade(rs.getInt("quantidade"));
                venda.setValorPraticado(rs.getDouble("valor_praticado"));
                venda.setFormaPagamento(rs.getString("forma_pagamento"));
                int clienteId = rs.getInt("cliente_id");
                Cliente cliente = new ClienteDAO().find(clienteId);
                venda.setCliente(cliente);
                vendas.add(venda);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vendas;
    }


    // Consulta por ID de Produto
    public List<Venda> consultarVendasPorProduto(int produtoId) {
        String sql = "SELECT * FROM Venda WHERE produto_id = ?";
        List<Venda> vendas = new ArrayList<>();

        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, produtoId);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setData(rs.getTimestamp("data"));
                Produto produto = new ProdutoDAO().find(rs.getInt("produto_id"));
                venda.setProduto(produto);
                venda.setQuantidade(rs.getInt("quantidade"));
                venda.setValorPraticado(rs.getDouble("valor_praticado"));
                venda.setFormaPagamento(rs.getString("forma_pagamento"));
                int clienteId = rs.getInt("cliente_id");
                Cliente cliente = new ClienteDAO().find(clienteId);
                venda.setCliente(cliente);
                vendas.add(venda);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vendas;
    }

    // Consulta para Mostrar Todas as Vendas
    public List<Venda> findAll() {
        String sql = "SELECT * FROM Venda";
        List<Venda> vendas = new ArrayList<>();

        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setData(rs.getTimestamp("data"));
                Produto produto = new ProdutoDAO().find(rs.getInt("produto_id"));
                venda.setProduto(produto);
                venda.setQuantidade(rs.getInt("quantidade"));
                venda.setValorPraticado(rs.getDouble("valor_praticado"));
                venda.setFormaPagamento(rs.getString("forma_pagamento"));
                int clienteId = rs.getInt("cliente_id");
                Cliente cliente = new ClienteDAO().find(clienteId);
                venda.setCliente(cliente);
                vendas.add(venda);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vendas;
    }
    
    

}