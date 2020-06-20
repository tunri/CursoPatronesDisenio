package srp.models;
import java.util.ArrayList;
import java.util.List;

import srp.models.interfaces.IOrderItem;


public class Order {
    private String id;
    private Double price;
    private String address;
    private String courier;

    private String customer;


    public List<IOrderItem> getOrderItems() {
        List<IOrderItem> ordersItems = new ArrayList<>();
        /*
            Get logic, ORM, SQL
        */
        return ordersItems;
    }

    public Double calculateTotalOrder() {
        List<IOrderItem> ordersItems = this.getOrderItems();

        Double totalPrice = 0.0;

        for (IOrderItem item : ordersItems) {
            totalPrice += item.getPrice();
        }

        return totalPrice;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }
}