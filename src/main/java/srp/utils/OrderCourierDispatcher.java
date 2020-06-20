package srp.utils;

import srp.models.Customer;
import srp.models.Order;

public class OrderCourierDispatcher {

    private Order order;
    private String customer;
    private String courier;

    public OrderCourierDispatcher(Order order, String customer) {
        this.order = order;
        this.customer = customer;
        this.setCourier();
    }

    public OrderCourierDispatcher(Order order) {
        this.order = order;
        this.customer = order.getCustomer();
        this.setCourier();
    }

    public String searchNearestCourier() {
        /*
            DB GIS logic here
            Use latlon customer address and latlon main order address
        */
        String courierId = "LaFldsmdfr";
        return courierId;
    }

    public void setCourier() {
        /*
            Aviability logic here,
            Is active? Is battery full? etc
        */
        this.courier = this.searchNearestCourier();
    }

    public String getBestCourier() {
        return this.courier;
    }
    
}