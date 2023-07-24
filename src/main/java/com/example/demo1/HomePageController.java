package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    //Connection
    @FXML
    static Connection connection;

    //search bar
    @FXML
    private TextField searchBarTextField;

    //table
    @FXML
    private TableColumn<User, String> BirthdayCol;
    @FXML
    private TableColumn<User, String> Student_ID;

    ObservableList<User> list;

    @FXML
    private TableColumn<User, String> name;

    @FXML
    private TableView<User> table;

    //deco
    @FXML
    private Circle image_circle;



    static void createConnecion()
    {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","12345678");
            System.out.println("Database connection sucessful!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        name.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        Student_ID.setCellValueFactory(new PropertyValueFactory<User,String>("Student_ID"));
        BirthdayCol.setCellValueFactory(new PropertyValueFactory<User,String>("Birthday"));
    }

    public void show(KeyEvent e)
    {
        if(e.getCode() == KeyCode.ENTER) {
            createConnecion();
            String stdID = null;
            String birthDate = null;
            String name_ = null;
            try {
                String sql = "SELECT * from userInfo where Student_ID = '"+ searchBarTextField.getText() + "' ";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    stdID = resultSet.getString("Student_ID");
                    name_ = resultSet.getString("Name");
                    birthDate = resultSet.getString("Birthdate");
                }
            } catch (Exception err) {
                err.printStackTrace();
            }

            list = FXCollections.observableArrayList(
                    new User(stdID,name_, birthDate)
            );
            table.setItems(list);
        }
    }

}
