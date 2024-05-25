import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;
import java.sql.Date;


import com.sun.webkit.dom.XPathResultImpl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

//Giorgi Tontuaa
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Repository repo = new Repository();
        ToDoList toDoList = new ToDoList(new HashMap<>());

        BorderPane borderPane = new BorderPane();
        HBox topBox = new HBox();
        Label heading = new Label("Welcome to my To Do list!");
        heading.setPadding(new Insets(20));
        topBox.getChildren().add(heading);
        topBox.setPrefHeight(100);
        borderPane.setTop(topBox);

        VBox centerBox = new VBox(10);
        centerBox.setPadding(new Insets(20));
        Button viewToDo = new Button("View ToDo List");
        viewToDo.setOnAction(e -> {
            List <String> toDoItems = repo.displayToDo();
            centerBox.getChildren().clear();
            for (String item : toDoItems) {
                Label itemLabel = new Label(item);
                centerBox.getChildren().add(itemLabel);
            }
            });

        Button addItem = new Button("Add Item");
        addItem.setOnAction(event -> {
            centerBox.getChildren().remove(addItem);
            centerBox.getChildren().remove(viewToDo);

            Button viewToDoAgain = new Button("View To-Do List");
            viewToDoAgain.setOnAction(e -> {
                List<String> updatedToDoItems = repo.displayToDo();
                centerBox.getChildren().clear();
                for (String item : updatedToDoItems) {
                    Label itemLabel = new Label(item);
                    centerBox.getChildren().add(itemLabel);
                }
            });

            Text toDoLabel = new Text("To Do:");
            TextArea textArea = new TextArea();
            textArea.setPromptText("Enter task here...");
            Button submit = new Button("Submit");
            submit.setOnAction(e -> {
                String userInputTask = textArea.getText();
                repo.addItems(userInputTask);
                textArea.clear();
                centerBox.getChildren().removeAll(toDoLabel, textArea, submit);
            });


            centerBox.getChildren().addAll(viewToDo, addItem, toDoLabel, textArea, submit);
        });
        Button deleteItem = new Button("Delete Item");
        deleteItem.setOnAction(e -> {
            centerBox.getChildren().remove(deleteItem);

            TextField deleteTextField = new TextField();
            deleteTextField.setPromptText("Enter task to delete");

            Button confirmDelete = new Button("Delete");
            confirmDelete.setOnAction(event -> {
                String taskToDelete = deleteTextField.getText().trim();
                if (!taskToDelete.isEmpty()) {
                    repo.deleteItem(taskToDelete);
                    List<String> updatedToDoItems = repo.displayToDo();
                    centerBox.getChildren().clear();
                    for (String item : updatedToDoItems) {
                        Label itemLabel = new Label(item);
                        centerBox.getChildren().add(itemLabel);
                    }
                }
            });

            List<String> updatedToDoItems = repo.displayToDo();
            for (String item : updatedToDoItems) {
                Label itemLabel = new Label(item);
                centerBox.getChildren().add(itemLabel);
            }

            centerBox.getChildren().addAll(deleteTextField, confirmDelete);
        });

        centerBox.getChildren().addAll(viewToDo, addItem, deleteItem);
        borderPane.setCenter(centerBox);

        Scene scene = new Scene(borderPane, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();






    }
}
