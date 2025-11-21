
package clothes_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Clothes_System extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
          User u = new User("Habiba alaa","01211","User","habiba@gmail.com","123h",100000,"Admin");
        if(addUser(u)) System.out.println("sucessful");
    }

    public static boolean addUser(User u){
        String sql ="INSERT INTO User(UID,Email,Password,Type,Salary) VALUES(?,?,?,?,?)";
        try(Connection connection=DBconnector.connect();
            PreparedStatement ps = connection.prepareStatement(sql)){
            
            ps.setString(1,u.getId());
            ps.setString(2,u.getEmail());
            ps.setString(3,u.getPassword());
            ps.setString(4,u.getUtype());
            ps.setDouble(5,u.getSalary());
        
            int affectedRows = ps.executeUpdate();
            
            return affectedRows > 0;
            
            }catch(SQLException e){
                e.printStackTrace();
                return false;
            }
    }
    public static void main(String[] args) {
        launch(args);
      
    }
    
}
