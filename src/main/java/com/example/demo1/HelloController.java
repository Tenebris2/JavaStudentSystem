package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;

public class HelloController {

    //Connection
    @FXML
    static Connection connection;

    //loginPage
    @FXML
    private TextField usernameTextField;
    @FXML
    private Button registerButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label usernameWarning;
    @FXML
    private Label passwordWarning;
    @FXML
    private Button loginButton;
    @FXML
    private Button enterButton;


    static void createConnecion()
    {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","12345678");
            System.out.println("Database connection sucessful!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void registerButton(ActionEvent e)
    {
        createConnecion();
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        Statement checkStatement;
        try {
            //check statement
            checkStatement = connection.createStatement();
            String rs_statement = "SELECT * FROM user where username = '"+username+"'";
            ResultSet rs = checkStatement.executeQuery(rs_statement);

            if(username.trim().isEmpty() || password.trim().isEmpty())//trim removes whitespaces
            {
                usernameWarning.setText("Required.");
                passwordWarning.setText("Please enter your password!");
                return;
            }
            else if (!rs.next())
            {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO user VALUES(?,?)");
                statement.setString(1,username);
                statement.setString(2,password);
                statement.execute();
                usernameTextField.setText("");
                passwordField.setText("");
                statement.close();
            }
            else
            {
                usernameWarning.setText("Please enter another username!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void loginButton(ActionEvent e)
    {
        createConnecion();
        SceneController sceneController = new SceneController();
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        Statement statement;
        System.out.println("Great");

        HelloApplication app = new HelloApplication();

        try {
            statement = connection.createStatement();
            String rs_statement = "SELECT * FROM user where username = '"+username+"' and password='"+ password + "'";
            ResultSet rs = statement.executeQuery(rs_statement);
            if (rs.next())
            {
                sceneController.switchToHomePage(e);
            }
            else
            {
                usernameWarning.setText("Please enter another username or password!");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}

