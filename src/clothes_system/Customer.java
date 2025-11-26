
package clothes_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Customer extends Person {
    
    public Customer( String name, String contact_info, Type type) {
        super( name, contact_info, type);
    }

    private Customer() {    }
    

    
    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:System.db");
    }

    
    public static boolean addCustomer(Customer c) {
        String sql = "INSERT INTO Person(Name, Contact_Info, Type) VALUES(?, ?, 'CUSTOMER')";
        try (Connection connection = DBconnector.connect();
            PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, c.getName());
            pst.setString(2, c.getContact_info());
            pst.executeUpdate();
            
            ResultSet keys = pst.getGeneratedKeys();
            int personId = 0 ;
            if (keys.next()){
                personId = keys.getInt(1);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
    
   public List<Customer> searchCustomer(String key) {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer WHERE name LIKE ? OR Contact_info LIKE ?";
        try (Connection connection = DBconnector.connect();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            String N = "%" + key + "%";
            pst.setString(1, N);
            pst.setString(2, N);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Customer c = new Customer();
                    c.setName(rs.getString("Name"));
                    c.setContact_info(rs.getString("contact_info"));

                    list.add(c);
                }
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
   
   public static List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer ";

        try (Connection connection = DBconnector.connect();
             PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Customer c = new Customer();
                c.setName(rs.getString("Name"));
                c.setContact_info(rs.getString("contact_info"));
                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    return list;
}
   
   
   // Update customer info
    public static boolean updateCustomer(Customer c) {
        String sql = "UPDATE Person SET Name = ?, Contact_Info = ? WHERE ID = ?";
        try (Connection connection = DBconnector.connect();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, c.getName());
            pst.setString(2, c.getContact_info());
            pst.setInt(3, c.getId());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Customer updated successfully");
                return true;
            } else {
                System.out.println("Customer not found with ID: " + c.getId());
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update customer");
            return false;
        }
}

    public static boolean deleteCustomer(int customerId) {
        try (Connection connection = DBconnector.connect()) {
            connection.setAutoCommit(false);

            String sqlCustomer = "DELETE FROM Customer WHERE CID = ?";
            try (PreparedStatement pstCust = connection.prepareStatement(sqlCustomer)) {
                pstCust.setInt(1, customerId);
                pstCust.executeUpdate();
            }

            String sqlPerson = "DELETE FROM Person WHERE ID = ?";
            try (PreparedStatement pstPerson = connection.prepareStatement(sqlPerson)) {
                pstPerson.setInt(1, customerId);
                pstPerson.executeUpdate();
            }

            connection.commit();
            System.out.println("Customer deleted successfully from both tables");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete customer");
            return false;
        }
    }

    
    
  /*  public List<Order> loadOrdersByCustomerId(int customerId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE customerId = ?";
        try (Connection conn = DBconnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order(rs.getDate("Date"),(float) rs.getDouble("Discount"),Order.PaymentMethod.valueOf(rs.getString("Payment_Method")),rs.getDouble("Calculated_Price"),rs.getDouble("Total_Price"),null);
                o.setId(rs.getInt("ID"));
                // بعدين حمل OrderItems لهذا الـ Order من جدول OrderItem
                o.setOrderItems(loadOrderItemsByOrderId(o.getId()));
                list.add(o);
            }
        } 
        catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return list;
    }*/

    @Override
    public String toString() {
        return "Customer{" + super.toString()+ '}';
    }
    
    
}
