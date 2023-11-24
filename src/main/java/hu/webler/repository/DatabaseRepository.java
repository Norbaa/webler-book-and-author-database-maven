package hu.webler.repository;

import javax.sql.rowset.RowSetWarning;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseRepository {
    private static Connection connection;
    private static final String JDBC_URL = "jdbc:mysql://localhost:"
            + System.getenv("DB_PORT")
            + "/"
            + System.getenv("DB_URL")
            + "?createDatabaseIfNotExist=true&useSSL=true&sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false";

    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
                createAuthorTable(connection);
                createBookTable(connection);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    private static void createAuthorTable(Connection connection) throws SQLException {
        String createAuthorTableSQL = "CREATE TABLE IF NOT EXISTS author(" +
                +id INT PRIMARY KEY AUTO_INCREMENT, " +
                + name VACHAR(255) NOT NULL " +
                + ")";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createAuthorTableSQL)) {
            preparedStatement.executeUpdate();
        }
    }

    private static void createBookTable(Connection connection) throws SQLException {
        String createBookTableSQL = "CREATE TABLE IF NOT EXISTS book(" +
                +title VACHAR(255) NOT NULL, "+
                + autor_id INT, "+
                + FOREIGN KEY(author_id) REFERENCES author (id) "+
                + ")";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createBookTableSQL)) {
            preparedStatement.executeUpdate();
        }
    }

    private DatabaseRepository() {

    }
}

