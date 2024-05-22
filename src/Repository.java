import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> displayToDo() {
        List<String> toDoItems = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(url, userName, password);
            String sql = "SELECT * FROM INTERACTIONS";
            PreparedStatement pr = con.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                String taskContent = rs.getString("int_name");
                boolean is_completed = rs.getBoolean("is_completed");
                String status = (is_completed) ? "[x]" : "[ ]";
                toDoItems.add(taskContent + " " + status);
            }
            rs.close();
            pr.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toDoItems;
    }
}
