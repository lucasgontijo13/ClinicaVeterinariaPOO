package clinica.model.dao;

import clinica.model.entity.Servico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {
    private Conexao conexao;

    public ServicoDAO() {
        this.conexao = new Conexao();
    }

    // Create
    public void create(Servico servico) {
        String sql = "INSERT INTO Servico (nome, descricao, preco) VALUES (?, ?, ?)";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, servico.getNome());
            pst.setString(2, servico.getDescricao());
            pst.setFloat(3, servico.getPreco());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Update
    public void update(Servico servico) {
        String sql = "UPDATE Servico SET nome = ?, descricao = ?, preco = ? WHERE id = ?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, servico.getNome());
            pst.setString(2, servico.getDescricao());
            pst.setFloat(3, servico.getPreco());
            pst.setInt(4, servico.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Delete
    public void delete(int id) {
        String sql = "DELETE FROM Servico WHERE id = ?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Find by ID
    public Servico find(int id) {
        String sql = "SELECT * FROM Servico WHERE id = ?";
        Servico servico = null;
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                servico = new Servico();
                servico.setId(rs.getInt("id"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setPreco(rs.getFloat("preco"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return servico;
    }

    // Find all
    public List<Servico> findAll() {
        String sql = "SELECT * FROM Servico";
        List<Servico> servicos = new ArrayList<>();
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Servico servico = new Servico();
                servico.setId(rs.getInt("id"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setPreco(rs.getFloat("preco"));
                servicos.add(servico);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return servicos;
    }
}
