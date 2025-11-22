/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clothes_system;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 * @author soft zone
 */
import clothes_system.DBconnector;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignInController implements Initializable {
    
    
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passField;
    @FXML
    private Text signInText;
    @FXML
    private Text emailText;
    @FXML
    private Text passText;
    @FXML
    private Button signInBtn;
    

    @FXML
    private void signInAction() throws IOException {
        String email = emailField.getText();
        String password = passField.getText();
        try {
        Connection conn = DBconnector.connect(); // دالة الاتصال بالداتابيز

        String sql = "SELECT Email, Password, Type FROM \"User\" WHERE Email = ? AND Password = ?";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, email);
        pst.setString(2, password);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String typeDB = rs.getString("Type");
            System.out.println("Login successful! User type: " + typeDB);
            if(typeDB.equalsIgnoreCase("ADMIN")){
                chooseAdmin();
            }else{
                chooseCashier();
            }
        } else {
            System.out.println("Wrong email or password!");
        }

        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
   
    }
    private void chooseAdmin() throws IOException{
         Stage stage = (Stage) signInBtn.getScene().getWindow();
        double width = stage.getWidth();
        double height = stage.getHeight();

        Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        Scene scene = new Scene(root, width, height); 
        stage.setScene(scene);
        stage.show();

        
    }
    private void chooseCashier() throws IOException{
        Stage stage = (Stage) signInBtn.getScene().getWindow();
        double width = stage.getWidth();
        double height = stage.getHeight();

        Parent root = FXMLLoader.load(getClass().getResource("Cashier.fxml"));
        Scene scene = new Scene(root, width, height); 
        stage.setScene(scene);
        stage.show();
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}
