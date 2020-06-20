package srp.repositories.impl;

import srp.models.Category;
import srp.repositories.CategoryRepository;

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

public class CategoryRepositoryImpl implements CategoryRepository {

    private static final String COLLECTION_NAME = "categorys";
    private static final FindOneAndReplaceOptions UPDATE_OPTIONS = new FindOneAndReplaceOptions()
            .returnDocument(ReturnDocument.AFTER);

    private final MongoCollection<Category> categorys;

    public CategoryRepositoryImpl(final MongoDatabase database) {
        this.categorys = database.getCollection(COLLECTION_NAME, Category.class);
    }

    @Override
    public void create(final Category category) {
        System.out.println("entrando: " + category);
        category.setId((new ObjectId()).toString());
        categorys.insertOne(category);
    }

    @Override
    public void delete(final String id) {
        categorys.deleteOne(new Document("_id", id));
    }

    @Override
    public Category find(final String id) {
        // System.out.println("_id: " + id);
        return categorys.find(eq("_id", id)).first();
    }

    @Override
    public List<Category> findAll() {
        final List<Category> result = new LinkedList<>();

        for (final Category category : categorys.find()) {
            // System.out.println("customer: " + categorys);
            result.add(category);
        }

        return result;
    }

    @Override
    public Category update(final Category post, final String id) {
        return categorys.findOneAndReplace(new Document("_id", id), post, UPDATE_OPTIONS);
    }
    
}