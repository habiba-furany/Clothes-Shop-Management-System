
package clothes_system;



public class Product {
    public enum Status { AVAILABLE ,NOT_AVAILABLE}
    
    private int id;
    private String name;
    private double price;
    private int quantity;
    private Status status;
    private Supplier supplier;

    public Product( int id,Supplier supplier, String name, double price, int quantity, Status status) {
        this.id = id;
        this.supplier=supplier;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status.name();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ", status=" + status + '}';
    }
    
    
}
