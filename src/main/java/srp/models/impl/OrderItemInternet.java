package srp.models.impl;

import srp.models.Order;
import srp.models.Product;
import srp.models.interfaces.IOrderItem;

public class OrderItemInternet implements IOrderItem {
    private Order order;
    private Product product;
    private Integer quantity;
    private Double price;

    @Override
    public Double calculatePrice() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Order getOrder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double getPrice() {
        // Intenet Promocion
        Double customPrice = 0.0;
        customPrice = this.price * this.quantity * 0.85;
        return customPrice;
    }

    @Override
    public Product getProduct() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getQuantity() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setOrder(Order order) {
        // TODO Auto-generated method stub

    }

    @Override
    public Double getTaxItem(Double price) {
        return price * 0.18;
    }

}