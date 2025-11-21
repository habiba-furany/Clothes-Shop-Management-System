
package clothes_system;


public class Cashier extends User{
    
    public Cashier(String email, String password, double salary, String utype, String name, String contact_info, String type) {
        super(name, contact_info, type, email, password, salary, utype);
    }

    @Override
    public String toString() {
        return "Cashier{" + super.toString() + "}";
    }
    
    
    
}
