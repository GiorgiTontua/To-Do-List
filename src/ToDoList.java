import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ToDoList {
    private HashMap<String, String> toDoList;

    public ToDoList(HashMap <String, String> toDoList){
        this.toDoList=toDoList;
    }

    public HashMap<String, String> getToDoList(){
        return toDoList;
    }

    public void setToDoList(HashMap<String, String> toDoList){
        this.toDoList = toDoList;
    }
}
