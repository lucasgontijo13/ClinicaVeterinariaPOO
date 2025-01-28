package clinica.model.dao;

import clinica.model.entity.Consulta;
import clinica.model.entity.Servico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {
    private Conexao conexao;
    private Connection connection;

    public ServicoDAO() {
        this.conexao = new Conexao();
        this.connection = conexao.getConnection();  // Conexão estabelecida uma vez aqui
    }

    // Create
    public void create(Servico servico) {
        String sql = "INSERT INTO Servico (nome, descricao, preco) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, servico.getNome());
            pst.setString(2, servico.getDescricao());
            pst.setFloat(3, servico.getPreco());
            pst.executeUpdate();

            // Obter o ID gerado
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                servico.setId(rs.getInt(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Update
    public void update(Servico servico) {
        String sql = "UPDATE Servico SET nome = ?, descricao = ?, preco = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
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
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Add Consulta a um Servico
    public void addConsulta(int servicoId, int consultaId) {
        String sql = "INSERT INTO Servico_Consulta (servico_id, consulta_id) VALUES (?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, servicoId);
            pst.setInt(2, consultaId);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Find by ID (com Consultas associadas)
    public Servico find(int id) {
        String sql = "SELECT * FROM Servico WHERE id = ?";
        Servico servico = null;
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                servico = new Servico();
                servico.setId(rs.getInt("id"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setPreco(rs.getFloat("preco"));
                servico.setConsultas(findConsultasByServicoId(id)); // Buscar consultas associadas
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return servico;
    }

    // Encontrar todas as Consultas associadas a um Serviço
    private List<Consulta> findConsultasByServicoId(int servicoId) {
        String sql = "SELECT c.* FROM Consulta c " +
                     "INNER JOIN Servico_Consulta sc ON c.id = sc.consulta_id " +
                     "WHERE sc.servico_id = ?";
        List<Consulta> consultas = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, servicoId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                consulta.setDateTime(rs.getTimestamp("data_horario").toLocalDateTime()); // Alterado para DateTime
                // Outros atributos da consulta
                consultas.add(consulta);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return consultas;
    }

    // Find all (com Consultas associadas)
    public List<Servico> findAll() {
        String sql = "SELECT * FROM Servico";
        List<Servico> servicos = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Servico servico = new Servico();
                servico.setId(rs.getInt("id"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setPreco(rs.getFloat("preco"));
                servico.setConsultas(findConsultasByServicoId(servico.getId())); // Buscar consultas associadas
                servicos.add(servico);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return servicos;
    }
}
