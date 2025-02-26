import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserDB {

    private static final String URL = "jdbc:mysql://localhost:3306/logindb";
    private static final String USER = "root";
    private static final String PASSWORD = "124578";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing the connection: " + e.getMessage());
            }
        }
    }
}
