package srp.models.interfaces;

import srp.models.*;

public interface IOrderItem {
    Order order = null;
    Product product = null;
    Integer quantity = null;
    Double price = null;

    public Double calculatePrice();

    public Order getOrder();

    public void setOrder(Order order);

    public Product getProduct();

    public Integer getQuantity();

    public Double getPrice();

    // impuesto
    public Double getTaxItem(Double price);

}