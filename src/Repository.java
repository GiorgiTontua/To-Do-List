import java.sql.*;

public class Repository {
    private String url = "jdbc:mysql://localhost:3306/mziuri";
    private String userName = "root";
    private String password = "Giorgi_123";

    public void addItems(String taskadd, Boolean isCompleted) {
        String sql = "INSERT INTO interactions (int_name, is_completed) VALUES (?, ?);";
        try (
                Connection con = DriverManager.getConnection(url, userName, password);
                PreparedStatement pr = con.prepareStatement(sql);

        ) {
            pr.setString(1, taskadd);
            pr.setBoolean(2, isCompleted);

            int rowsAffected = pr.executeUpdate();
            if (rowsAffected > 0)
                System.out.println("Added successfully.");
            else
                System.out.println("Adding failed.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
