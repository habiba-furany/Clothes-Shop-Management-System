/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clothes_system;

import static clothes_system.Reports.LowStockAlert;
import static clothes_system.Reports.allOrdersInYears;
import static clothes_system.Reports.bestSupplier;
import static clothes_system.Reports.getTopNSoldProducts;
import static clothes_system.Reports.payment_Methods_Prices;
import static clothes_system.Reports.topOrderUser;
import static clothes_system.Reports.topRevenueUser;
import static clothes_system.Reports.totalPriceInYears;
import static clothes_system.Reports.totalRevenue;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * FXML Controller class
 *
 * @author soft zone
 */
public class AdminController implements Initializable {

 @FXML
    private Text top_Price_Result;
    public void getReportText() throws SQLException {
    ResultSet rs = topRevenueUser(); 

    StringBuilder sb = new StringBuilder();
    while(rs.next()) {
        String name = rs.getString("Name");
        int price = rs.getInt("Total_Price");
        sb.append("Customer: ").append(name)
          .append("\n") 
          .append("\n")      
          .append("Total Price: ").append(price)
          .append("\n");
    }
    top_Price_Result.setText(sb.toString());
    top_Price_Result.setFont(Font.font("Arial", 18));
    top_Price_Result.setFill(Color.web("#766c6c"));
}
    @FXML
    private Text top_Order_Result;
    public void getReport2Text() throws SQLException {
    ResultSet rs = topOrderUser(); 

    StringBuilder sb = new StringBuilder();
    while(rs.next()) {
        String name =rs.getString("Name");
        String contact = rs.getString("Contact_Info");
        int no_Orders = rs.getInt("no_OF_Orders");
        sb.append("Customer: ").append(name)
          .append("\n") 
          .append("\n")      
          .append("Contact Information: ").append(contact)
          .append("\n")
          .append("\n")
          .append("Number Of Orders: ").append(no_Orders);
        
    }
    top_Order_Result.setText(sb.toString());
    top_Order_Result.setFont(Font.font("Arial", 18));
    top_Order_Result.setFill(Color.web("#766c6c"));

}
    @FXML
    private Text top_Supplier;
    public void getReport4Text() throws SQLException {
    ResultSet rs = bestSupplier(); 

    StringBuilder sb = new StringBuilder();
    if(rs.next()) {
        int upplierID = rs.getInt("SID");
        int soldQTY   =rs.getInt("TotalSoldQty");
        String supplierName = rs.getString("Name");
    
        sb.append(" Supplier ID: ").append(upplierID)   
           .append("\n")
          .append(" Supplier Name: ").append(supplierName)
          .append("\n") 
          .append(" Sold Quantity: ").append(soldQTY)
          .append("\n");
        
    }
    top_Supplier.setText(sb.toString());
    top_Supplier.setFont(Font.font("Arial", 18));
    top_Supplier.setFill(Color.web("#766c6c"));

}
    @FXML
    private Text payment_Method_Rep;
    public void getReport3Text() throws SQLException {
    ResultSet rs = payment_Methods_Prices(); 

    StringBuilder sb = new StringBuilder();
    while(rs.next()) {
        String payment = rs.getString("Payment_Method");
        double payment_Price = rs.getDouble("Payment_Prices");
    
        sb.append("Payment Method: ").append(payment) 
          .append("\n")      
          .append("Payment Total Price: ").append(payment_Price)
          .append("\n");
    
        
    }
    payment_Method_Rep.setText(sb.toString());
    payment_Method_Rep.setFont(Font.font("Arial", 18));
    payment_Method_Rep.setFill(Color.web("#766c6c"));

}
    
    @FXML
    private Text Total_Revenue;
    public void getReport5Text() throws SQLException {
    ResultSet rs = totalRevenue(); 

    StringBuilder sb = new StringBuilder();
    if(rs.next()) {
        int price =rs.getInt("total_revenue");
    
        sb.append(" Total Revenue For All Orders: ").append(price)   ;
          
        
    }
    Total_Revenue.setText(sb.toString());
    Total_Revenue.setFont(Font.font("Arial", 18));
    Total_Revenue.setFill(Color.web("#766c6c"));

}
    
    @FXML
    private Text alert;
    public void getReport6Text() throws SQLException {
    ResultSet rs =LowStockAlert(); 

    StringBuilder sb = new StringBuilder();
    while(rs.next()) {
        int ID = rs.getInt("ID");
        String name = rs.getString("Name");
    
        sb.append(" Product ID: ").append(ID)
                .append("\n")
                .append(" Product Name:").append(name)
                .append("\n");

          
        
    }
    alert.setText(sb.toString());
    alert.setFont(Font.font("Arial", 18));
    alert.setFill(Color.web("#766c6c"));

}
    
    @FXML
    private AnchorPane reportsPane;
    @FXML
    private void reportsClicked() throws SQLException{
        reportsPane.setVisible(true); 
    }
   @FXML
private BarChart<String, Number> prices_Chart; 

@FXML
private void loadSalesData() throws SQLException {
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Sales");

    ResultSet rs = totalPriceInYears();
    while (rs.next()) {
        String year = rs.getString("year");
        double price = rs.getDouble("total_year_price");
        series.getData().add(new XYChart.Data<>(year, price));
    }

    prices_Chart.getData().add(series);

    for (XYChart.Series<String, Number> s : prices_Chart.getData()) {
        for (XYChart.Data<String, Number> data : s.getData()) {
            data.getNode().setStyle("-fx-bar-fill: #E2EAF4;");
        }
    }
}
@FXML
private BarChart<String, Number> Orders_Chart; 

@FXML
private void loadOrdersData() throws SQLException {
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Orders");

    ResultSet rs = allOrdersInYears();
    while (rs.next()) {
        String year = rs.getString("year");
        int number = rs.getInt("no_of_orders");
        series.getData().add(new XYChart.Data<>(year, number));
    }

    Orders_Chart.getData().add(series);

    for (XYChart.Series<String, Number> s : Orders_Chart.getData()) {
        for (XYChart.Data<String, Number> data : s.getData()) {
            data.getNode().setStyle("-fx-bar-fill: #E2EAF4;");
        }
    }
}
@FXML
private BarChart<String, Number> Seller_Chart; 

@FXML
private void loadSellerData() throws SQLException {
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Seller");

    ResultSet rs =getTopNSoldProducts();
    while (rs.next()) {
        int productId = rs.getInt("ID");
        int quantity = rs.getInt("TotalSoldQty");
        series.getData().add(new XYChart.Data<>(String.valueOf(productId), quantity));
    }

    Seller_Chart.getData().add(series);

    for (XYChart.Series<String, Number> s : Seller_Chart.getData()) {
        for (XYChart.Data<String, Number> data : s.getData()) {
            data.getNode().setStyle("-fx-bar-fill: #E2EAF4;");
        }
    }
}
@FXML
private AnchorPane anchorReport;
@FXML
private void reportsDisplay() throws SQLException{
    anchorReport.setVisible(true);
    
}
@FXML
private void intializeReports() throws SQLException{
    getReportText(); 
        getReport2Text();
        getReport3Text();
        getReport4Text();
        getReport5Text();
        getReport6Text();
        loadSalesData();
        loadOrdersData();
        loadSellerData();
}



    
    
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     try {
         intializeReports();
         anchorReport.setVisible(false);
     } catch (SQLException ex) {
         Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
     }
        
    }    
         

    
    
}
