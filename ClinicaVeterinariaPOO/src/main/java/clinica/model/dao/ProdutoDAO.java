package clinica.model.dao;

import clinica.model.entity.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Conexao conexao;
    private Connection connection;

    public ProdutoDAO() {
        this.conexao = new Conexao();
        this.connection = conexao.getConnection();  // Conexão estabelecida uma vez aqui
    }

    // Create
    public void create(Produto produto) {
        String sql = "INSERT INTO Produto (nome, descricao, preco) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, produto.getNome());
            pst.setString(2, produto.getDescricao());
            pst.setFloat(3, produto.getPreco());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Update
    public void update(Produto produto) {
        String sql = "UPDATE Produto SET nome = ?, descricao = ?, preco = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, produto.getNome());
            pst.setString(2, produto.getDescricao());
            pst.setFloat(3, produto.getPreco());
            pst.setInt(4, produto.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Delete
    public void delete(int id) {
        String sql = "DELETE FROM Produto WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Find by ID
    public Produto find(int id) {
        String sql = "SELECT * FROM Produto WHERE id = ?";
        Produto produto = null;
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getFloat("preco"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return produto;
    }

    // Find all
    public List<Produto> findAll() {
        String sql = "SELECT * FROM Produto";
        List<Produto> produtos = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getFloat("preco"));
                produtos.add(produto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return produtos;
    }
}
