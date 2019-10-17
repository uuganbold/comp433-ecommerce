package edu.luc.comp433.api.cxf;

import edu.luc.comp433.api.payload.CategoryRepresentation;
import edu.luc.comp433.api.payload.CategoryRequest;
import edu.luc.comp433.api.workflow.CategoryActivity;
import edu.luc.comp433.api.ws.CategoryWebService;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.jaxrs.ext.ResponseStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Slf4j
public class CategoryResource implements CategoryWebService {

    private CategoryActivity categoryActivity;

    public CategoryResource(CategoryActivity categoryActivity) {
        this.categoryActivity = categoryActivity;
    }

    @GET
    @Path("category/{id}")
    public CategoryRepresentation getCategory(@PathParam("id") long id) {
        return categoryActivity.getCategory(id);
    }

    @Override
    @POST
    @Path("categories")
    public CategoryRepresentation createCategory(CategoryRequest categoryRequest) {
            return categoryActivity.createCategory(categoryRequest);
    }

    @Override
    @PUT
    @Path("category/{id}")
    public CategoryRepresentation updateCategory(@PathParam("id") long id, CategoryRequest categoryRequest) {
            return categoryActivity.update(id, categoryRequest);
    }

    @Override
    @GET
    @Path("categories")
    public List<CategoryRepresentation> allCategories() {
        return categoryActivity.list();
    }

    @DELETE
    @Path("category/{id}")
    @ResponseStatus(Response.Status.NO_CONTENT)
    public void deleteCategory(@PathParam("id") long id) {
            categoryActivity.delete(id);
    }


}
