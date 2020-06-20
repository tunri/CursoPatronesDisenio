package srp.controllers;

import srp.config.Paths;

import srp.models.Category;
import srp.repositories.CategoryRepository;

import io.javalin.http.Context;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;


public class CategoryController{
    private static final String ID = "id";

    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository postRepository) {
        this.categoryRepository = postRepository;
    }
    

    public void create(Context context) {
        Category category = context.bodyAsClass(Category.class);

        if (category.getId() != null) {
            throw new BadRequestResponse(String.format("Unable to create a new post with existing id: %s", category));
        }

        categoryRepository.create(category);
        context.status(HttpStatus.CREATED_201)
                .header(HttpHeader.LOCATION.name(), Paths.formatCategorysPostLocation(category.getId().toString()));

    }

    public void delete(Context context) {
        categoryRepository.delete(context.pathParam(ID));

    }

    public void find(Context context) {
        String id = context.pathParam(ID);
        Category category = categoryRepository.find(id);

        if (category == null) {
            throw new NotFoundResponse(String.format("A category with id '%s' is not found", id));
        }

        context.json(category);

    }

    public void findAll(Context context) {
        context.json(categoryRepository.findAll());
    }

    public void update(Context context) {
        Category category = context.bodyAsClass(Category.class);
        String id = context.pathParam(ID);

        if (category.getId() != null && !category.getId().toString().equals(id)) {
            throw new BadRequestResponse("Id update is not allowed");
        }

        categoryRepository.update(category, id);

    }
    
}