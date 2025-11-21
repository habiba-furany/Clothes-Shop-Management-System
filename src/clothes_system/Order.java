
package clothes_system;

import java.util.Date;
import java.util.UUID;


public class Order {
    public enum PaymentMethod {CASH,CREDIT}
    private String id;
    private Date date;
    private float disount;
    private PaymentMethod payment_method;
    private double calculated_price;
    private double total_price;

    public Order( Date date, float disount, PaymentMethod payment_method, double calculated_price, double total_price) {
        this.id = UUID.randomUUID().toString();
        this.date = date;
        this.disount = disount;
        this.payment_method = payment_method;
        this.calculated_price = calculated_price;
        this.total_price = total_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getDisount() {
        return disount;
    }

    public void setDisount(float disount) {
        this.disount = disount;
    }

    public PaymentMethod getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(PaymentMethod payment_method) {
        this.payment_method = payment_method;
    }

    public double getCalculated_price() {
        return calculated_price;
    }

    public void setCalculated_price(double calculated_price) {
        this.calculated_price = calculated_price;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", date=" + date + ", disount=" + disount + ", payment_method=" + payment_method + ", calculated_price=" + calculated_price + ", total_price=" + total_price + '}';
    }
    
    
    
}
