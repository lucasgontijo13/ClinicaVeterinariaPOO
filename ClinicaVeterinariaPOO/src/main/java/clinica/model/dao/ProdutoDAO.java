package clinica.model.dao;

import clinica.model.entity.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Conexao conexao;

    public ProdutoDAO() {
        this.conexao = new Conexao();
    }
    
    //Create
    public void create(Produto produto) {
        String sql = "INSERT INTO Produto (nome, descricao, preco, estoque) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Use Statement.RETURN_GENERATED_KEYS
            pst.setString(1, produto.getNome());
            pst.setString(2, produto.getDescricao());
            pst.setFloat(3, produto.getPreco());
            pst.setInt(4, produto.getEstoque());
            pst.executeUpdate();

            // Obter o ID gerado automaticamente
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    produto.setId(rs.getInt(1)); // Atribui o ID gerado ao produto
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    // Update
    public void update(Produto produto) {
        String sql = "UPDATE Produto SET nome = ?, descricao = ?, preco = ?, estoque = ? WHERE id = ?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, produto.getNome());
            pst.setString(2, produto.getDescricao());
            pst.setFloat(3, produto.getPreco());
            pst.setInt(4, produto.getEstoque()); 
            pst.setInt(5, produto.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Delete
    public void delete(int id) {
        String sql = "DELETE FROM Produto WHERE id = ?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
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
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getFloat("preco"));
                produto.setEstoque(rs.getInt("estoque"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return produto;
    }
    
    // Busca por nome (retorna uma lista de produtos)
    public List<Produto> findByName(String nome) {
        String sql = "SELECT * FROM Produto WHERE nome LIKE ?";
        List<Produto> produtos = new ArrayList<>();
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + nome + "%"); // Permite buscas parciais
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getFloat("preco"));
                produto.setEstoque(rs.getInt("estoque"));
                produtos.add(produto); // Adiciona o produto à lista
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return produtos; // Retorna a lista de produtos encontrados
    }

    

    // Find all
    public List<Produto> findAll() {
        String sql = "SELECT * FROM Produto";
        List<Produto> produtos = new ArrayList<>();
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getFloat("preco"));
                produto.setEstoque(rs.getInt("estoque"));
                produtos.add(produto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return produtos;
    }
    public boolean atualizarPreco(int id, float novoPreco) {
        String sql = "UPDATE Produto SET preco = ? WHERE id = ?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setFloat(1, novoPreco);
            pst.setInt(2, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean atualizarEstoque(int id, int novoEstoque) {
        String sql = "UPDATE Produto SET estoque = ? WHERE id = ?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, novoEstoque);
            pst.setInt(2, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    
    public boolean existeVendaParaProduto(int produtoId) {
        String sql = "SELECT COUNT(*) FROM venda WHERE produto_id = ?"; // Verifica quantas vendas estão vinculadas ao produto
        try (Connection conn = conexao.getConnection(); // Obtém a conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produtoId);  // Substitui o ? pelo ID do produto
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;  // Se houver uma ou mais vendas, retorna true
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Caso contrário, retorna false
    }

}