
package clothes_system;


public class OrderItems {
    private int desired_quantity;
    private double total_price;

    public OrderItems(int desired_quantity, double total_price) {
        this.desired_quantity = desired_quantity;
        this.total_price = total_price;
    }

    public int getDesired_quantity() {
        return desired_quantity;
    }

    public void setDesired_quantity(int desired_quantity) {
        this.desired_quantity = desired_quantity;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    @Override
    public String toString() {
        return "OrderItems{" + "desired_quantity=" + desired_quantity + ", total_price=" + total_price + '}';
    }
    
    
}
