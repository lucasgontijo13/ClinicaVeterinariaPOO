package clinica.model.dao;

import clinica.model.entity.Pagamento;
import clinica.model.entity.Consulta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {
    private Conexao conexao;
    private Connection connection;

    public PagamentoDAO() {
        this.conexao = new Conexao();
        this.connection = conexao.getConnection();  // Conexão estabelecida uma vez aqui
    }

    // Create
    public void create(Pagamento pagamento) {
        String sql = "INSERT INTO Pagamento (valor, data_pagamento, consulta_id, forma_pagamento, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setFloat(1, pagamento.getValor());
            pst.setTimestamp(2, new Timestamp(pagamento.getDataPagamento().getTime())); // Agora com a data de pagamento
            pst.setInt(3, pagamento.getConsulta().getId());
            pst.setString(4, pagamento.getFormaPagamento());
            pst.setString(5, pagamento.getStatus());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Update
    public void update(Pagamento pagamento) {
        String sql = "UPDATE Pagamento SET valor = ?, data_pagamento = ?, consulta_id = ?, forma_pagamento = ?, status = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setFloat(1, pagamento.getValor());
            pst.setTimestamp(2, new Timestamp(pagamento.getDataPagamento().getTime())); // Atualizando data de pagamento
            pst.setInt(3, pagamento.getConsulta().getId());
            pst.setString(4, pagamento.getFormaPagamento());
            pst.setString(5, pagamento.getStatus());
            pst.setInt(6, pagamento.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Delete
    public void delete(int id) {
        String sql = "DELETE FROM Pagamento WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Find by ID
    public Pagamento find(int id) {
        String sql = "SELECT * FROM Pagamento WHERE id = ?";
        Pagamento pagamento = null;
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                pagamento = new Pagamento();
                pagamento.setId(rs.getInt("id"));
                pagamento.setValor(rs.getFloat("valor"));
                pagamento.setDataPagamento(rs.getTimestamp("data_pagamento")); // Recuperando a data de pagamento
                Consulta consulta = new ConsultaDAO().find(rs.getInt("consulta_id"));
                pagamento.setConsulta(consulta);
                pagamento.setFormaPagamento(rs.getString("forma_pagamento"));
                pagamento.setStatus(rs.getString("status"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pagamento;
    }

    // Find all
    public List<Pagamento> findAll() {
        String sql = "SELECT * FROM Pagamento";
        List<Pagamento> pagamentos = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Pagamento pagamento = new Pagamento();
                pagamento.setId(rs.getInt("id"));
                pagamento.setValor(rs.getFloat("valor"));
                pagamento.setDataPagamento(rs.getTimestamp("data_pagamento"));
                Consulta consulta = new ConsultaDAO().find(rs.getInt("consulta_id"));
                pagamento.setConsulta(consulta);
                pagamento.setFormaPagamento(rs.getString("forma_pagamento"));
                pagamento.setStatus(rs.getString("status"));
                pagamentos.add(pagamento);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pagamentos;
    }
}
