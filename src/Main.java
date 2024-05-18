import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Scanner;



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

import java.util.HashMap;

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
        Button viewToDoBeginning = new Button("View to do");
        /*viewToDoBeggining.setOnAction(e -> {
            repo.displayToDo(ToDoList, centerBox);
        }); */

        Button addItem = new Button("Add item");
        addItem.setOnAction(event ->{
            centerBox.getChildren().remove(addItem);
           // centerBox.getChildren().remove(viewToDoBeggining);
            Button viewToDo = new Button("View to do");
            /*viewToDo.setOnAction(e -> {
                repo.displayToDo(toDoList, centerBox);

            }); */
            Text toDoLabel = new Text("To Do:");
            TextArea textArea = new TextArea();
            textArea.setPromptText("Enter text here... ");
            Button submit=new Button("Submit");
            submit.setOnAction(e -> {
                String userInputTask = textArea.getText();
                System.out.println(userInputTask);
                repo.addItems(userInputTask, false);
                textArea.clear();
            });

            centerBox.getChildren().addAll(viewToDoBeginning, addItem);
        });

        centerBox.getChildren().addAll(viewToDoBeginning, addItem);
        borderPane.setCenter(centerBox);

        Scene scene = new Scene(borderPane, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();






    }
}
