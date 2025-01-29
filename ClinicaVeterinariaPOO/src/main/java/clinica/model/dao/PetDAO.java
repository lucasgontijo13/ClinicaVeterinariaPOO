package clinica.model.dao;

import clinica.model.entity.Pet;
import clinica.model.entity.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAO {
    private Conexao conexao;

    public PetDAO() {
        this.conexao = new Conexao();
    }

    // Create
    public void create(Pet pet) {
        String sql = "INSERT INTO Pet (nome, especie, raca, idade, cliente_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {  // Especificando para retornar chaves geradas
            pst.setString(1, pet.getNome());
            pst.setString(2, pet.getEspecie());
            pst.setString(3, pet.getRaca());
            pst.setInt(4, pet.getIdade());
            pst.setInt(5, pet.getCliente().getId()); // Cliente está relacionado
            pst.executeUpdate();

            // Recuperando o ID gerado
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    pet.setId(rs.getInt(1)); // Atribuindo o ID gerado ao pet
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    // Update
    public void update(Pet pet) {
        String sql = "UPDATE Pet SET nome = ?, especie = ?, raca = ?, idade = ?, cliente_id = ? WHERE id = ?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
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
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
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
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
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
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setNome(rs.getString("nome"));
                pet.setEspecie(rs.getString("especie"));
                pet.setRaca(rs.getString("raca"));
                pet.setIdade(rs.getInt("idade"));
                Cliente cliente = new ClienteDAO().find(rs.getInt("cliente_id"));
                pet.setCliente(cliente);
                pets.add(pet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pets;
    }

    
    public List<Pet> findByRaca(String raca) {
        String sql = "SELECT * FROM Pet WHERE raca LIKE ?";
        List<Pet> pets = new ArrayList<>();
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + raca + "%");  // Adicionando '%' para permitir correspondência parcial
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setNome(rs.getString("nome"));
                pet.setEspecie(rs.getString("especie"));
                pet.setRaca(rs.getString("raca"));
                pet.setIdade(rs.getInt("idade"));
                Cliente cliente = new ClienteDAO().find(rs.getInt("cliente_id"));
                pet.setCliente(cliente);
                pets.add(pet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pets;
    }
    
    public List<Pet> findByEspecie(String especie) {
        String sql = "SELECT * FROM Pet WHERE especie LIKE ?";
        List<Pet> pets = new ArrayList<>();
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + especie + "%");  // Adicionando '%' para permitir correspondência parcial
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setNome(rs.getString("nome"));
                pet.setEspecie(rs.getString("especie"));
                pet.setRaca(rs.getString("raca"));
                pet.setIdade(rs.getInt("idade"));
                Cliente cliente = new ClienteDAO().find(rs.getInt("cliente_id"));
                pet.setCliente(cliente);
                pets.add(pet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pets;
    }
    
    public List<Pet> findByIdade(int idade) {
        String sql = "SELECT * FROM Pet WHERE idade = ?";
        List<Pet> pets = new ArrayList<>();
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, idade);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setNome(rs.getString("nome"));
                pet.setEspecie(rs.getString("especie"));
                pet.setRaca(rs.getString("raca"));
                pet.setIdade(rs.getInt("idade"));
                Cliente cliente = new ClienteDAO().find(rs.getInt("cliente_id"));
                pet.setCliente(cliente);
                pets.add(pet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pets;
    }
     
    public List<Pet> findByDono(int clienteId) {
        String sql = "SELECT * FROM Pet WHERE cliente_id = ?";
        List<Pet> pets = new ArrayList<>();
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, clienteId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setNome(rs.getString("nome"));
                pet.setEspecie(rs.getString("especie"));
                pet.setRaca(rs.getString("raca"));
                pet.setIdade(rs.getInt("idade"));
                Cliente cliente = new ClienteDAO().find(rs.getInt("cliente_id"));
                pet.setCliente(cliente);
                pets.add(pet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pets;
    }
    
    
    public boolean existeConsultaParaPet(int petId) {
        String sql = "SELECT COUNT(*) FROM consulta WHERE pet_id = ?"; // Verifica quantas consultas estão vinculadas ao pet
        try (Connection conn = conexao.getConnection(); // Obtém a conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, petId);  // Substitui o ? pelo ID do pet
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;  // Se houver uma ou mais consultas, retorna true
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Caso contrário, retorna false
    }




}