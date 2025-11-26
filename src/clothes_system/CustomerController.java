package clothes_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.Node;
import java.io.IOException;

public class CustomerController {

    @FXML
    private Button btnViewCustomers;

    @FXML
    private Button btnSearchCustomers;

    @FXML
    private Button btnManageCustomers;

    @FXML
    private StackPane contentArea;

    @FXML
    private void initialize() {

        // Load "View Customers" as default page
        loadPage("ViewCustomers.fxml");

        btnViewCustomers.setOnAction(e -> loadPage("ViewCustomers.fxml"));
        btnSearchCustomers.setOnAction(e -> loadPage("SearchCustomer.fxml"));
        btnManageCustomers.setOnAction(e -> loadPage("ManageCustomer.fxml"));
    }

    private void loadPage(String fxml) {
        try {
            Node node = FXMLLoader.load(getClass().getResource(fxml));
            contentArea.getChildren().setAll(node);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
