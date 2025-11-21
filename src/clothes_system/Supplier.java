
package clothes_system;


public class Supplier extends Person{
    
    public Supplier( String name, String contact_info, String type) {
        super( name, contact_info, type);
    }

    @Override
    public String toString() {
        return "Supplier{" + super.toString() + '}';
    }
    
    
}
