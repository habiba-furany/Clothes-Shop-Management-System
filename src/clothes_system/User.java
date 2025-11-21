
package clothes_system;


public class User extends Person {
    private String email;
    private String password;
    private double salary;
    private String utype;

    public User(String name, String contact_info, String type,String email, String password, double salary, String utype) {
        super( name, contact_info, type);
        this.email = email;
        this.password = password;
        this.salary = salary;
        this.utype = utype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }
    
    
    
}
