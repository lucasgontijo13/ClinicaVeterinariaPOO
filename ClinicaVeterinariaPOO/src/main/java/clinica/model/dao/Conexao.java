package clinica.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao {
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/clinicaveterinariadb";
    private static final String DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "";  // Substitua se necessário
    private static final String PASS = "";  // Substitua pela senha do seu banco de dados

    private Connection connection;

    public Connection getConnection() {
       
        try {
            Class.forName(DRIVER_MYSQL);  // Carrega o driver do MySQL
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            connection = DriverManager.getConnection(URL_MYSQL, USER, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexão fechada com sucesso.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
