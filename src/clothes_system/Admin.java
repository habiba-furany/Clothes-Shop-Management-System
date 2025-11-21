
package clothes_system;


public class Admin extends User {
    
    public Admin(String email, String password, double salary, String utype, String id, String name, String contact_info, String type) {
        super(name, contact_info, type,email, password, salary, utype);
    }

    @Override
    public String toString() {
        return "Admin{" + super.toString() +"}";
    }
    
    
}
