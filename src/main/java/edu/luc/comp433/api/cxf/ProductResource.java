package edu.luc.comp433.api.cxf;

import edu.luc.comp433.api.payload.ProductRepresentation;
import edu.luc.comp433.api.payload.ProductRequest;
import edu.luc.comp433.api.workflow.ProductActivity;
import edu.luc.comp433.api.ws.ProductWebService;
import org.apache.cxf.jaxrs.ext.ResponseStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public class ProductResource implements ProductWebService {

    private ProductActivity productActivity;

    public ProductResource(ProductActivity productActivity) {
        this.productActivity = productActivity;
    }

    @Override
    @GET
    @Path("/product/{id}")
    @Produces("application/hal+json")
    public ProductRepresentation getProduct(@PathParam("id") long id) {
        return productActivity.getProduct(id);
    }

    @Override
    @POST
    @Path("/products")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public ProductRepresentation createProduct(ProductRequest ProductRequest) {
        return productActivity.createProduct(ProductRequest);
    }

    @Override
    @PUT
    @Path("/product/{id}")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public ProductRepresentation updateProduct(@PathParam("id") long id, ProductRequest ProductRequest) {
        return productActivity.update(id, ProductRequest);
    }

    public List<ProductRepresentation> allProducts() {
        return productActivity.list();
    }

    @Override
    @GET
    @Path(value = "/products")
    @Produces("application/hal+json")
    public List<ProductRepresentation> search(@QueryParam("q") String query) {
        if (query == null || query.length() == 0) {
            return allProducts();
        }
        return productActivity.search(query);
    }

    @Override
    @DELETE
    @Path("/product/{id}")
    @ResponseStatus(Response.Status.NO_CONTENT)
    public void deleteProduct(@PathParam("id") long id) {
        productActivity.delete(id);
    }
}
