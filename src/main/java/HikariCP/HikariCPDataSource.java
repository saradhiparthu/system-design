package HikariCP;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class HikariCPDataSource {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/e-commerce?useSSL=false&serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("root");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setPoolName("MyHikariPool");

        HikariDataSource ds = new HikariDataSource(config);

        try (Connection conn = ds.getConnection()) {
            // Create a Statement
            Statement stmt = conn.createStatement();
            // Execute a query
            ResultSet rs = stmt.executeQuery("SELECT * FROM `e-commerce`.employees");
            while (rs.next()) {
                int id = rs.getInt("id");
                System.out.println(id);
            }

            // Close resources
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("Connection successful: " + !conn.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
