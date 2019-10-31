package edu.luc.comp433.api.cxf;

import edu.luc.comp433.api.cxf.linkbuilder.LinkBuilder;
import edu.luc.comp433.api.payload.CategoryRepresentation;
import edu.luc.comp433.api.payload.CategoryRequest;
import edu.luc.comp433.api.workflow.CategoryActivity;
import edu.luc.comp433.api.ws.CategoryWebService;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.jaxrs.ext.ResponseStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@Slf4j
public class CategoryResource implements CategoryWebService {

    private CategoryActivity categoryActivity;

    @Context
    private UriInfo uriInfo;

    public CategoryResource(CategoryActivity categoryActivity) {
        this.categoryActivity = categoryActivity;
    }

    @GET
    @Path("category/{id}")
    @Produces("application/hal+json")
    public CategoryRepresentation getCategory(@PathParam("id") long id) {
        return withLinks(categoryActivity.getCategory(id));
    }

    @Override
    @POST
    @Path("categories")
    @Produces("application/hal+json")
    public CategoryRepresentation createCategory(CategoryRequest categoryRequest) {
        return withLinks(categoryActivity.createCategory(categoryRequest));
    }

    @Override
    @PUT
    @Path("category/{id}")
    @Produces("application/hal+json")
    public CategoryRepresentation updateCategory(@PathParam("id") long id, CategoryRequest categoryRequest) {
        return withLinks(categoryActivity.update(id, categoryRequest));
    }

    @Override
    @GET
    @Path("categories")
    @Produces("application/hal+json")
    public List<CategoryRepresentation> allCategories() {
        return withLinks(categoryActivity.list());
    }

    @DELETE
    @Path("category/{id}")
    @ResponseStatus(Response.Status.NO_CONTENT)
    public void deleteCategory(@PathParam("id") long id) {
            categoryActivity.delete(id);
    }

    protected CategoryRepresentation withLinks(CategoryRepresentation category) {
        category.add(LinkBuilder.get(uriInfo).linkTo(CategoryResource.class, "getCategory")
                .withSelfRel().build(category.getId()));
        category.add(LinkBuilder.get(uriInfo).linkTo(CategoryResource.class, "allCategories")
                .withRel("all").build());
        return category;
    }

    protected List<CategoryRepresentation> withLinks(List<CategoryRepresentation> categories) {
        categories.forEach(this::withLinks);
        return categories;
    }

}
