package clinica.model.dao;

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
        String sql = "INSERT INTO Venda (data, produto_id, quantidade, forma_pagamento) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setTimestamp(1, new Timestamp(venda.getData().getTime()));
            pst.setInt(2, venda.getProduto().getId());
            pst.setInt(3, venda.getQuantidade());
            pst.setString(4, venda.getFormaPagamento());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Update
    public void update(Venda venda) {
        String sql = "UPDATE Venda SET data = ?, produto_id = ?, quantidade = ?, forma_pagamento = ? WHERE id = ?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setTimestamp(1, new Timestamp(venda.getData().getTime()));
            pst.setInt(2, venda.getProduto().getId());
            pst.setInt(3, venda.getQuantidade());
            pst.setString(4, venda.getFormaPagamento());
            pst.setInt(5, venda.getId());
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

    // Find by ID
    public Venda find(int id) {
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
                venda.setFormaPagamento(rs.getString("forma_pagamento"));  // Recuperando a forma de pagamento
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return venda;
    }

    // Find all
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
                venda.setFormaPagamento(rs.getString("forma_pagamento"));  // Recuperando a forma de pagamento
                vendas.add(venda);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vendas;
    }
}
