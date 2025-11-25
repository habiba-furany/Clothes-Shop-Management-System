
package clothes_system;

import clothes_system.Person.Type;
import clothes_system.User.Utype;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Clothes_System extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        
          User u = new User("Hanen","0124454",Type.USER,"hanen@gmail.com","h456",15000,Utype.CASHIER);
       if(addUser(u)) System.out.println("sucessful");


    }

    public static boolean addUser(User u){
        String sql ="INSERT INTO Person(Name,Contact_Info,Type) VALUES(?,?,?)";
        try(Connection connection=DBconnector.connect();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,u.getName());
            ps.setString(2,u.getContact_info());
            ps.setString(3,u.getType());
            ps.executeUpdate();
           ResultSet generatedKeys = ps.getGeneratedKeys();
//            int personId = 0;
//            if (generatedKeys.next()) {
//                 personId = generatedKeys.getInt(1);
//            }
            
            String sqlUser = "UPDATE User SET Email = ?, Password = ?, Type = ?, Salary = ? WHERE UID = ?";
            PreparedStatement psUser = connection.prepareStatement(sqlUser);
            
            psUser.setString(1, u.getEmail());
            psUser.setString(2, u.getPassword());
            psUser.setString(3, u.getUtype());
            psUser.setDouble(4, u.getSalary());
            psUser.setInt(5, u.getId());
            psUser.executeUpdate();
                
   
            System.out.println(" missin done ");
            return true;

            }catch(SQLException e){
                e.printStackTrace();
                return false;
            }
    }
    public static void main(String[] args) {
        launch(args);
      
    }
    
}
