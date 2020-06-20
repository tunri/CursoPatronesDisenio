package srp.repositories;

import srp.models.Product;

import java.util.List;

public interface ProductRepository {
    void create(Product product);

    Product find(String id);

    List<Product> findAll();

    Product update(Product post, String id);

    void delete(String id);
}