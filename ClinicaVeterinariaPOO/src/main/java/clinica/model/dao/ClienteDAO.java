package clinica.model.dao;

import clinica.model.entity.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Conexao conexao;

    public ClienteDAO() {
        this.conexao = new Conexao();
    }

    // Create
    public void create(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nome, telefone, email) VALUES (?, ?, ?)";

        try (PreparedStatement pst = conexao.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) { 
            // Usa a conexão já existente e retorna as chaves geradas automaticamente (ID autoincrementado)
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getTelefone());
            pst.setString(3, cliente.getEmail());
            pst.executeUpdate();

            // Recupera o ID gerado automaticamente
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1); // Obtém o ID gerado
                    cliente.setId(generatedId); // Atualiza o cliente com o ID gerado
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    // Update
    public void update(Cliente cliente) {
        String sql = "UPDATE Cliente SET nome = ?, telefone = ?, email = ? WHERE id = ?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
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
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
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
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
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

   
    public List<Cliente> findAll() {
        String sql = "SELECT * FROM Cliente";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
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
    
    public List<Cliente> findByName(String nome) {
        String sql = "SELECT * FROM Cliente WHERE nome LIKE ?";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + nome + "%"); // Permite buscas parciais
            ResultSet rs = pst.executeQuery();
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
    
    public boolean existeRegistroParaCliente(int clienteId) {
        // SQL para verificar se o cliente está vinculado a algum pet ou venda
        String sqlPet = "SELECT COUNT(*) FROM pet WHERE cliente_id = ?"; // Verifica se o cliente está vinculado a algum pet
        String sqlVenda = "SELECT COUNT(*) FROM venda WHERE cliente_id = ?"; // Verifica se o cliente está vinculado a alguma venda

        try (Connection conn = conexao.getConnection(); // Obtém a conexão com o banco de dados
             PreparedStatement stmtPet = conn.prepareStatement(sqlPet);
             PreparedStatement stmtVenda = conn.prepareStatement(sqlVenda)) {

            // Verifica vinculação com pets
            stmtPet.setInt(1, clienteId);
            try (ResultSet rsPet = stmtPet.executeQuery()) {
                if (rsPet.next()) {
                    int countPet = rsPet.getInt(1);
                    if (countPet > 0) {
                        return true; // Se o cliente estiver vinculado a algum pet, retorna true
                    }
                }
            }

            // Verifica vinculação com vendas
            stmtVenda.setInt(1, clienteId);
            try (ResultSet rsVenda = stmtVenda.executeQuery()) {
                if (rsVenda.next()) {
                    int countVenda = rsVenda.getInt(1);
                    if (countVenda > 0) {
                        return true; // Se o cliente estiver vinculado a alguma venda, retorna true
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Se não houver vínculo com pets ou vendas, retorna false
    }
}