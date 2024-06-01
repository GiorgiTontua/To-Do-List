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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//Giorgi Tontua
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
            Label display=new Label("(To-Do list has been displayed.)");
            List <String> toDoItems = repo.displayToDo();
            for (String item : toDoItems) {
                Label itemLabel = new Label(item);

                CheckBox completionCheckBox = new CheckBox();
                completionCheckBox.setOnAction(event -> {
                    boolean completed = completionCheckBox.isSelected();
                    repo.updateTaskCompletion(item, completed);
                });

                HBox taskBox = new HBox(10);
                taskBox.getChildren().addAll(itemLabel, completionCheckBox);
                centerBox.getChildren().add(taskBox);
            }
            centerBox.getChildren().addAll(display);
            });
        viewToDo.setStyle("-fx-min-width: 100px; -fx-min-height: 40px;");

        Button addItem = new Button("Add Item");
        addItem.setOnAction(event -> {
            centerBox.getChildren().remove(addItem);
            centerBox.getChildren().remove(viewToDo);

            Text toDoLabel = new Text("To Do:");
            TextField textField = new TextField();
            textField.setPromptText("Enter task here...");
            Button submit = new Button("Submit");
            submit.setOnAction(e -> {
                String userInputTask = textField.getText();
                repo.addItems(userInputTask);
                textField.clear();
                centerBox.getChildren().removeAll(toDoLabel, textField, submit);
            });
            submit.setStyle("-fx-min-width: 100px; -fx-min-height: 40px;");


            centerBox.getChildren().addAll(viewToDo, addItem, toDoLabel, textField, submit);
        });
        addItem.setStyle("-fx-min-width: 100px; -fx-min-height: 40px;");
        Button deleteItem = new Button("Delete Item");
        deleteItem.setOnAction(e -> {
            centerBox.getChildren().remove(deleteItem);

            TextField deleteTextField = new TextField();
            deleteTextField.setPromptText("Enter task to delete");

            Button confirmDelete = new Button("Delete");
            confirmDelete.setOnAction(event -> {
                String taskToDelete = deleteTextField.getText().trim();
                Label textDelete = new Label("To-Do has been deleted.");
                if (!taskToDelete.isEmpty()) {
                    repo.deleteItem(taskToDelete);
                    List<String> updatedToDoItems = repo.displayToDo();
                    for (String item : updatedToDoItems) {
                        Label itemLabel = new Label(item);
                    }
                }
                centerBox.getChildren().addAll(textDelete);
            });
            confirmDelete.setStyle("-fx-min-width: 100px; -fx-min-height: 40px;");

            List<String> updatedToDoItems = repo.displayToDo();
            for (String item : updatedToDoItems) {
                Label itemLabel = new Label(item);
                centerBox.getChildren().add(itemLabel);
            }

            centerBox.getChildren().addAll(deleteTextField, confirmDelete);
        });
        deleteItem.setStyle("-fx-min-width: 100px; -fx-min-height: 40px;");

        Button clearToDo=new Button("Clear To-Do");
        clearToDo.setOnAction(e -> {
            repo.clearToDo();
            Label clearMessage = new Label("To-Do list has been cleared.");
            centerBox.getChildren().addAll(clearMessage);
        });
        clearToDo.setStyle("-fx-min-width: 100px; -fx-min-height: 40px;");

        centerBox.getChildren().addAll(viewToDo, addItem, deleteItem, clearToDo);
        borderPane.setCenter(centerBox);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(10);
        topBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(borderPane, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("To-Do List");

    }
}
