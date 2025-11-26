/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clothes_system;
import static clothes_system.DBconnector.connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
/**
 *
 * @author soft zone
 */
public class Reports {
     private static Connection connect;
    private static PreparedStatement prepare;
    private static ResultSet result;
    public static ResultSet topRevenueUser() throws SQLException{
        Connection conn = DBconnector.connect();
        String query = "SELECT P.Name, P.Contact_Info, COUNT(O.OID) AS no_OF_Orders,SUM(Total_Price) AS total_Price\n" +
                       "FROM Person P\n" +
                       "INNER JOIN Orders O ON O.CID = P.ID\n" +
                       "GROUP BY P.ID\n" +
                       "ORDER BY no_OF_Orders DESC\n" +
                       "LIMIT 1;";
        PreparedStatement prst = conn.prepareStatement(query);
        ResultSet rs = prst.executeQuery();
        return rs;
    }
        public static ResultSet topOrderUser() throws SQLException{
            Connection conn = DBconnector.connect();
            String query= "SELECT P.Name, P.Contact_Info, COUNT(O.OID) AS no_OF_Orders\n" +
                           "FROM Person P\n" +
                           "INNER JOIN Orders O ON O.CID = P.ID\n" +
                           "GROUP BY P.ID\n" +
                           "ORDER BY no_OF_Orders DESC\n" +
                           "LIMIT 1;";
            PreparedStatement prst = conn.prepareStatement(query);
            ResultSet rs = prst.executeQuery();
            return rs;

        }
        public static ResultSet allOrdersInYears() throws SQLException{
            Connection conn = DBconnector.connect();
            String query= "SELECT SUBSTR(DATE,-4) AS year,COUNT(ID) AS no_of_orders\n" +
                          "FROM Orders\n" +
                          "Group BY year";
            PreparedStatement prst = conn.prepareStatement(query);

            
            ResultSet rs = prst.executeQuery();
            return rs;
            
        }
        public static ResultSet totalPriceInYears() throws SQLException{
             Connection conn = DBconnector.connect();
             String query= "SELECT \n" +
                           "    SUBSTR(DATE,-4) AS year,\n" +
                           "    SUM(Total_price) AS total_year_price\n" +
                           "FROM Orders\n" +
                           "GROUP BY year;";
            PreparedStatement prst = conn.prepareStatement(query);

            ResultSet rs = prst.executeQuery();
            return rs;

        }
        public static ResultSet payment_Methods_Prices() throws SQLException{
            Connection conn = DBconnector.connect();
            String query="SELECT Payment_Method,SUM(Total_Price) AS Payment_Prices\n" +
                         "FROM Orders\n" +
                         "GROUP BY Payment_Method\n" +
                         "ORDER BY Payment_Prices DESC;";
            PreparedStatement prst = conn.prepareStatement(query);
            ResultSet rs = prst.executeQuery();
            return rs;
         
        }
        public static ResultSet getTopNSoldProducts() {
    
    
   // Map<Integer, Integer> topProducts = new LinkedHashMap<>();
    
    
    String sql = "SELECT P.ID , sum(O.Desired_Quantity) as TotalSoldQty\n" +
                 "FROM Product as P INNER JOIN OrderItem as O \n" +
                "ON P.ID=O.PID GROUP BY P.ID,P.Name ORDER by TotalSoldQty DESC  ;";
    
    try{
          connect=DBconnector.connect();
          prepare = connect.prepareStatement(sql);
          //prepare.setInt(1, limit);
          result=  prepare.executeQuery();
          
      
      } catch(SQLException e){
       e.printStackTrace();
      }
    return result;

    }
       public static ResultSet bestSupplier(){

     
    String sql ="SELECT P.SID,s.Name ,sum(O.Desired_Quantity) as TotalSoldQty \n" +
                "FROM Product as P INNER JOIN OrderItem as O ON P.ID=O.PID \n" +
                "INNER join Person s WHERE s.ID=P.SID GROUP BY P.SID ORDER by TotalSoldQty DESC LIMIT 1;";
      try{
          connect=DBconnector.connect();
          prepare = connect.prepareStatement(sql);
          result=  prepare.executeQuery();

      } catch(SQLException e){
       e.printStackTrace();
      }
      
      return result;
    
    }
       public static ResultSet totalRevenue(){ 
     
    String sql ="SELECT sum(O.Total_Price) as total_revenue FROM Orders as O";
      try{
          connect=DBconnector.connect();
          prepare = connect.prepareStatement(sql);
          result=  prepare.executeQuery();
      
      
      } catch(SQLException e){
       e.printStackTrace();
      }
      
      return result;
    
    }
       public static ResultSet LowStockAlert(){
 
     
    String sql ="SELECT P.ID , P.Name from product as P where P.Quantity < 6";
      try{
          connect=DBconnector.connect();
          prepare = connect.prepareStatement(sql);
          result=  prepare.executeQuery();

      
      } catch(SQLException e){
       e.printStackTrace();
      }
      
      return result;
    
    }




        
    
}
