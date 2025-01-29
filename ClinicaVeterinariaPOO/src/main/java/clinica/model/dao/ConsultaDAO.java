package clinica.model.dao;

import clinica.model.entity.Consulta;
import clinica.model.entity.Pet;
import clinica.model.entity.Servico;
import clinica.model.entity.Veterinario;
import java.sql.*;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {
    private Conexao conexao;

    public ConsultaDAO() {
        this.conexao = new Conexao();
    }

    public void create(Consulta consulta) {
        String sqlConsulta = "INSERT INTO Consulta (data, pet_id, veterinario_id, descricao, valorpraticado, formapagamento) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexao.getConnection();
             PreparedStatement pstConsulta = conn.prepareStatement(sqlConsulta, Statement.RETURN_GENERATED_KEYS)) {

            // Definir os parâmetros para a consulta
            pstConsulta.setObject(1, consulta.getDateTime());  // Enviar LocalDateTime diretamente

            pstConsulta.setInt(2, consulta.getPet().getId());  // ID do Pet
            pstConsulta.setInt(3, consulta.getVeterinario().getId());  // ID do Veterinário
            pstConsulta.setString(4, consulta.getDescricao());  // Descrição da consulta
            pstConsulta.setDouble(5, consulta.getValorPraticado());  // Valor praticado
            pstConsulta.setString(6, consulta.getFormaPagamento());  // Forma de pagamento

            // Executar a inserção da consulta
            pstConsulta.executeUpdate();

            // Recuperar o ID gerado da consulta
            ResultSet rs = pstConsulta.getGeneratedKeys();
            if (rs.next()) {
                int consultaId = rs.getInt(1);  // Recuperar o ID gerado

                // Atribuir o ID gerado ao objeto consulta
                consulta.setId(consultaId);

                // Inserir os serviços relacionados na tabela consultaservico
                try (PreparedStatement pstConsultaServico = conn.prepareStatement("INSERT INTO consultaservico (consulta_id, servico_id) VALUES (?, ?)")) {
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


    public void update(Consulta consulta) {
        String sqlConsulta = "UPDATE Consulta SET data = ?, pet_id = ?, veterinario_id = ?, descricao = ? WHERE id = ?";
        String sqlDeleteServicos = "DELETE FROM consultaservico WHERE consulta_id = ?";
        String sqlInsertServicos = "INSERT INTO consultaservico (consulta_id, servico_id) VALUES (?, ?)";

        try (Connection conn = conexao.getConnection()) {
            // Atualizar a consulta
            try (PreparedStatement pstConsulta = conn.prepareStatement(sqlConsulta)) {
                // Converte Date para Timestamp
                pstConsulta.setTimestamp(1, new Timestamp(consulta.getDateTime().getTime()));
                pstConsulta.setInt(2, consulta.getPet().getId());
                pstConsulta.setInt(3, consulta.getVeterinario().getId());
                pstConsulta.setString(4, consulta.getDescricao());
                pstConsulta.setInt(5, consulta.getId());
                pstConsulta.executeUpdate();
            }

            // Remover os serviços antigos
            try (PreparedStatement pstDeleteServicos = conn.prepareStatement(sqlDeleteServicos)) {
                pstDeleteServicos.setInt(1, consulta.getId());
                pstDeleteServicos.executeUpdate();
            }

            // Inserir os novos serviços
            try (PreparedStatement pstInsertServicos = conn.prepareStatement(sqlInsertServicos)) {
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



    public void delete(int id) {
        // SQL para deletar as associações da consulta com os serviços
        String sqlDeleteServicos = "DELETE FROM consultaservico WHERE consulta_id = ?";
        // SQL para deletar a consulta
        String sqlDeleteConsulta = "DELETE FROM Consulta WHERE id = ?";

        try (Connection conn = conexao.getConnection()) {
            // Remover a associação da consulta com os serviços
            try (PreparedStatement pstDeleteServicos = conn.prepareStatement(sqlDeleteServicos)) {
                pstDeleteServicos.setInt(1, id);
                pstDeleteServicos.executeUpdate();
            }

            // Excluir a consulta
            try (PreparedStatement pstDeleteConsulta = conn.prepareStatement(sqlDeleteConsulta)) {
                pstDeleteConsulta.setInt(1, id);
                pstDeleteConsulta.executeUpdate();
            }

            System.out.println("Consulta e suas associações com serviços excluídas com sucesso.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    
    
    public Consulta find(int id) {
        String sqlConsulta = "SELECT * FROM Consulta WHERE id = ?";
        String sqlServicos = "SELECT s.* FROM Servico s "
                             + "INNER JOIN consultaservico cs ON s.id = cs.servico_id "
                             + "WHERE cs.consulta_id = ?";
        Consulta consulta = null;

        try (Connection conn = conexao.getConnection();
             PreparedStatement pstConsulta = conn.prepareStatement(sqlConsulta)) {

            pstConsulta.setInt(1, id);
            ResultSet rsConsulta = pstConsulta.executeQuery();
            if (rsConsulta.next()) {
                consulta = new Consulta();
                consulta.setId(rsConsulta.getInt("id"));
                consulta.setDescricao(rsConsulta.getString("descricao"));
                consulta.setValorPraticado(rsConsulta.getDouble("valorpraticado"));
                consulta.setFormaPagamento(rsConsulta.getString("formapagamento"));

                // Obter o pet e veterinário
                Pet pet = new PetDAO().find(rsConsulta.getInt("pet_id"));
                consulta.setPet(pet);

                Veterinario veterinario = new VeterinarioDAO().find(rsConsulta.getInt("veterinario_id"));
                consulta.setVeterinario(veterinario);

                consulta.setDateTime(rsConsulta.getTimestamp("data"));
                

                // Recuperar serviços relacionados
                try (PreparedStatement pstServicos = conn.prepareStatement(sqlServicos)) {
                    pstServicos.setInt(1, id);
                    ResultSet rsServicos = pstServicos.executeQuery();
                    while (rsServicos.next()) {
                        Servico servico = new Servico();
                        servico.setId(rsServicos.getInt("id"));
                        servico.setNome(rsServicos.getString("nome"));
                        servico.setDescricao(rsServicos.getString("descricao"));
                        servico.setPreco(rsServicos.getFloat("preco"));

                        // Adicionar serviço à consulta
                        consulta.addServico(servico);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return consulta;
    }


   
    public List<Consulta> buscarTodasConsultas() {
    String sql = "SELECT * FROM Consulta";

    List<Consulta> consultas = new ArrayList<>();

    try (Connection conn = conexao.getConnection();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Consulta consulta = new Consulta();
            consulta.setId(rs.getInt("id"));
            consulta.setDescricao(rs.getString("descricao"));
            consulta.setValorPraticado(rs.getDouble("valorpraticado"));
            consulta.setFormaPagamento(rs.getString("formapagamento"));
            consulta.setDateTime(rs.getTimestamp("data"));

            // Obter o pet e veterinário
            Pet pet = new PetDAO().find(rs.getInt("pet_id"));
            consulta.setPet(pet);

            Veterinario veterinario = new VeterinarioDAO().find(rs.getInt("veterinario_id"));
            consulta.setVeterinario(veterinario);

            consultas.add(consulta);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return consultas;
}
    
    public List<Consulta> buscarConsultasPorIdPet(int petId) {
        String sql = "SELECT * FROM Consulta WHERE pet_id = ?";
        List<Consulta> consultas = new ArrayList<>();

        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, petId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                consulta.setDescricao(rs.getString("descricao"));
                consulta.setValorPraticado(rs.getDouble("valorpraticado"));
                consulta.setFormaPagamento(rs.getString("formapagamento"));
                consulta.setDateTime(rs.getTimestamp("data"));

                // Buscar o pet relacionado
                Pet pet = new PetDAO().find(rs.getInt("pet_id"));
                consulta.setPet(pet);

                // Buscar o veterinário relacionado
                Veterinario veterinario = new VeterinarioDAO().find(rs.getInt("veterinario_id"));
                consulta.setVeterinario(veterinario);

                // Adicionar à lista
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consultas;
    }
    
    public List<Consulta> buscarConsultasPorIdVeterinario(int veterinarioId) {
        String sql = "SELECT * FROM Consulta WHERE veterinario_id = ?";
        List<Consulta> consultas = new ArrayList<>();

        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, veterinarioId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                consulta.setDescricao(rs.getString("descricao"));
                consulta.setValorPraticado(rs.getDouble("valorpraticado"));
                consulta.setFormaPagamento(rs.getString("formapagamento"));
                consulta.setDateTime(rs.getTimestamp("data"));

                // Obter o pet e veterinário
                Pet pet = new PetDAO().find(rs.getInt("pet_id"));
                consulta.setPet(pet);

                Veterinario veterinario = new VeterinarioDAO().find(rs.getInt("veterinario_id"));
                consulta.setVeterinario(veterinario);

                consultas.add(consulta);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return consultas;
    }

    public List<Consulta> buscarConsultasPorData(LocalDate dataInicial, LocalDate dataFinal) {
        String sql = "SELECT * FROM Consulta WHERE data >= ? AND data <= ?";

        List<Consulta> consultas = new ArrayList<>();

        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            // Converter LocalDate para Timestamp no início e fim do intervalo
            Timestamp dataInicialTimestamp = Timestamp.valueOf(dataInicial.atStartOfDay());
            Timestamp dataFinalTimestamp = Timestamp.valueOf(dataFinal.atTime(23, 59, 59));

            pst.setTimestamp(1, dataInicialTimestamp);
            pst.setTimestamp(2, dataFinalTimestamp);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                consulta.setDescricao(rs.getString("descricao"));
                consulta.setValorPraticado(rs.getDouble("valorpraticado"));
                consulta.setFormaPagamento(rs.getString("formapagamento"));
                consulta.setDateTime(rs.getTimestamp("data"));

                // Obter o pet e veterinário
                Pet pet = new PetDAO().find(rs.getInt("pet_id"));
                consulta.setPet(pet);

                Veterinario veterinario = new VeterinarioDAO().find(rs.getInt("veterinario_id"));
                consulta.setVeterinario(veterinario);

                consultas.add(consulta);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return consultas;
    }

    public List<Consulta> buscarConsultasPorFormaPagamento(String formaPagamento) {
        String sql = "SELECT * FROM Consulta WHERE formaPagamento LIKE ?";

        List<Consulta> consultas = new ArrayList<>();

        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            // Adicionar o '%' antes e depois da forma de pagamento para busca parcial
            pst.setString(1, "%" + formaPagamento.toLowerCase() + "%");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                consulta.setDescricao(rs.getString("descricao"));
                consulta.setValorPraticado(rs.getDouble("valorpraticado"));
                consulta.setFormaPagamento(rs.getString("formapagamento"));
                consulta.setDateTime(rs.getTimestamp("data"));

                // Obter o pet e veterinário
                Pet pet = new PetDAO().find(rs.getInt("pet_id"));
                consulta.setPet(pet);

                Veterinario veterinario = new VeterinarioDAO().find(rs.getInt("veterinario_id"));
                consulta.setVeterinario(veterinario);

                consultas.add(consulta);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return consultas;
    }

    public List<Consulta> buscarConsultasPorValor(double valorMinimo, double valorMaximo) {
        String sql = "SELECT * FROM Consulta WHERE valorpraticado BETWEEN ? AND ?";

        List<Consulta> consultas = new ArrayList<>();

        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            // Passando os valores mínimo e máximo para a consulta SQL
            pst.setDouble(1, valorMinimo);
            pst.setDouble(2, valorMaximo);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                consulta.setDescricao(rs.getString("descricao"));
                consulta.setValorPraticado(rs.getDouble("valorpraticado"));
                consulta.setFormaPagamento(rs.getString("formapagamento"));
                consulta.setDateTime(rs.getTimestamp("data"));

                // Obter o pet e veterinário
                Pet pet = new PetDAO().find(rs.getInt("pet_id"));
                consulta.setPet(pet);

                Veterinario veterinario = new VeterinarioDAO().find(rs.getInt("veterinario_id"));
                consulta.setVeterinario(veterinario);

                consultas.add(consulta);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return consultas;
    }

    public List<Consulta> buscarConsultasPorServico(int servicoId) {
        String sql = "SELECT c.* FROM Consulta c " +
                     "JOIN ConsultaServico cs ON c.id = cs.consulta_id " +
                     "WHERE cs.servico_id = ?";

        List<Consulta> consultas = new ArrayList<>();

        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            // Passando o ID do serviço para a consulta SQL
            pst.setInt(1, servicoId);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                consulta.setDescricao(rs.getString("descricao"));
                consulta.setValorPraticado(rs.getDouble("valorpraticado"));
                consulta.setFormaPagamento(rs.getString("formapagamento"));
                consulta.setDateTime(rs.getTimestamp("data"));

                // Obter o pet e veterinário
                Pet pet = new PetDAO().find(rs.getInt("pet_id"));
                consulta.setPet(pet);

                Veterinario veterinario = new VeterinarioDAO().find(rs.getInt("veterinario_id"));
                consulta.setVeterinario(veterinario);

                consultas.add(consulta);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return consultas;
    }

    
    public boolean existeConsultaParaVeterinario(int veterinarioId) {
        String sql = "SELECT COUNT(*) FROM Consulta WHERE veterinario_id = ?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, veterinarioId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true se houver consultas vinculadas
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    
    public boolean verificarDisponibilidade(int veterinarioId, Timestamp dataHoraConsulta) {
        // Definindo o intervalo de 60 minutos (1 hora) para verificar a sobreposição
        long intervaloMinimoEmMilissegundos = 60 * 60 * 1000;  // 60 minutos = 1 hora

        // Calculando os horários de início e fim da consulta solicitada
        Timestamp dataHoraFimConsulta = new Timestamp(dataHoraConsulta.getTime() + intervaloMinimoEmMilissegundos);

        // SQL para verificar se já existe alguma consulta no intervalo de 60 minutos
        String sql = "SELECT * FROM consulta WHERE veterinario_id = ? AND ("
                   + "(data >= ? AND data < ?) OR "
                   + "(data + INTERVAL 1 HOUR > ? AND data < ?))"; 

        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            // Configura os parâmetros da consulta SQL
            pst.setInt(1, veterinarioId);  // veterinário específico
            pst.setTimestamp(2, dataHoraConsulta);  // horário de início da consulta solicitada
            pst.setTimestamp(3, dataHoraFimConsulta);  // horário final da consulta solicitada
            pst.setTimestamp(4, dataHoraConsulta);  // horário de início da consulta solicitada
            pst.setTimestamp(5, dataHoraFimConsulta);  // horário final da consulta solicitada

            ResultSet rs = pst.executeQuery();

            // Se houver um registro, significa que o veterinário não está disponível
            return !rs.next();  // Se rs.next() retornar false, significa que o horário está livre

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Caso ocorra algum erro ou o veterinário já tenha consulta no horário
    }







    
    
    

}
