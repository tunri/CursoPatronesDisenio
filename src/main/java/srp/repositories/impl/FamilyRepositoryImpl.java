package srp.repositories.impl;

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

public class FamilyRepositoryImpl implements FamilyRepository {

    private static final String COLLECTION_NAME = "familys";
    private static final FindOneAndReplaceOptions UPDATE_OPTIONS = new FindOneAndReplaceOptions()
            .returnDocument(ReturnDocument.AFTER);

    private final MongoCollection<Family> familys;

    public FamilyRepositoryImpl(final MongoDatabase database) {
        this.familys = database.getCollection(COLLECTION_NAME, Family.class);
    }

    @Override
    public void create(final Family family) {
        System.out.println("entrando: " + family);
        family.setId((new ObjectId()).toString());
        familys.insertOne(family);
    }

    @Override
    public void delete(final String id) {
        familys.deleteOne(new Document("_id", id));
    }

    @Override
    public Family find(final String id) {
        // System.out.println("_id: " + id);
        return familys.find(eq("_id", id)).first();
    }

    @Override
    public List<Family> findAll() {
        final List<Family> result = new LinkedList<>();

        for (final Family family : familys.find()) {
            // System.out.println("customer: " + familys);
            result.add(family);
        }

        return result;
    }

    @Override
    public Family update(final Family post, final String id) {
        return familys.findOneAndReplace(new Document("_id", id), post, UPDATE_OPTIONS);
    }
    
}