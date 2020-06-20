package srp.repositories;

import srp.models.Order;

import java.util.List;

public interface OrderRepository {
    
    void create(Order customer);

    Order find(String id);

    List<Order> findAll();

    Order update(Order post, String id);

    void delete(String id);
    
}