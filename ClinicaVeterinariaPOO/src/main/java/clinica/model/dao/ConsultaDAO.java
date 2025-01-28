package clinica.model.dao;

import clinica.model.entity.Consulta;
import clinica.model.entity.Pet;
import clinica.model.entity.Servico;
import clinica.model.entity.Veterinario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {
    private Conexao conexao;
    private Connection connection;

    public ConsultaDAO() {
        this.conexao = new Conexao();
        this.connection = conexao.getConnection();  // Conexão estabelecida uma vez aqui
    }

    // Create
    public void create(Consulta consulta) {
        String sqlConsulta = "INSERT INTO Consulta (data, pet_id, veterinario_id, descricao) VALUES (?, ?, ?, ?)";
        String sqlConsultaServico = "INSERT INTO consultaservico (consulta_id, servico_id) VALUES (?, ?)";

        try (PreparedStatement pstConsulta = connection.prepareStatement(sqlConsulta, Statement.RETURN_GENERATED_KEYS)) {

            // Inserir consulta com LocalDateTime
            pstConsulta.setTimestamp(1, Timestamp.valueOf(consulta.getDateTime()));  // Convertendo LocalDateTime para Timestamp
            pstConsulta.setInt(2, consulta.getPet().getId());
            pstConsulta.setInt(3, consulta.getVeterinario().getId());
            pstConsulta.setString(4, consulta.getDescricao());
            pstConsulta.executeUpdate();

            // Recuperar o ID gerado da consulta
            ResultSet rs = pstConsulta.getGeneratedKeys();
            if (rs.next()) {
                int consultaId = rs.getInt(1);

                // Inserir os serviços relacionados na tabela consultaservico
                try (PreparedStatement pstConsultaServico = connection.prepareStatement(sqlConsultaServico)) {
                    for (Servico servico : consulta.getServicos()) {
                        pstConsultaServico.setInt(1, consultaId);
                        pstConsultaServico.setInt(2, servico.getId());
                        pstConsultaServico.executeUpdate();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Update
    public void update(Consulta consulta) {
        String sqlConsulta = "UPDATE Consulta SET data = ?, pet_id = ?, veterinario_id = ?, descricao = ? WHERE id = ?";
        String sqlDeleteServicos = "DELETE FROM consultaservico WHERE consulta_id = ?";
        String sqlInsertServicos = "INSERT INTO consultaservico (consulta_id, servico_id) VALUES (?, ?)";

        try {
            // Atualizar a consulta
            try (PreparedStatement pstConsulta = connection.prepareStatement(sqlConsulta)) {
                pstConsulta.setTimestamp(1, Timestamp.valueOf(consulta.getDateTime()));  // Convertendo LocalDateTime para Timestamp
                pstConsulta.setInt(2, consulta.getPet().getId());
                pstConsulta.setInt(3, consulta.getVeterinario().getId());
                pstConsulta.setString(4, consulta.getDescricao());
                pstConsulta.setInt(5, consulta.getId());
                pstConsulta.executeUpdate();
            }

            // Remover os serviços antigos
            try (PreparedStatement pstDeleteServicos = connection.prepareStatement(sqlDeleteServicos)) {
                pstDeleteServicos.setInt(1, consulta.getId());
                pstDeleteServicos.executeUpdate();
            }

            // Inserir os serviços novos
            try (PreparedStatement pstInsertServicos = connection.prepareStatement(sqlInsertServicos)) {
                for (Servico servico : consulta.getServicos()) {
                    pstInsertServicos.setInt(1, consulta.getId());
                    pstInsertServicos.setInt(2, servico.getId());
                    pstInsertServicos.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Delete
    public void delete(int id) {
        String sql = "DELETE FROM Consulta WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Find by ID
    public Consulta find(int id) {
        String sqlConsulta = "SELECT * FROM Consulta WHERE id = ?";
        String sqlServicos = "SELECT s.* FROM Servico s "
                            + "INNER JOIN consultaservico cs ON s.id = cs.servico_id "
                            + "WHERE cs.consulta_id = ?";
        Consulta consulta = null;

        try (PreparedStatement pstConsulta = connection.prepareStatement(sqlConsulta)) {

            pstConsulta.setInt(1, id);
            ResultSet rsConsulta = pstConsulta.executeQuery();
            if (rsConsulta.next()) {
                consulta = new Consulta();
                consulta.setId(rsConsulta.getInt("id"));
                consulta.setDateTime(rsConsulta.getTimestamp("data").toLocalDateTime());  // Convertendo Timestamp para LocalDateTime

                Pet pet = new PetDAO().find(rsConsulta.getInt("pet_id"));
                consulta.setPet(pet);

                Veterinario veterinario = new VeterinarioDAO().find(rsConsulta.getInt("veterinario_id"));
                consulta.setVeterinario(veterinario);

                consulta.setDescricao(rsConsulta.getString("descricao"));

                // Recuperar serviços relacionados
                try (PreparedStatement pstServicos = connection.prepareStatement(sqlServicos)) {
                    pstServicos.setInt(1, id);
                    ResultSet rsServicos = pstServicos.executeQuery();
                    while (rsServicos.next()) {
                        Servico servico = new Servico();
                        servico.setId(rsServicos.getInt("id"));
                        servico.setNome(rsServicos.getString("nome"));
                        servico.setDescricao(rsServicos.getString("descricao"));
                        servico.setPreco(rsServicos.getFloat("preco"));

                        // Adicionar serviço individualmente
                        consulta.addServico(servico);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return consulta;
    }

    // Find all
    public List<Consulta> findAll() {
        String sqlConsulta = "SELECT * FROM Consulta";
        String sqlServicos = "SELECT s.* FROM Servico s "
                            + "INNER JOIN consultaservico cs ON s.id = cs.servico_id "
                            + "WHERE cs.consulta_id = ?";
        List<Consulta> consultas = new ArrayList<>();

        try (PreparedStatement pstConsulta = connection.prepareStatement(sqlConsulta);
             ResultSet rsConsulta = pstConsulta.executeQuery()) {

            while (rsConsulta.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rsConsulta.getInt("id"));
                consulta.setDateTime(rsConsulta.getTimestamp("data").toLocalDateTime());  // Convertendo Timestamp para LocalDateTime

                Pet pet = new PetDAO().find(rsConsulta.getInt("pet_id"));
                consulta.setPet(pet);

                Veterinario veterinario = new VeterinarioDAO().find(rsConsulta.getInt("veterinario_id"));
                consulta.setVeterinario(veterinario);

                consulta.setDescricao(rsConsulta.getString("descricao"));

                // Buscar os serviços relacionados à consulta
                try (PreparedStatement pstServicos = connection.prepareStatement(sqlServicos)) {
                    pstServicos.setInt(1, consulta.getId());
                    try (ResultSet rsServicos = pstServicos.executeQuery()) {
                        while (rsServicos.next()) {
                            Servico servico = new Servico();
                            servico.setId(rsServicos.getInt("id"));
                            servico.setNome(rsServicos.getString("nome"));
                            servico.setDescricao(rsServicos.getString("descricao"));
                            servico.setPreco(rsServicos.getFloat("preco"));

                            // Adicionar serviço individualmente
                            consulta.addServico(servico);
                        }
                    }
                }

                // Adicionar a consulta à lista
                consultas.add(consulta);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return consultas;
    }

}
