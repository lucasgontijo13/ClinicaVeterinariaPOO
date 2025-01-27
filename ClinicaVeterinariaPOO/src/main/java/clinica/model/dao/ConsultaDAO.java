package clinica.model.dao;

import clinica.model.entity.Consulta;
import clinica.model.entity.Pet;
import clinica.model.entity.Veterinario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {
    private Conexao conexao;

    public ConsultaDAO() {
        this.conexao = new Conexao();
    }

    // Create
    public void create(Consulta consulta) {
        String sql = "INSERT INTO Consulta (data, pet_id, veterinario_id, descricao) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setTimestamp(1, new Timestamp(consulta.getData().getTime()));
            pst.setInt(2, consulta.getPet().getId());
            pst.setInt(3, consulta.getVeterinario().getId());
            pst.setString(4, consulta.getDescricao());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Update
    public void update(Consulta consulta) {
        String sql = "UPDATE Consulta SET data = ?, pet_id = ?, veterinario_id = ?, descricao = ? WHERE id = ?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setTimestamp(1, new Timestamp(consulta.getData().getTime()));
            pst.setInt(2, consulta.getPet().getId());
            pst.setInt(3, consulta.getVeterinario().getId());
            pst.setString(4, consulta.getDescricao());
            pst.setInt(5, consulta.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Delete
    public void delete(int id) {
        String sql = "DELETE FROM Consulta WHERE id = ?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Find by ID
    public Consulta find(int id) {
        String sql = "SELECT * FROM Consulta WHERE id = ?";
        Consulta consulta = null;
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                consulta.setData(rs.getTimestamp("data"));
                Pet pet = new PetDAO().find(rs.getInt("pet_id"));
                Veterinario veterinario = new VeterinarioDAO().find(rs.getInt("veterinario_id"));
                consulta.setPet(pet);
                consulta.setVeterinario(veterinario);
                consulta.setDescricao(rs.getString("descricao"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return consulta;
    }

    // Find all
    public List<Consulta> findAll() {
        String sql = "SELECT * FROM Consulta";
        List<Consulta> consultas = new ArrayList<>();
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                consulta.setData(rs.getTimestamp("data"));
                Pet pet = new PetDAO().find(rs.getInt("pet_id"));
                Veterinario veterinario = new VeterinarioDAO().find(rs.getInt("veterinario_id"));
                consulta.setPet(pet);
                consulta.setVeterinario(veterinario);
                consulta.setDescricao(rs.getString("descricao"));
                consultas.add(consulta);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return consultas;
    }
}
