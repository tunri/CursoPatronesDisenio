package srp.controllers;

import srp.config.Paths;

import srp.models.Product;
import srp.repositories.ProductRepository;

import io.javalin.http.Context;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;


public class ProductController{
    private static final String ID = "id";

    private ProductRepository productRepository;

    public ProductController(ProductRepository postRepository) {
        this.productRepository = postRepository;
    }
    

    public void create(Context context) {
        Product product = context.bodyAsClass(Product.class);

        if (product.getId() != null) {
            throw new BadRequestResponse(String.format("Unable to create a new post with existing id: %s", product));
        }

        productRepository.create(product);
        context.status(HttpStatus.CREATED_201)
                .header(HttpHeader.LOCATION.name(), Paths.formatProductsPostLocation(product.getId().toString()));

    }

    public void delete(Context context) {
        productRepository.delete(context.pathParam(ID));

    }

    public void find(Context context) {
        String id = context.pathParam(ID);
        Product product = productRepository.find(id);

        if (product == null) {
            throw new NotFoundResponse(String.format("A product with id '%s' is not found", id));
        }

        context.json(product);

    }

    public void findAll(Context context) {
        context.json(productRepository.findAll());
    }

    public void update(Context context) {
        Product product = context.bodyAsClass(Product.class);
        String id = context.pathParam(ID);

        if (product.getId() != null && !product.getId().toString().equals(id)) {
            throw new BadRequestResponse("Id update is not allowed");
        }

        productRepository.update(product, id);

    }
    
}