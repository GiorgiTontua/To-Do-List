import com.mysql.cj.x.protobuf.MysqlxPrepare;

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

    public void addItems(String taskadd) {
        String sql = "INSERT INTO interactions (int_name) VALUES (?);";
        try (
                Connection con = DriverManager.getConnection(url, userName, password);
                PreparedStatement pr = con.prepareStatement(sql);
        ) {
            pr.setString(1, taskadd);
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
                toDoItems.add(taskContent);
            }
            rs.close();
            pr.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toDoItems;
    }

    public void deleteItem(String itemToDelete){
        try{
            Connection con = DriverManager.getConnection(url, userName, password);
            String sql = "DELETE FROM interactions WHERE int_name = ?";
            PreparedStatement pr=con.prepareStatement(sql);
            pr.setString(1, itemToDelete);
            int rowsAffected = pr.executeUpdate();
            pr.close();
            con.close();
            if (rowsAffected > 0) {
                System.out.println("ToDo was deleted successfully.");
            } else {
                System.out.println("ToDo with the specified name was not found. No deletion occurred.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearToDo(){
        try{
            Connection con = DriverManager.getConnection(url, userName, password);
            String sql = "TRUNCATE interactions";
            PreparedStatement pr=con.prepareStatement(sql);
            int rowsAffected = pr.executeUpdate();
            pr.close();
            con.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

