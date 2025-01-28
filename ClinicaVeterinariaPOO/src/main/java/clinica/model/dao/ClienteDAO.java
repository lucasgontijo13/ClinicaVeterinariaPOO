package clinica.model.dao;

import clinica.model.entity.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Conexao conexao;
    private Connection connection;

    public ClienteDAO() {
        this.conexao = new Conexao();
        this.connection = conexao.getConnection();  
    }

    // Create
    public void create(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nome, telefone, email) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {  
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getTelefone());
            pst.setString(3, cliente.getEmail());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Update
    public void update(Cliente cliente) {
        String sql = "UPDATE Cliente SET nome = ?, telefone = ?, email = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {  
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getTelefone());
            pst.setString(3, cliente.getEmail());
            pst.setInt(4, cliente.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Delete
    public void delete(int id) {
        String sql = "DELETE FROM Cliente WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {  
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Find by ID
    public Cliente find(int id) {
        String sql = "SELECT * FROM Cliente WHERE id = ?";
        Cliente cliente = null;
        try (PreparedStatement pst = connection.prepareStatement(sql)) {  
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cliente;
    }

    // Find all
    public List<Cliente> findAll() {
        String sql = "SELECT * FROM Cliente";
        List<Cliente> clientes = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {  
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return clientes;
    }

}
