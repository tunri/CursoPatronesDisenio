package srp.repositories.impl;

import srp.models.Customer;
import srp.repositories.CustomerRepository;

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

public class CustomerRepositoryImpl implements CustomerRepository {

    private static final String COLLECTION_NAME = "customers";
    private static final FindOneAndReplaceOptions UPDATE_OPTIONS = new FindOneAndReplaceOptions()
            .returnDocument(ReturnDocument.AFTER);

    private final MongoCollection<Customer> customers;

    public CustomerRepositoryImpl(MongoDatabase database) {
        this.customers = database.getCollection(COLLECTION_NAME, Customer.class);
    }

    @Override
    public void create(Customer customer) {
        System.out.println("entrando: " + customer);
        customer.setId((new ObjectId()).toString());
        customers.insertOne(customer);
    }

    @Override
    public void delete(String id) {
        customers.deleteOne(new Document("_id", id));
    }

    @Override
    public Customer find(String id) {
        //System.out.println("_id: " + id);
        return customers.find(eq("_id", id)).first();
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> result = new LinkedList<>();

        for (Customer customer : customers.find()) {
            //System.out.println("customer: " + customer);
            result.add(customer);
        }

        return result;
    }

    @Override
    public Customer update(Customer post, String id) {
        return customers.findOneAndReplace(new Document("_id", id), post, UPDATE_OPTIONS);
    }
    
}