package srp.controllers;

import srp.config.Paths;

import srp.models.Family;
import srp.repositories.FamilyRepository;

import io.javalin.http.Context;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;


public class FamilyController{
    private static final String ID = "id";

    private FamilyRepository familyRepository;

    public FamilyController(FamilyRepository postRepository) {
        this.familyRepository = postRepository;
    }
    

    public void create(Context context) {
        Family family = context.bodyAsClass(Family.class);

        if (family.getId() != null) {
            throw new BadRequestResponse(String.format("Unable to create a new post with existing id: %s", family));
        }

        familyRepository.create(family);
        context.status(HttpStatus.CREATED_201)
                .header(HttpHeader.LOCATION.name(), Paths.formatFamilysPostLocation(family.getId().toString()));

    }

    public void delete(Context context) {
        familyRepository.delete(context.pathParam(ID));

    }

    public void find(Context context) {
        String id = context.pathParam(ID);
        Family family = familyRepository.find(id);

        if (family == null) {
            throw new NotFoundResponse(String.format("A family with id '%s' is not found", id));
        }

        context.json(family);

    }

    public void findAll(Context context) {
        context.json(familyRepository.findAll());
    }

    public void update(Context context) {
        Family family = context.bodyAsClass(Family.class);
        String id = context.pathParam(ID);

        if (family.getId() != null && !family.getId().toString().equals(id)) {
            throw new BadRequestResponse("Id update is not allowed");
        }

        familyRepository.update(family, id);

    }
    
}