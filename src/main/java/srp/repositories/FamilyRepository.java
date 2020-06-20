package srp.repositories;

import srp.models.Family;

import java.util.List;

public interface FamilyRepository {
    void create(Family family);

    Family find(String id);

    List<Family> findAll();

    Family update(Family post, String id);

    void delete(String id);
}