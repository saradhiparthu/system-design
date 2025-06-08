package JDBC;

import java.sql.*;

public class BasicJDBC {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/e-commerce?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "root";
        try {
            // Load the JDBC driver (optional in newer versions)
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Create a Connection
            Connection conn = DriverManager.getConnection(url, user, password);
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
