
package clothes_system;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


public class AdminController implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private Button myButton2; 

    @FXML
    private Button productButton, myButton1, myButton3, myButton4, myButton5 ,AddUserButton ,EditUserButton ,DelUserButton;

    @FXML
    private AnchorPane centerPane; 
    
    @FXML
    private TableView<EmployeeRow> employeeTable;

    @FXML
    private TableColumn<EmployeeRow, Integer> colID;
    @FXML
    private TableColumn<EmployeeRow, String> colName;
    @FXML
    private TableColumn<EmployeeRow, String> colPhone;
    @FXML
    private TableColumn<EmployeeRow, String> colEmail;
    @FXML
    private TableColumn<EmployeeRow, Double> colSalary;
    @FXML
    private TableColumn<EmployeeRow, String> colRole;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         employeeTable.setVisible(false);
         AddUserButton.setVisible(false);
         EditUserButton.setVisible(false);        
         DelUserButton.setVisible(false);     
         searchField.setVisible(false);

        // لما تضغط على زرار الموظفين يظهر الجدول
        myButton2.setOnAction(event -> {
            employeeTable.setVisible(true);
             AddUserButton.setVisible(true);
         EditUserButton.setVisible(true);        
         DelUserButton.setVisible(true); 
                  searchField.setVisible(true);

        });

        // أمثلة: ممكن تخفي الجدول لما تضغط على زرار تاني
        myButton1.setOnAction(event -> employeeTable.setVisible(false));
        myButton3.setOnAction(event -> employeeTable.setVisible(false));
        myButton4.setOnAction(event -> employeeTable.setVisible(false));
        myButton5.setOnAction(event -> employeeTable.setVisible(false));
        productButton.setOnAction(event -> employeeTable.setVisible(false));
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colID.setStyle("-fx-alignment: CENTER;");
colName.setStyle("-fx-alignment: CENTER;");
colPhone.setStyle("-fx-alignment: CENTER;");
colEmail.setStyle("-fx-alignment: CENTER;");
colSalary.setStyle("-fx-alignment: CENTER;");
colRole.setStyle("-fx-alignment: CENTER;");


        ObservableList<EmployeeRow> data = FXCollections.observableArrayList();
        data.add(new EmployeeRow(1, "Amal Ragab", "0123456789", "amal@example.com", 5000, "Cashier"));
        data.add(new EmployeeRow(2, "Sara Ahmed", "0112233445", "sara@example.com", 4000, "Cashier"));
        data.add(new EmployeeRow(3, "Mohamed Ali", "0109988776", "mohamed@example.com", 4500, "Cashier"));

        employeeTable.setItems(data);
    }
     public static class EmployeeRow {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty name;
        private final SimpleStringProperty phone;
        private final SimpleStringProperty email;
        private final SimpleDoubleProperty salary;
        private final SimpleStringProperty role;

        public EmployeeRow(int id, String name, String phone, String email, double salary, String role) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.phone = new SimpleStringProperty(phone);
            this.email = new SimpleStringProperty(email);
            this.salary = new SimpleDoubleProperty(salary);
            this.role = new SimpleStringProperty(role);
        }

        public int getId() { return id.get(); }
        public String getName() { return name.get(); }
        public String getPhone() { return phone.get(); }
        public String getEmail() { return email.get(); }
        public double getSalary() { return salary.get(); }
        public String getRole() { return role.get(); }
    }
    }    
    
