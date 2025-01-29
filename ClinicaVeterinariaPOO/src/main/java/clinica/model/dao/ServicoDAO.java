package clinica.model.dao;

import clinica.model.entity.Servico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {
    private Conexao conexao;

    // Construtor da classe
    public ServicoDAO() {
        this.conexao = new Conexao(); // Inicializando a classe Conexao
    }

    // Método para salvar um novo serviço
    public void salvar(Servico servico) {
        String sql = "INSERT INTO servico (nome, descricao, preco) VALUES (?, ?, ?)";

        try (Connection connection = conexao.getConnection(); // Obtendo a conexão
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, servico.getNome());
            stmt.setString(2, servico.getDescricao());
            stmt.setFloat(3, servico.getPreco());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                servico.setId(rs.getInt(1)); // Definir o ID gerado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para atualizar um serviço existente
    public void atualizar(Servico servico) {
        String sql = "UPDATE servico SET nome = ?, descricao = ?, preco = ? WHERE id = ?";

        try (Connection connection = conexao.getConnection(); // Obtendo a conexão
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, servico.getNome());
            stmt.setString(2, servico.getDescricao());
            stmt.setFloat(3, servico.getPreco());
            stmt.setInt(4, servico.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para excluir um serviço
    public void excluir(int id) {
        String sql = "DELETE FROM servico WHERE id = ?";

        try (Connection connection = conexao.getConnection(); // Obtendo a conexão
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar um serviço por ID
    public Servico buscarPorId(int id) {
        String sql = "SELECT * FROM servico WHERE id = ?";
        Servico servico = null;

        try (Connection connection = conexao.getConnection(); // Obtendo a conexão
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                servico = new Servico();
                servico.setId(rs.getInt("id"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setPreco(rs.getFloat("preco"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servico;
    }



    // Buscar Serviço por Nome (parcial)
    public List<Servico> buscarPorNome(String nome) {
        String sql = "SELECT * FROM servico WHERE nome LIKE ?";
        List<Servico> servicos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.getConnection().prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Servico servico = new Servico();
                servico.setId(rs.getInt("id"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setPreco(rs.getFloat("preco"));
                servicos.add(servico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicos;
    }

    // Buscar Serviço por Faixa de Preço
    public List<Servico> buscarPorFaixaDePreco(float precoMinimo, float precoMaximo) {
        String sql = "SELECT * FROM servico WHERE preco BETWEEN ? AND ?";
        List<Servico> servicos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.getConnection().prepareStatement(sql)) {
            stmt.setFloat(1, precoMinimo);
            stmt.setFloat(2, precoMaximo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Servico servico = new Servico();
                servico.setId(rs.getInt("id"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setPreco(rs.getFloat("preco"));
                servicos.add(servico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicos;
    }

    // Listar Todos os Serviços
    public List<Servico> listarTodos() {
        String sql = "SELECT * FROM servico";
        List<Servico> servicos = new ArrayList<>();

        try (Statement stmt = conexao.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Servico servico = new Servico();
                servico.setId(rs.getInt("id"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setPreco(rs.getFloat("preco"));
                servicos.add(servico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicos;
    }

}
