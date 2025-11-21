
package clothes_system;
import java.util.UUID;

public class Person {
    private String id;
    private String name;
    private String contact_info;
    private String type;

    public Person(String name, String contact_info, String type) {
//        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.contact_info = contact_info;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_info() {
        return contact_info;
    }

    public void setContact_info(String contact_info) {
        this.contact_info = contact_info;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", name=" + name + ", contact_info=" + contact_info + ", type=" + type + '}';
    }
    
    
    
}
