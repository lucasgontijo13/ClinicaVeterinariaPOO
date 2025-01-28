package clinica.model.dao;

import clinica.model.entity.Pet;
import clinica.model.entity.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAO {
    private Conexao conexao;
    private Connection connection;

    public PetDAO() {
        this.conexao = new Conexao();
        this.connection = conexao.getConnection();  // Conexão estabelecida uma vez aqui
    }

    // Create
    public void create(Pet pet) {
        String sql = "INSERT INTO Pet (nome, especie, raca, idade, cliente_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, pet.getNome());
            pst.setString(2, pet.getEspecie());
            pst.setString(3, pet.getRaca());
            pst.setInt(4, pet.getIdade());
            pst.setInt(5, pet.getCliente().getId()); // Cliente está relacionado
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Update
    public void update(Pet pet) {
        String sql = "UPDATE Pet SET nome = ?, especie = ?, raca = ?, idade = ?, cliente_id = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, pet.getNome());
            pst.setString(2, pet.getEspecie());
            pst.setString(3, pet.getRaca());
            pst.setInt(4, pet.getIdade());
            pst.setInt(5, pet.getCliente().getId()); // Cliente está relacionado
            pst.setInt(6, pet.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Delete
    public void delete(int id) {
        String sql = "DELETE FROM Pet WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Find by ID
    public Pet find(int id) {
        String sql = "SELECT * FROM Pet WHERE id = ?";
        Pet pet = null;
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setNome(rs.getString("nome"));
                pet.setEspecie(rs.getString("especie"));
                pet.setRaca(rs.getString("raca"));
                pet.setIdade(rs.getInt("idade"));
                // Buscar cliente relacionado
                Cliente cliente = new ClienteDAO().find(rs.getInt("cliente_id"));
                pet.setCliente(cliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pet;
    }

    // Find all
    public List<Pet> findAll() {
        String sql = "SELECT * FROM Pet";
        List<Pet> pets = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setNome(rs.getString("nome"));
                pet.setEspecie(rs.getString("especie"));
                pet.setRaca(rs.getString("raca"));
                pet.setIdade(rs.getInt("idade"));
                // Buscar cliente relacionado
                Cliente cliente = new ClienteDAO().find(rs.getInt("cliente_id"));
                pet.setCliente(cliente);
                pets.add(pet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pets;
    }
}
