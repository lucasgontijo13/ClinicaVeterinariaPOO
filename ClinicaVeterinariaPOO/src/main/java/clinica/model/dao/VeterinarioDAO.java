package clinica.model.dao;

import clinica.model.entity.Veterinario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDAO {
    private Conexao conexao;
    private Connection connection;

    public VeterinarioDAO() {
        this.conexao = new Conexao();
        this.connection = conexao.getConnection();  // Conexão estabelecida uma vez aqui
    }

    // Create
    public void create(Veterinario veterinario) {
        String sql = "INSERT INTO Veterinario (nome, especializacao, telefone) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, veterinario.getNome());
            pst.setString(2, veterinario.getEspecializacao());
            pst.setString(3, veterinario.getTelefone());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Update
    public void update(Veterinario veterinario) {
        String sql = "UPDATE Veterinario SET nome = ?, especializacao = ?, telefone = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, veterinario.getNome());
            pst.setString(2, veterinario.getEspecializacao());
            pst.setString(3, veterinario.getTelefone());
            pst.setInt(4, veterinario.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Delete
    public void delete(int id) {
        String sql = "DELETE FROM Veterinario WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Find by ID
    public Veterinario find(int id) {
        String sql = "SELECT * FROM Veterinario WHERE id = ?";
        Veterinario veterinario = null;
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                veterinario = new Veterinario();
                veterinario.setId(rs.getInt("id"));
                veterinario.setNome(rs.getString("nome"));
                veterinario.setEspecializacao(rs.getString("especializacao"));
                veterinario.setTelefone(rs.getString("telefone"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return veterinario;
    }

    // Find all
    public List<Veterinario> findAll() {
        String sql = "SELECT * FROM Veterinario";
        List<Veterinario> veterinarios = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Veterinario veterinario = new Veterinario();
                veterinario.setId(rs.getInt("id"));
                veterinario.setNome(rs.getString("nome"));
                veterinario.setEspecializacao(rs.getString("especializacao"));
                veterinario.setTelefone(rs.getString("telefone"));
                veterinarios.add(veterinario);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return veterinarios;
    }
}
