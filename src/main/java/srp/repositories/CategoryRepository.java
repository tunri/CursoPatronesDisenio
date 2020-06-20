package srp.repositories;

import srp.models.Category;

import java.util.List;

public interface CategoryRepository {
    void create(Category category);

    Category find(String id);

    List<Category> findAll();

    Category update(Category post, String id);

    void delete(String id);
}