package edu.luc.comp433.api.cxf;

import edu.luc.comp433.api.payload.CategoryRepresentation;
import edu.luc.comp433.api.payload.CategoryRequest;
import edu.luc.comp433.api.workflow.CategoryActivity;
import edu.luc.comp433.api.ws.CategoryWebService;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
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
        CategoryRepresentation categoryRepresentation = categoryActivity.getCategory(id);
        if (categoryRepresentation == null) {
            throw new NotFoundException("Category not found");
        }
        return categoryRepresentation;
    }

    @Override
    @POST
    @Path("categories")
    public CategoryRepresentation createCategory(CategoryRequest categoryRequest) {
        try {
            return categoryActivity.createCategory(categoryRequest);
        } catch (DuplicatedEntryException dee) {
            throw new WebApplicationException("Duplicated Entry Not Allowed", Response.Status.CONFLICT);
        }
    }

    @Override
    @PUT
    @Path("category/{id}")
    public CategoryRepresentation updateCategory(@PathParam("id") long id, CategoryRequest categoryRequest) {
        try {
            return categoryActivity.update(id, categoryRequest);
        } catch (EntryNotFoundException enf) {
            throw new NotFoundException(enf);
        } catch (DuplicatedEntryException dee) {
            throw new WebApplicationException(dee.getMessage(), Response.Status.CONFLICT);
        }
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
        try {
            categoryActivity.delete(id);
        } catch (EntryNotFoundException enf) {
            throw new NotFoundException(enf);
        } catch (NotRemovableException nre) {
            throw new BadRequestException(nre);
        }
    }


}
