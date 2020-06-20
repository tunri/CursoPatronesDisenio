package srp.repositories.impl;

import srp.models.Product;
import srp.repositories.ProductRepository;

import srp.models.Category;
import srp.repositories.CategoryRepository;
import srp.models.Family;
import srp.repositories.FamilyRepository;

import java.util.List;
import java.util.LinkedList;

//import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;

public class ProductRepositoryImpl implements ProductRepository {

    private static final String COLLECTION_NAME = "products";
    private static final FindOneAndReplaceOptions UPDATE_OPTIONS = new FindOneAndReplaceOptions()
            .returnDocument(ReturnDocument.AFTER);

    private final MongoCollection<Product> products;

    private final CategoryRepositoryImpl categoryRepositoryImpl;
    private final FamilyRepositoryImpl familyRepositoryImpl;
    public ProductRepositoryImpl(MongoDatabase database) {
        this.products = database.getCollection(COLLECTION_NAME, Product.class);
        this.categoryRepositoryImpl = new CategoryRepositoryImpl(database);
        this.familyRepositoryImpl = new FamilyRepositoryImpl(database);

    }

    @Override
    public void create(Product product) {
        System.out.println("entrando: " + product);
        product.setId((new ObjectId()).toString());
        products.insertOne(product);
    }

    @Override
    public void delete(String id) {
        products.deleteOne(new Document("_id", id));
    }

    @Override
    public Product find(String id) {
        //System.out.println("_id: " + id);
        return products.find(eq("_id", id)).first();
    }

    @Override
    public List<Product> findAll() {
        List<Product> result = new LinkedList<>();

        for (Product product : products.find()) {
            //System.out.println("customer: " + products);
            Category category = categoryRepositoryImpl.find(product.getCategory_id());
            Family family = familyRepositoryImpl.find(product.getFamily_id());
            product.setCategory(category);
            product.setFamily(family);
            result.add(product);
        }

        return result;
    }

    @Override
    public Product update(Product post, String id) {
        return products.findOneAndReplace(new Document("_id", id), post, UPDATE_OPTIONS);
    }
    
}