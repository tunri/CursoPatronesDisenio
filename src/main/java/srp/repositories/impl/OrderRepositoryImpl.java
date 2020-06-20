package srp.repositories.impl;

import srp.models.Order;
import srp.repositories.OrderRepository;

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

public class OrderRepositoryImpl implements OrderRepository {

    private static final String COLLECTION_NAME = "orders";
    private static final FindOneAndReplaceOptions UPDATE_OPTIONS = new FindOneAndReplaceOptions()
            .returnDocument(ReturnDocument.AFTER);

    private final MongoCollection<Order> orders;

    public OrderRepositoryImpl(MongoDatabase database) {
        this.orders = database.getCollection(COLLECTION_NAME, Order.class);
    }

    @Override
    public void create(Order order) {
        System.out.println("entrando: " + order);
        order.setId((new ObjectId()).toString());
        orders.insertOne(order);
    }

    @Override
    public void delete(String id) {
        orders.deleteOne(new Document("_id", id));
    }

    @Override
    public Order find(String id) {
        //System.out.println("_id: " + id);
        return orders.find(eq("_id", id)).first();
    }

    @Override
    public List<Order> findAll() {
        List<Order> result = new LinkedList<>();

        for (Order order : orders.find()) {
            //System.out.println("order: " + order);
            result.add(order);
        }

        return result;
    }

    @Override
    public Order update(Order post, String id) {
        return orders.findOneAndReplace(new Document("_id", id), post, UPDATE_OPTIONS);
    }
    
}